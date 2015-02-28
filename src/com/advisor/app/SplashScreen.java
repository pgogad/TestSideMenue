package com.advisor.app;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class SplashScreen extends Activity
{
    private AsyncHttpClient client = new AsyncHttpClient();
	private ProgressDialog progress;
	@Override
	public void onCreate( Bundle icicle )
	{
		super.onCreate( icicle );
		setContentView( R.layout.splash_screen );

		progress = new ProgressDialog( this );
		progress.setCancelable( false );
		progress.setMessage( "Loading.." );
		
		getRate();
	}

	public void getRate()
	{
		try
		{
			progress.show();
            client.get(this.getApplicationContext(),"http://dry-dusk-8611.herokuapp.com/ping",new AsyncHttpResponseHandler()
            {
                    @Override
                    public void onSuccess( String response )
                    {
                    	progress.dismiss();
                        Log.d("HTTP", "onSuccess: " + response);
                        Intent mainIntent = new Intent( SplashScreen.this, MainLanding.class );
                        SplashScreen.this.startActivity( mainIntent );
                        SplashScreen.this.finish();
                        overridePendingTransition( R.anim.slide_in, R.anim.slide_out );
                    }
                    
                    @Override
                    public void onFailure( int statusCode, Throwable error, String content )
					{
                    	progress.dismiss();
                        //Log.d("HTTP", "onSuccess: " + response);
                        Intent mainIntent = new Intent( SplashScreen.this, MainLanding.class );
                        SplashScreen.this.startActivity( mainIntent );
                        SplashScreen.this.finish();
                        overridePendingTransition( R.anim.slide_in, R.anim.slide_out );
						
					}
            });
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
	}
}
