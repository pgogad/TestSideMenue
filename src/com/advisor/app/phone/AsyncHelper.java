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
		String[] results = new String[3];

		String args = params[0];
		if( args.equalsIgnoreCase( "mainpage" ) )
		{
			results[0] = HttpHelper.getRates();
		}
		else
		{
			results[0] = HttpHelper.requestWebService();
			results[1] = HttpHelper.getRates();
			results[2] = HttpHelper.getPhoneNumber();
		}
		return results;
	}
}
