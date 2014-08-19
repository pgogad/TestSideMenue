package com.test.testsidemenue.runners;

import android.app.IntentService;
import android.content.Intent;

import com.test.testsidemenue.FileManagement;

public class TimerServiceIntent extends IntentService
{
	private Thread t;

	public TimerServiceIntent()
	{
		super("SianeTimerService");
	}

	@Override
	protected void onHandleIntent( Intent intent )
	{
		System.out.println("Service started");
		FileManagement fileManagement = new FileManagement(this);
		String[] mins = fileManagement.readFromFile();
		mins[1] = String.valueOf(System.currentTimeMillis());
		long endTime = System.currentTimeMillis() + (((60 * Integer.valueOf(mins[0]).intValue()) + 15) * 1000);
		TimeKeepers tk = new TimeKeepers(endTime, this);

		synchronized (this)
		{
			t = new Thread(tk);
			t.setDaemon(true);
			try
			{
				t.start();
				t.join();
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}

		mins[2] = String.valueOf(System.currentTimeMillis());
		fileManagement.writeToFile(mins);
		stopSelf();
	}

	public void onDestroy( )
	{
		if (t.isAlive())
		{
			System.out.println("Calling interrupt...");
			t.interrupt();
		}
	}
}
