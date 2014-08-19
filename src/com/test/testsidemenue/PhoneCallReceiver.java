package com.test.testsidemenue;

import java.lang.reflect.Method;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import com.android.internal.telephony.ITelephony;

public class PhoneCallReceiver extends BroadcastReceiver
{
//	private boolean isAppCall = false;
//	private String number = null;
	private static PhoneCallListener phoneListener = null;
	TelephonyManager telephony;

	@Override
	public void onReceive(Context context, Intent intent)
	{
		
		if(null == phoneListener)
		{
			phoneListener = new PhoneCallListener(context,"");
			telephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
			telephony.listen(phoneListener, PhoneStateListener.LISTEN_CALL_STATE);
		}
	}

	public void onDestroy() 
	{
        telephony.listen(phoneListener, PhoneStateListener.LISTEN_NONE);
    }
	
	@SuppressWarnings(
	{ "unchecked", "rawtypes", "unused" })
	private void disconnectPhoneItelephony(Context context)
	{
		ITelephony telephonyService;
		System.out.println("Now disconnecting using ITelephony....");
		TelephonyManager telephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		try
		{
			System.out.println("End time : " + System.currentTimeMillis());
			System.out.println("Get getTeleService...");
			Class c = Class.forName(telephony.getClass().getName());
			Method m = c.getDeclaredMethod("getITelephony");
			m.setAccessible(true);
			telephonyService = (ITelephony) m.invoke(telephony);
			System.out.println("Disconnecting Call now...");
			System.out.println("Call disconnected...");
			telephonyService.endCall();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	// 04-27 23:14:26.216: I/System.out(23593): Start time : 1398662066227
	// 04-27 23:15:26.975: I/System.out(23593): End time : 1398662126982

}
