package com.advisor.app;

import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
//import android.support.v7.app.ActionBarDrawerToggle;
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

	private String rates;
	private int minutes = 0;
	private AsyncHelper async;

	@Override
	protected void onCreate( Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );
		setContentView( R.layout.gmail_style_navigation );

		dataBase = new AdvisorDB( this );

		// minutes = dataBase.getAvailableMinutes().divide( new BigDecimal(
		// "5.00" ) ).intValueExact();
		// StrictMode.ThreadPolicy policy = new
		// StrictMode.ThreadPolicy.Builder().permitAll().build();
		// StrictMode.setThreadPolicy( policy );

		async = new AsyncHelper( this );

		try
		{
			String[] result = async.execute( "mainpage" ).get();
			rates = result[Constants.RATE];
		}
		catch( InterruptedException | ExecutionException e )
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

		logo.setMaxHeight( 200 );
		logo.setMaxWidth( 200 );
		logo.setPadding( 5, 5, 5, 5 );
		logo.setX( 10 );
		logo.setY( 10 );
		logo.setBackgroundColor( Color.GREEN );

		LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams( LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT );
		logo.setLayoutParams( llp );

		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics( metrics );

		TextView intro = new TextView( this );

		intro.setWidth( metrics.widthPixels - 120 );
		intro.setPadding( 10, 10, 10, 5 );

		intro.setX( 10 );
		intro.setBackgroundColor( Color.YELLOW );
		intro.setGravity( Gravity.LEFT );
		intro.setText( "Java Collections Interview Questions \n" + "Index based access allow access of the element directly on the basis of index. " );

		TextView description = new TextView( this );

		description.setWidth( metrics.widthPixels );
		description.setBackgroundColor( Color.CYAN );
		description.setGravity( Gravity.LEFT );

		StringBuffer buff = new StringBuffer();

		buff.append(
				"Index based access allow access of the element directly on the basis of index. The cursor of the datastructure can directly goto the 'n' location and get the element. " )
				.append( "It doesnot traverse through n-1 elements. " )
				.append(
						"In Iterator based access, the cursor has to traverse through each element to get the desired element.So to reach the 'n'th element it need to traverse through n-1 elements. " )
				.append(
						"Insertion,updation or deletion will be faster for iterator based access if the operations are performed on elements present in between the datastructure. " )
				.append(
						"Insertion,updation or deletion will be faster for index based access if the operations are performed on elements present at last of the datastructure. " )
				.append( "Traversal or search in index based datastructure is faster. " )
				.append( "ArrayList is index access and LinkedList is iterator access. " );

		// buff.append(
		// "My readings are quick, honest and direct. At times, I can see far into timelines, up to 25 years down the road."
		// )
		// .append( "\n\n" )
		// .append(
		// "Throughout the years, I have been privileged to give thousands of readings with the desire to help people with their life struggles."
		// )
		// .append(
		// "Given that I am seasoned, I am also versed in many different skills."
		// )
		// .append( "\n\n" )
		// .append(
		// "I am certified in various techniques that include Life Coaching," )
		// .append(
		// "Personal Development/Career Development both at the Master Level. The aim in my sessions is for you to have full understanding and directness"
		// )
		// .append(
		// "to your concern, with a focus on new options and insights so you may generate your own self healing and wisdom. I go on what I see."
		// )
		// .append(
		// "I can remote view into the present and timeline into the future. I do receive visual imprints as I have 100% photographic memory."
		// )
		// .append(
		// "I am genetically able to see into the future because of my acute ability of memory precision. Please understand that I am clairaudient HOWEVER,"
		// )
		// .append(
		// "I do not utilize this in my sessions per choice. I am clairessient and can also tap into the multi-dimensions through a timeline grid I have learned"
		// )
		// .append(
		// "to access. This is a very general overview of what is offered to you."
		// )
		// .append(
		// "Growth is a focus and lately my journey has allowed a new perspective into the way I read - clearer, more precise, dealing less and less on the superficial"
		// )
		// .append(
		// "but more acutely into the root of the matter. What is frivilous is transient (though not without consequence), and I (we) will move beyond that."
		// )
		// .append( "\n\n" )
		// .append(
		// "Please note, I have changed in the way I read and must admit, before my scope to be exact with dates was spot on, and"
		// )
		// .append(
		// "my readings allowed for precise dates and time, however due to a personal transition and pathline shift, I have let go of time....and now give approx."
		// )
		// .append(
		// "as a guideline and do not probe deeper into being exact with dates .... I focus more on healing and soul shifts......my timing will be accurate but my"
		// )
		// .append(
		// "focus is not on exact date precision as it was before. I can still do that but it takes alot of mental energy I now use for deeper soul healing segments."
		// )
		// .append(
		// "This is my personal choice as a reader. Things have stepped up a few notches in the spiritual approach!"
		// ).append( "\n\n" )
		// .append( "Many, Many Blessings to you." ).append( "\n\n" );

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
		// Sync the toggle state after onRestoreInstanceState has occurred.
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
					overridePendingTransition( R.anim.slide_in, R.anim.slide_out );
				}
				else
				{
					drawerLayout.closeDrawer( drawerListView );
					Toast.makeText( getApplicationContext(), "Please buy minutes in order to call Adviser :-)!!", Toast.LENGTH_LONG ).show();
				}
			}
			else
			{
			}
		}
	}

	public void onBackPressed()
	{
		super.onBackPressed();
		overridePendingTransition( R.anim.slide_in, R.anim.slide_out );
		finish();
	}
}
