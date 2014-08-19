/*
 *  Copyright (c) 2011 by Twilio, Inc., all rights reserved.
 *
 *  Use of this software is subject to the terms and conditions of 
 *  the Twilio Terms of Service located at http://www.twilio.com/legal/tos
 */

package com.test.testsidemenue.twilio;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.util.Log;

import com.twilio.client.Connection;
import com.twilio.client.Device;
import com.twilio.client.Twilio;

public class MonkeyPhone implements Twilio.InitListener
{
	private static final String TAG = "MonkeyPhone";
	private Connection connection;
	private Device device;

	public MonkeyPhone(Context context)
	{
		Twilio.initialize(context, this /* Twilio.InitListener */);
	}

	/* Twilio.InitListener method */
	@Override
	public void onInitialized( )
	{
		try
		{
			String capabilityToken = HttpHelper.httpGet("http://twilio.webmatrixtech.com/auth.php");
			device = Twilio.createDevice(capabilityToken, null /* DeviceListener */);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void connect( )
	{
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("PhoneNumber", "5127574974");
		connection = device.connect(parameters, null);
		if (null == connection)
		{
			System.out.println("Connection failed....");
		}

	}

	public void disconnect( )
	{
		if (connection != null)
		{
			connection.disconnect();
			connection = null;
		}
	}

	/* Twilio.InitListener method */
	@Override
	public void onError( Exception e )
	{
		Log.e(TAG, "Twilio SDK couldn't start: " + e.getLocalizedMessage());
	}

	@Override
	protected void finalize( )
	{
		if (device != null)
		{
			device.release();
		}
	}
}
