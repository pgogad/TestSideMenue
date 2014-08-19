package com.test.testsidemenue;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.test.testsidemenue.runners.TimerServiceIntent;
import com.test.testsidemenue.twilio.MonkeyPhone;

public class CallActivity extends Activity
{

	private MonkeyPhone phone;
	
	@SuppressLint("ServiceCast")
	protected void onCreate( Bundle savedInstanceState )
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.call_activity);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		phone = new MonkeyPhone(getApplicationContext());
		PhoneCallListener phoneListener = new PhoneCallListener(this, "");
		TelephonyManager telephonyManager = (TelephonyManager) this.getSystemService(TELEPHONY_SERVICE);
		telephonyManager.listen(phoneListener, PhoneStateListener.LISTEN_CALL_STATE);

		TextView textArea = (TextView) findViewById(R.id.call_disclaimers_text);
		textArea.setText("Please note :\n" + "Google voice email, password or phone number is not be saved by us.");
		textArea.setMovementMethod(new ScrollingMovementMethod());
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

	
	
	public void makeIntentCall( )
	{
		Intent intent = new Intent(this, TimerServiceIntent.class);
		this.startService(intent);

		Intent callIntent = new Intent(Intent.ACTION_CALL);
		callIntent.setData(Uri.parse("tel:+16177759198"));
		startActivity(callIntent);
	}
}
