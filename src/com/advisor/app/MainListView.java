package com.advisor.app;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainListView extends ListActivity
{
	String[] list = { "Pawan", "Pratik" };

	protected void onCreate( Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );
		ArrayAdapter<String> adapter = new ArrayAdapter<String>( this, android.R.layout.simple_list_item_1, list );
		setListAdapter( adapter );
	}

	public void onListItemClick( ListView parent, View v, int position, long id )
	{
		Toast.makeText( this, "You have selected : " + list[position], Toast.LENGTH_LONG ).show();
		Intent mainIntent = new Intent( MainListView.this, MainLanding.class );
		this.startActivity( mainIntent );
		overridePendingTransition( R.anim.slide_in, R.anim.slide_out );
	}
}
