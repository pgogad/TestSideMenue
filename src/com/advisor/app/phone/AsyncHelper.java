package com.advisor.app.phone;

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
		String[] results = new String[4];

		String args = params[0];
		if( args.equalsIgnoreCase( "mainpage" ) )
		{
			results[Constants.RATE] = HttpHelper.getRates();
		}
		if(args.equalsIgnoreCase( "paypalapproval" ))
		{
			results[Constants.PAY_PAL] = HttpHelper.postPayPalApproval( params[1] );
		}
		else 
		{
			results[Constants.CAPABILITY_TOKEN] = HttpHelper.requestWebService();
			results[Constants.RATE] = HttpHelper.getRates();
			results[Constants.PHONE_NUMBER] = HttpHelper.getPhoneNumber();
		}
		return results;
	}
}
