package com.advisor.app;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.text.method.ScrollingMovementMethod;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.advisor.app.db.AdvisorDB;
import com.advisor.app.disclaimer.DisclaimerActivity;
import com.advisor.app.login.RegisterMe;
import com.advisor.app.login.SigninActivity;
import com.advisor.app.phone.AsyncHelper;
import com.advisor.app.phone.Constants;
import com.advisor.app.util.UtilHelper;

public class MainLanding extends Activity
{

	private String[] drawerListViewItems;
	private ListView drawerListView;
	private DrawerLayout drawerLayout;
	private ActionBarDrawerToggle actionBarDrawerToggle;
	private AdvisorDB dataBase;
	private ProgressDialog progress;

	private String rates = null;
	private int minutes = 0;
	private AsyncHelper async = null;

	private SharedPreferences sharedPref;
	private Editor editor;

	@Override
	protected void onCreate( Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );
		setContentView( R.layout.gmail_style_navigation );

		dataBase = new AdvisorDB( this );
		progress = new ProgressDialog( this );
		progress.setMessage( "Loading..." );
		progress.setCancelable( false );
		
		async = new AsyncHelper( progress );

		sharedPref = getSharedPreferences( Constants.SHARED_PREF_NAME, Context.MODE_PRIVATE );
		editor = sharedPref.edit();
		try
		{
			if( rates == null )
			{
				String[] result = async.execute( "mainpage" ).get();
				rates = result[Constants.RATE];
			}
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}

		FrameLayout frame = (FrameLayout) findViewById( R.id.content_frame );

		LinearLayout parent = new LinearLayout( this );
		parent.setOrientation( LinearLayout.VERTICAL );

		LinearLayout linear = new LinearLayout( this );
		LinearLayout forDescription = new LinearLayout( this );
		linear.setOrientation( LinearLayout.HORIZONTAL );

		forDescription.setOrientation( LinearLayout.VERTICAL );

		ImageView logo = new ImageView( this );

		logo.setAdjustViewBounds( true );
		logo.setImageResource( R.drawable.sian_icon );

		logo.setMaxHeight( 500 );
		logo.setMaxWidth( 500 );
		logo.setPadding( 5, 5, 5, 5 );
		logo.setX( 10 );
		logo.setY( 10 );
		//logo.setBackgroundColor( Color.GREEN );

		LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams( LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT );
		logo.setLayoutParams( llp );

		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics( metrics );

		TextView intro = new TextView( this );

		intro.setWidth( metrics.widthPixels - 120 );
		intro.setPadding( 10, 10, 10, 5 );

		intro.setX( 10 );
		// intro.setBackgroundColor( Color.YELLOW );
		intro.setGravity( Gravity.LEFT );
		intro.setTypeface(Typeface.DEFAULT_BOLD);
		
		intro.setText( "\nClear \u2605 Accurate \u2605 Caring" );
		
		
		TextView description = new TextView( this );

		description.setWidth( metrics.widthPixels );
		// description.setBackgroundColor( Color.CYAN );
		description.setGravity( Gravity.LEFT );

		StringBuffer buff = new StringBuffer();

		buff.append(
				"Throughout the years, I have been privileged to give thousands of readings with the desire to help people with their life struggles. Given that I am seasoned, "
						+ "I am also versed in many different skills. Clarity and direction will be the focal point of our readings. I care deeply. This will always be a part of our sessions. "
						+ "It is my hope you will nurture greater peace, and abundance of light and love with each call.	I am certified in various techniques that include Life Coaching, "
						+ "certified in both Personal Development and Career Development, at the Master Level - a naturally born intuitive with advance skills in psychic mediumship. "
						+ "I read on all 7 levels which include psychic remote viewing. Some say I have advanced remote viewing abilities." )
				.append(
						"The aim in these sessions is for you to have full understanding of your concern, with a focus on new options and insights so you may generate your own self healing. "
								+ "We are all capable of growth and change. I go on what I see. I can remote view into the present and timeline into the future. Often information comes through as visual "
								+ "imprints which is part of my 100% photographic memory. This was tested medically in 2002. I am genetically able to see into the future because of my acute ability of memory precision."
								+ " Please understand that I am clairaudient, HOWEVER, I do not utilize this in my sessions per choice. I am clairessient as well and can also tap into the multi-dimensions through a timeline grid "
								+ "I have learned to access. I hope this helps you with your decision to find a considerate and accurate advisor.\n\n" )
				.append( "Love and Light, \n" ).append( "Sian" );

		description.setText( buff.toString() );
		description.setMaxLines( 30 );
		description.setPadding( 10, 10, 10, 10 );
		description.setMovementMethod( new ScrollingMovementMethod() );
		forDescription.addView( description );

