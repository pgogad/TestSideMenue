package com.test.testsidemenue;

import java.lang.reflect.Method;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.telephony.TelephonyManager;

import com.android.internal.telephony.ITelephony;

public class TimerService extends Service
{

	private FileManagement fileManagement = null;

	@Override
	public void onCreate()
	{
		fileManagement = new FileManagement(this);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		String[] mins = fileManagement.readFromFile();
		mins[1] = String.valueOf(System.currentTimeMillis());
		long endTime = System.currentTimeMillis() + (((60 * Integer.valueOf(mins[0]).intValue()) + 10) * 1000);
		while (System.currentTimeMillis() < endTime)
		{
			synchronized (this)
			{
				try
				{
					this.wait(1000 * 10);
				}
				catch (Exception e)
				{
				}
			}
		}
		mins[2] = String.valueOf(System.currentTimeMillis());
		disconnectPhoneItelephony(this);
		fileManagement.writeToFile(mins);
		stopSelf();
		return Service.START_STICKY;
	}

	@SuppressWarnings(
	{ "unchecked", "rawtypes" })
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
			// telephonyService.silenceRinger();
			System.out.println("Disconnecting Call now...");
			// telephonyService.answerRingingCall();
			// telephonyService.endcall();
			System.out.println("Call disconnected...");
			telephonyService.endCall();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public IBinder onBind(Intent intent)
	{
		return null;
	}
}
