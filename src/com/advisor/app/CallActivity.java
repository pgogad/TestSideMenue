package com.advisor.app;

import com.advisor.app.phone.PhoneHelper;
import com.test.testsidemenue.R;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Toast;

public class CallActivity extends Activity
{
	private PhoneHelper phone;
	
	protected void onCreate( Bundle savedInstanceState )
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.call_activity);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		phone = new PhoneHelper(getApplicationContext());		
	}
	
	
	public void hangupCall(View view)
	{
		phone.disconnect();
	}
	
	public void placeCall( View view )
	{
		try
		{
			phone.connect();
		}
		catch (Exception e)
		{
			Toast.makeText(getApplicationContext(), "Call Failed!!", Toast.LENGTH_LONG).show();
		}
	}
	
}