		linear.addView( logo );
		linear.addView( intro );

		parent.addView( linear );
		parent.addView( forDescription );

		frame.addView( parent, new ViewGroup.LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT ) );

		drawerListViewItems = getResources().getStringArray( R.array.nav_drawer_items );
		drawerListView = (ListView) findViewById( R.id.left_drawer );
		drawerListView.setAdapter( new ArrayAdapter<String>( this, R.layout.drawer_listview_item, drawerListViewItems ) );

		drawerLayout = (DrawerLayout) findViewById( R.id.drawer_layout );

		actionBarDrawerToggle = new ActionBarDrawerToggle( MainLanding.this, drawerLayout, R.drawable.ic_drawer, R.string.siane, R.string.app_name );
		drawerLayout.setDrawerListener( actionBarDrawerToggle );

		getActionBar().setDisplayHomeAsUpEnabled( true );

		drawerLayout.setDrawerShadow( R.drawable.drawer_shadow, GravityCompat.START );
		drawerListView.setOnItemClickListener( new DrawerItemClickListener() );

	}

	@Override
	public void onConfigurationChanged( Configuration newConfig )
	{
		super.onConfigurationChanged( newConfig );
		actionBarDrawerToggle.onConfigurationChanged( newConfig );
	}

	@Override
	protected void onPostCreate( Bundle savedInstanceState )
	{
		super.onPostCreate( savedInstanceState );
		actionBarDrawerToggle.syncState();
	}

	@Override
	public boolean onOptionsItemSelected( MenuItem item )
	{
		if( actionBarDrawerToggle.onOptionsItemSelected( item ) )
		{
			return true;
		}
		return super.onOptionsItemSelected( item );
	}

	private class DrawerItemClickListener implements ListView.OnItemClickListener
	{
		@SuppressWarnings( "rawtypes" )
		@Override
		public void onItemClick( AdapterView parent, View view, int position, long id )
		{
			String option = (String) ((TextView) view).getText();

			if( option.equalsIgnoreCase( "Add Minutes" ) )
			{
				Intent intent = new Intent( getBaseContext(), com.advisor.app.payment.PayPalActivity.class );
				startActivity( intent );
				overridePendingTransition( R.anim.slide_in, R.anim.slide_out );
				drawerLayout.closeDrawer( drawerListView );
			}
			else if( option.equalsIgnoreCase( "Call Advisor" ) )
			{
				minutes = UtilHelper.getMinutesRemaining( dataBase.getAvailableMinutes(), rates );
				if( Integer.valueOf( minutes ) > 0 )
				{
					Intent intent = new Intent( getBaseContext(), CallActivity.class );
					startActivity( intent );
					drawerLayout.closeDrawer( drawerListView );
					overridePendingTransition( R.anim.slide_in, R.anim.slide_out );
				}
				else
				{
					drawerLayout.closeDrawer( drawerListView );
					Toast.makeText( getApplicationContext(), "Please buy minutes!!", Toast.LENGTH_LONG ).show();
				}
			}
			else if( option.equalsIgnoreCase( "Login" ) )
			{
				Intent login = new Intent( getBaseContext(), SigninActivity.class );
				startActivity( login );
				drawerLayout.closeDrawer( drawerListView );
				overridePendingTransition( R.anim.slide_in, R.anim.slide_out );
			}
			else if( option.equalsIgnoreCase( "Register" ) )
			{
				Intent register = new Intent( getBaseContext(), RegisterMe.class );
				startActivity( register );
				drawerLayout.closeDrawer( drawerListView );
				overridePendingTransition( R.anim.slide_in, R.anim.slide_out );
			}
			else if( option.equalsIgnoreCase( "Log Out" ) )
			{

				String[] shared = UtilHelper.sharedPrefExpand( sharedPref.getString( Constants.EDITOR_EMAIL, Constants.SP_DEFAULT ) );
				shared[Constants.SP_EMAIL] = Constants.SP_BLANK;
				editor.putString( Constants.EDITOR_EMAIL, UtilHelper.sharedPrefContract( shared ) );
				editor.commit();
				drawerLayout.closeDrawer( drawerListView );
			}
			else if( option.equalsIgnoreCase( "Disclaimer" ) )
			{
				Intent disclaimer = new Intent( getBaseContext(), DisclaimerActivity.class );
				startActivity( disclaimer );
				drawerLayout.closeDrawer( drawerListView );
				overridePendingTransition( R.anim.slide_in, R.anim.slide_out );
			}
			else
			{
				overridePendingTransition( R.anim.slide_in, R.anim.slide_out );
			}
		}
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();

	}

	public void onBackPressed()
	{
		super.onBackPressed();
		overridePendingTransition( R.anim.slide_in, R.anim.slide_out );
		finish();
	}
}
