package com.advisor.app.phone;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.util.Log;

import com.test.testsidemenue.twilio.HttpHelper;
import com.twilio.client.Connection;
import com.twilio.client.ConnectionListener;
import com.twilio.client.Device;
import com.twilio.client.Twilio;

public class PhoneHelper implements Twilio.InitListener
{

	private static final String TAG = "PhoneHelper";
	private Connection connection;
	private ConnectionListener connectionListener;
	private Device device;

	public PhoneHelper(Context context)
	{
		Twilio.initialize(context, this);
		connectionListener = new PhoneConnectionListener();
	}
	
	@Override
	public void onError( Exception arg0 )
	{
		Log.e(TAG, "Twilio SDK couldn't start: " + arg0.getLocalizedMessage());
	}

	public void connect()
	{
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("PhoneNumber", "6177759198");
		parameters.put("timeLimit", "60");
		connection = device.connect(parameters, connectionListener);
		
		if (null == connection)
		{
			Log.e(TAG, "Failed to initialize Twilio ");
		}
	}

	public void disconnect()
	{
		if (connection != null)
		{
			connection.disconnect();
			connection = null;
		}
	}

	@Override
	public void onInitialized()
	{
		try
		{
			String capabilityToken = HttpHelper.httpGet("http://dry-dusk-8611.herokuapp.com/client");
			device = Twilio.createDevice(capabilityToken, null);
		}
		catch (Exception e)
		{
			Log.e(TAG, "Failed to get capability token", e);
		}
	}

	@Override
	protected void finalize()
	{
		if (connection != null)
		{
			connection.disconnect();
		}

		if (device != null)
		{
			device.release();
		}
	}
}
