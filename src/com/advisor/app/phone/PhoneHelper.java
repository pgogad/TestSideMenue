package com.advisor.app.phone;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.util.Log;

import com.advisor.app.db.AdvisorDB;
import com.twilio.client.Connection;
import com.twilio.client.ConnectionListener;
import com.twilio.client.Device;
import com.twilio.client.Twilio;

public class PhoneHelper implements Twilio.InitListener, ConnectionListener
{
	private static final String TAG = "PhoneHelper";
	private Connection connection;
	private Device device;

	private String capabilityToken = null;

	private long startTime;
	private long endTime;

	private AdvisorDB database;

	public PhoneHelper(Context context)
	{
		database = new AdvisorDB( context );
		Twilio.initialize( context, this );
	}

	@Override
	public void onError( Exception arg0 )
	{
		Log.e( TAG, "Twilio SDK couldn't start: " + arg0.getLocalizedMessage() );
	}

	public void connect()
	{
		Map<String, String> parameters = new HashMap<String, String>();
		int amount = database.getAvailableMinutes().divide( new BigDecimal( "5.00" ) ).intValueExact();

		parameters.put( "PhoneNumber", HttpHelper.getPhoneNumber() );
		parameters.put( "timeLimit", String.valueOf( amount * 60 ) );
		connection = device.connect( parameters, this );

		if( null == connection )
		{
			Log.e( TAG, "Failed to initialize Twilio " );
		}
	}

	public void disconnect()
	{
		if( connection != null )
		{
			connection.disconnect();
			connection = null;
		}

		if( device != null )
		{
			device.release();
		}
		capabilityToken = null;
	}

	@Override
	public void onInitialized()
	{
		try
		{
			if( null == capabilityToken )
			{
				capabilityToken = HttpHelper.requestWebService();
				device = Twilio.createDevice( capabilityToken, null );
			}
		}
		catch( Exception e )
		{
			Log.e( TAG, "Failed to get capability token", e );
		}
	}

	@Override
	protected void finalize()
	{
		if( connection != null )
		{
			connection.disconnect();
		}

		if( device != null )
		{
			device.release();
		}
	}

	@Override
	public void onConnected( Connection arg0 )
	{
		startTime = System.currentTimeMillis();
		Log.d( TAG, "Call connected" );
	}

	@Override
	public void onConnecting( Connection arg0 )
	{
		Log.d( TAG, "Inside on connecting" );
	}

	@Override
	public void onDisconnected( Connection arg0 )
	{
		onDisconnect();
	}

	@Override
	public void onDisconnected( Connection arg0, int arg1, String arg2 )
	{
		onDisconnect();
	}

	private void onDisconnect()
	{
		endTime = System.currentTimeMillis();
		long seconds = (endTime - startTime) / 1000;
		Log.d( TAG, "Call lasted " + seconds + " seconds" );

		int minutes = 0;

		if( seconds >= 30 && seconds <= 60 )
		{
			minutes = 1;
		}
		else
		{
			minutes = Long.valueOf( seconds ).intValue() / 60;
		}

		Log.d( TAG, "Minutes to be charged : " + minutes + " mins" );

		BigDecimal amount = database.getAvailableMinutes();
		BigDecimal chargedAmt = new BigDecimal( String.valueOf( minutes ) );

		amount = amount.subtract( chargedAmt.multiply( new BigDecimal( "5.00" ) ) );

		Log.d( TAG, "Amount remaining : " + amount.toString() + " mins" );

		database.insertRecord( String.valueOf( amount.doubleValue() ), startTime, endTime );

		if( device != null )
		{
			device.release();
		}
	}
}
