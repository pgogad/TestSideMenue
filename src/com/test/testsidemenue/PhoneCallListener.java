package com.test.testsidemenue;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import com.test.testsidemenue.runners.TimerServiceIntent;

public class PhoneCallListener extends PhoneStateListener
{
	// private boolean isPhoneCalling = false;
	Context context;
	Intent timer;
	private FileManagement fileManagement;
	String phoneNumber = null;
	boolean callEnded = false;

	public PhoneCallListener(Context context, String number)
	{
		super();
		this.context = context;
		fileManagement = new FileManagement(context);
		phoneNumber = number;
	}

	private boolean isMyServiceRunning( )
	{
		ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE))
		{
			if (TimerServiceIntent.class.getName().equals(service.service.getClassName()))
			{
				return true;
			}
		}
		return false;
	}

	public static Boolean phoneRinging = false;

	public void onCallStateChanged( int state, String incomingNumber )
	{
		System.out.println("Phone state : " + incomingNumber);

		switch (state)
		{
		case TelephonyManager.CALL_STATE_IDLE:
			System.out.println("IDLE");
			if (callEnded)
			{
				// Check if service is running if yes kill it
				// This will be after call is placed
				System.out.println("Entered if block for CALL_STATE_IDLE");
				System.out.println("Put the code to end service here.....");

				if (isMyServiceRunning())
				{
					System.out.println("Service is running killing it");
					context.stopService(new Intent(context, TimerServiceIntent.class));
				}
				else
				{
					System.out.println("Service is not running no need to kill ");
				}
				callEnded = false;
			}
			else
			{
				System.out.println("Entered else block for CALL_STATE_IDLE");
			}
			break;
		case TelephonyManager.CALL_STATE_OFFHOOK:
			callEnded = true;
			System.out.println("Entered CALL_STATE_OFFHOOK");
			break;
		case TelephonyManager.CALL_STATE_RINGING:
			// put in start time here
			System.out.println("Entered CALL_STATE_RINGING");
			break;
		default:
			break;
		}
	}
}
