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

public class LoginActivity extends Activity
{

	private EditText emailET;
	private EditText pwdET;
	private ProgressDialog prgDialog;
	private AsyncHttpClient client = new AsyncHttpClient();

	protected void onCreate( Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );
		setContentView( R.layout.login_screen );

		emailET = (EditText) findViewById( R.id.loginEmail );
		pwdET = (EditText) findViewById( R.id.loginPassword );
		prgDialog = new ProgressDialog(this);
	}

	public void onBackPressed()
	{
		super.onBackPressed();
		overridePendingTransition( R.anim.slide_in, R.anim.slide_out );
		finish();
	}

	public void doLogging( View view )
	{
		String email = emailET.getText().toString();
		String password = pwdET.getText().toString();

		if( UtilHelper.isNotNull( email ) && UtilHelper.isNotNull( password ) )
		{
			if( UtilHelper.validate( email ) )
			{
				try
				{
					prgDialog.setMessage("Please wait...");
					prgDialog.setCancelable(false);
					prgDialog.show();
					client.addHeader("content-type", "application/json");
					String url = "http://dry-dusk-8611.herokuapp.com/dologin/" + URLEncoder.encode( email, "UTF-8" ) + "/" + URLEncoder.encode( password, "UTF-8" );
					client.get( this.getApplicationContext(), url, new AsyncHttpResponseHandler()
					{
						@Override
						public void onSuccess( String response )
						{
							Log.d( "HTTP", "onSuccess: " + response );
							prgDialog.dismiss();
						}
					} );
				}
				catch( Exception e )
				{
					e.printStackTrace();
				}
			}
			else
			{
				Toast.makeText( getApplicationContext(), "Please enter valid email", Toast.LENGTH_LONG ).show();
			}
		}
		else
		{
			Toast.makeText( getApplicationContext(), "Please fill the form, don't leave any field blank", Toast.LENGTH_LONG ).show();
		}
	}
}
