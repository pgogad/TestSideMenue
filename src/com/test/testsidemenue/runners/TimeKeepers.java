package com.test.testsidemenue.runners;

import java.lang.reflect.Method;

import android.content.Context;
import android.telephony.TelephonyManager;

import com.android.internal.telephony.ITelephony;

public class TimeKeepers implements Runnable
{

	private long endTime;
	private Context context;

	public TimeKeepers(long endTime, Context context)
	{
		this.endTime = endTime;
		this.context = context;
	}

	public TimeKeepers()
	{
	}

	@Override
	public void run( )
	{
		while (System.currentTimeMillis() < endTime)
		{
			try
			{
				System.out.println("Siane timer is running...");
				Thread.sleep(1000 * 10);
			}
			catch (InterruptedException e)
			{
				return;
			}
			catch (Exception e)
			{
			}
		}
		disconnectPhoneItelephony();
	}

	@SuppressWarnings(
	{ "unchecked", "rawtypes" })
	private void disconnectPhoneItelephony( )
	{
		ITelephony telephonyService;
		System.out.println("Now disconnecting using ITelephony....");
		TelephonyManager telephony = (TelephonyManager) this.context.getSystemService(Context.TELEPHONY_SERVICE);
		try
		{
			System.out.println("End time : " + System.currentTimeMillis());
			System.out.println("Get getTeleService...");
			Class c = Class.forName(telephony.getClass().getName());
			Method m = c.getDeclaredMethod("getITelephony");
			m.setAccessible(true);
			telephonyService = (ITelephony) m.invoke(telephony);
			// telephonyService.silenceRinger();
			System.out.println("Disconnecting Call now...");
			// telephonyService.answerRingingCall();
			// telephonyService.endcall();
			System.out.println("Call disconnected...");
			telephonyService.endCall();
		}
		catch (Exception e)
		{
		}
	}

	public long getEndTime( )
	{
		return endTime;
	}

	public void setEndTime( long endTime )
	{
		this.endTime = endTime;
	}

}
