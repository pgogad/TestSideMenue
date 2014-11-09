package com.test.testsidemenue;

//import android.app.ActionBar;
//import android.app.ActionBar.Tab;
//import android.app.FragmentTransaction;
//import android.os.Bundle;
//import android.support.v4.app.FragmentActivity;
//import android.util.DisplayMetrics;
//import android.view.Menu;
//import android.view.View;
//import android.widget.Button;
//import android.widget.LinearLayout;
//
//import com.advisor.app.animations.CollapseAnimation;
//import com.advisor.app.animations.ExpandAnimation;

public class MainActivity //extends FragmentActivity implements ActionBar.TabListener 
{
//	private LinearLayout menu;
//	private Button btnToggleMenuList;
//	private int screenWidth;
//	private boolean isExpanded = false;
//	
//	@Override
//	protected void onCreate(Bundle savedInstanceState)
//	{
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.menue);
///*		ActionBar actionBar = getActionBar();
//		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
//		actionBar.addTab(actionBar.newTab().setText(R.string.app_name).setTabListener(this));
//		actionBar.addTab(actionBar.newTab().setText(R.string.toggle).setTabListener(this));
//*/		
//		menu = (LinearLayout) findViewById(R.id.linearLayout2);
//		btnToggleMenuList = (Button) findViewById(R.id.button1);
//		DisplayMetrics metrics = new DisplayMetrics();
//		getWindowManager().getDefaultDisplay().getMetrics(metrics);
//		screenWidth = metrics.widthPixels;
//	}
//
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu)
//	{
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.main, menu);
//		return true;
//	}
//
//	
//	public void showMenu(View view)
//	{
//		if(isExpanded)
//		{
//			isExpanded = false;
//			menu.startAnimation(new CollapseAnimation(menu, 0, (int)(screenWidth * 0.7), 20));
//		}
//		else
//		{
//			isExpanded = true;
//			menu.startAnimation(new ExpandAnimation(menu, 0, (int)(screenWidth * 0.7), 20));
//		}
//	}
//	
//	@Override
//	public void onTabReselected(Tab tab, FragmentTransaction ft)
//	{
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void onTabSelected(Tab tab, FragmentTransaction ft)
//	{
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void onTabUnselected(Tab tab, FragmentTransaction ft)
//	{
//		// TODO Auto-generated method stub
//		
//	}
//	
//	public void onBackPressed()
//    {
//    	super.onBackPressed();
//    	overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
//    	finish();
//    }
//

}
