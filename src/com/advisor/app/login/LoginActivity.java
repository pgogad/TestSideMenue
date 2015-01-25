package com.advisor.app.login;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.advisor.app.R;
import com.advisor.app.phone.AsyncHelper;
import com.advisor.app.util.UtilHelper;

public class LoginActivity extends Activity
{

	private EditText emailET;
	private EditText pwdET;
	private AsyncHelper async;

	protected void onCreate( Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );
		setContentView( R.layout.login_screen );

		async = new AsyncHelper( this );

		emailET = (EditText) findViewById( R.id.loginEmail );
		pwdET = (EditText) findViewById( R.id.loginPassword );
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
				async.execute( "login", email, password );
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
