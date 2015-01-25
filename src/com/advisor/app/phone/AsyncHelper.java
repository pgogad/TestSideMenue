package com.advisor.app.phone;

import java.math.BigDecimal;

import com.advisor.app.util.UtilHelper;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

public class AsyncHelper extends AsyncTask<String, Void, String[]>
{

	private ProgressDialog progress;
	private Context context;

	public AsyncHelper(Context context)
	{
		this.context = context;
	}

	protected void onPostExecute( String[] result )
	{
		super.onPostExecute( result );
		progress.dismiss();
	}

	protected void onPreExecute()
	{
		progress = ProgressDialog.show( context, "Loading....", "Please wait", true );
	}

	@Override
	protected String[] doInBackground( String... params )
	{
		String[] results = new String[5];

		String args = params[0];
		if( args.equalsIgnoreCase( "mainpage" ) )
		{
			results[Constants.RATE] = HttpHelper.getRates();
		}
		else if( args.equalsIgnoreCase( "paypalapproval" ) )
		{
			results[Constants.PAY_PAL] = HttpHelper.postPayPalApproval( params[1] );
		}
		else if( args.equalsIgnoreCase( "login" ) )
		{
			results[Constants.LOGIN] = HttpHelper.validateLogin( params[1], params[2] );
		}
		else
		{
			String amount = params[1];
			results[Constants.RATE] = HttpHelper.getRates();

			int minutes = UtilHelper.getMinutesRemaining( new BigDecimal( amount ), results[Constants.RATE] );

			results[Constants.CAPABILITY_TOKEN] = HttpHelper.requestWebService( minutes );
			results[Constants.PHONE_NUMBER] = HttpHelper.getPhoneNumber();
		}
		return results;
	}
}
