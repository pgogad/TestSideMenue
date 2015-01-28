package com.advisor.app.login;

import java.net.URLEncoder;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.advisor.app.R;
import com.advisor.app.util.UtilHelper;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class RegisterMe extends Activity
{

	private ProgressDialog prgDialog;
	private EditText emailET;
	private EditText pwdET;
	
	private AsyncHttpClient client = new AsyncHttpClient();

	protected void onCreate( Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );
		setContentView( R.layout.register_me );

		emailET = (EditText) findViewById( R.id.registerEmail );
		pwdET = (EditText) findViewById( R.id.registerPassword );

		prgDialog = new ProgressDialog( this );
	}

	public void doRegistration( View view )
	{

		String email = emailET.getText().toString();
		String password = pwdET.getText().toString();

		if( UtilHelper.isNotNull( email ) && UtilHelper.isNotNull( password ) )
		{
			if( UtilHelper.validate( email ) )
			{
				try
				{
					prgDialog = new ProgressDialog( this );
					prgDialog.setMessage( "Please wait..." );
					prgDialog.setCancelable( false );
					prgDialog.show();
					client.addHeader( "content-type", "application/json" );
					String url = "http://dry-dusk-8611.herokuapp.com/dologin/" + URLEncoder.encode( email, "UTF-8" ) + "/"
							+ URLEncoder.encode( password, "UTF-8" );
					client.get( this.getApplicationContext(), url, new AsyncHttpResponseHandler()
					{
						@Override
						public void onSuccess( String response )
						{
							Log.d( "HTTP", "onSuccess: " + response );
							prgDialog.dismiss();
							Toast.makeText( getApplicationContext(), "Login Succesful", Toast.LENGTH_LONG ).show();
						}

						@Override
						public void onFailure( int statusCode, Throwable error, String content )
						{
							prgDialog.dismiss();
							if( statusCode == 404 )
							{
								Toast.makeText( getApplicationContext(), "Requested resource not found", Toast.LENGTH_LONG ).show();
							}
							else if( statusCode == 500 )
							{
								Toast.makeText( getApplicationContext(), "Something went wrong at server end", Toast.LENGTH_LONG ).show();
							}
							else
							{
								Toast.makeText( getApplicationContext(), "Unexpected Error occcured! Please try again later", Toast.LENGTH_LONG ).show();
							}
						}

					} );
					
				}
				catch( Exception ex )
				{
					ex.printStackTrace();
				}
				finally
				{
					if( prgDialog.isShowing() )
					{
						prgDialog.dismiss();
					}
				}
			}
			else
			{
				Toast.makeText( getApplicationContext(), "Please enter valid email address", Toast.LENGTH_LONG ).show();
			}
		}
		else
		{
			Toast.makeText( getApplicationContext(), "Please fill the form, don't leave any field blank", Toast.LENGTH_LONG ).show();
		}

	}
}
