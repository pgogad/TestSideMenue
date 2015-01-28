package com.advisor.app.login;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.advisor.app.R;

public class RegisterMe extends Activity
{

	private ProgressDialog prgDialog;
	private EditText emailET;
	private EditText pwdET;

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

	}
}
