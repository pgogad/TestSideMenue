package com.advisor.app;

import java.math.BigDecimal;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.advisor.app.db.AdvisorDB;
import com.advisor.app.phone.AsyncHelper;
import com.advisor.app.phone.PhoneHelper;

public class CallActivity extends Activity
{
	private PhoneHelper phone;
	private AsyncHelper asyncHelper;
	private AdvisorDB dataBase;

	protected void onCreate( Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );
		setContentView( R.layout.call_activity );
		dataBase = new AdvisorDB( this );
		asyncHelper = new AsyncHelper( this );

		try
		{
			BigDecimal amount = dataBase.getAvailableMinutes();
			phone = new PhoneHelper( getApplicationContext(), asyncHelper.execute( "call", amount.toString() ).get() );
		}
		catch( Exception ex )
		{
			ex.printStackTrace();
		}
	}

	public void hangupCall( View view )
	{
		try
		{
			phone.disconnect();
			super.onBackPressed();
			overridePendingTransition( R.anim.slide_in, R.anim.slide_out );
			finish();
		}
		catch( Exception ex )
		{
			Log.e( "CallActivity", "Problem while executing hangup", ex );
		}
	}

	public void placeCall( View view )
	{
		try
		{
			phone.connect();

		}
		catch( Exception e )
		{
			Toast.makeText( getApplicationContext(), "Call Failed!!", Toast.LENGTH_LONG ).show();
		}
	}

	public void onDestroy()
	{
		super.onDestroy();
		phone.destroy();
	}

	public void onBackPressed()
	{
		super.onBackPressed();
		overridePendingTransition( R.anim.slide_in, R.anim.slide_out );
		finish();
	}
}
