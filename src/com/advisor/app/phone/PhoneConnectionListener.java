package com.advisor.app.phone;

import com.twilio.client.Connection;
import com.twilio.client.ConnectionListener;

public class PhoneConnectionListener implements ConnectionListener
{
	
	private long startTime;
	private long endTime;

	public PhoneConnectionListener()
	{
		
	}
	
	
	public long getTimeOnCall()
	{
		return endTime - startTime;
	}
	
	@Override
	public void onConnected( Connection arg0 )
	{
		startTime = System.currentTimeMillis();
	}

	@Override
	public void onConnecting( Connection arg0 )
	{
		//startTime = System.currentTimeMillis();
	}

	@Override
	public void onDisconnected( Connection arg0 )
	{
		endTime = System.currentTimeMillis();
	}

	@Override
	public void onDisconnected( Connection arg0, int arg1, String arg2 )
	{
		endTime = System.currentTimeMillis();
	}

}
