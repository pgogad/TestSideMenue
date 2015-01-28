package com.advisor.app;

import android.app.Activity;
import android.app.ProgressDialog;
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
	private ProgressDialog progress;

	protected void onCreate( Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );
		setContentView( R.layout.call_activity );
		dataBase = new AdvisorDB( this );
		progress = new ProgressDialog( this );
		progress.setMessage( "Loading..." );
		progress.setCancelable( false );

		asyncHelper = new AsyncHelper( progress );

		try
		{
			phone = new PhoneHelper( CallActivity.this,  asyncHelper.execute( "call",dataBase.getAvailableMinutes().toString() ).get());
		}
		catch( Exception ex )
		{
			Log.e("CallActivity","Error occured while executing async task");
		}
	}

	public void hangupCall( View view )
	{
		try
		{
			phone.disconnect();
			super.onBackPressed();
			overridePendingTransition( R.anim.slide_in, R.anim.slide_out );
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
			if(null != phone)
			{
				phone.connect();
			}
			else
			{
				phone = new PhoneHelper( CallActivity.this,  asyncHelper.execute( "call",dataBase.getAvailableMinutes().toString() ).get());
				phone.connect();
			}
			
		}
		catch( Exception e )
		{
			Toast.makeText( getApplicationContext(), "Call Failed!!", Toast.LENGTH_LONG ).show();
		}
	}

	public void onDestroy()
	{
		super.onDestroy();
		phone = null;
	}

	public void onBackPressed()
	{
		super.onBackPressed();
		overridePendingTransition( R.anim.slide_in, R.anim.slide_out );
		finish();
	}
}
