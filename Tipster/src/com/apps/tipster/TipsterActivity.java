package com.apps.tipster;

import java.util.ArrayList;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.apps.tipster.SimpleGestureFilter.SimpleGestureListener;
import com.devspark.appmsg.AppMsg;
import com.google.analytics.tracking.android.EasyTracker;
import com.slidingmenu.lib.app.SlidingActivity;

public class TipsterActivity extends SlidingActivity implements SimpleGestureListener {
	
	private SimpleGestureFilter detector;
	
	// SideMenuItems Listview
	ListView lv;
	// ListItems data
	ArrayList<SideMenuItem> sideMenuItems = new ArrayList<SideMenuItem>();
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashpage);
        

        
	    ActionBar actionBar = getSupportActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(true);
	    
	    detector = new SimpleGestureFilter(this,this);
	    
        setBehindContentView(R.layout.slide_menu);
        getSlidingMenu().setBehindOffsetRes(R.dimen.actionbar_home_width); 
        
        // Getting listview
     	lv = (ListView) findViewById(R.id.list);
     	
     	addSideMenuItems();
     	
		// list adapter
		ListAdapter adapter = new SideMenuAdapter(this, R.layout.side_menu_item, sideMenuItems);
		
		// Adding data into listview
		lv.setAdapter(adapter);
		
		//Set listener to listview
		lv.setOnItemClickListener(new OnItemClickListener(){

			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				TipsterActivity.this.toggle();
				Handler handler = new Handler(Looper.getMainLooper());
				switch(position){
				case 0:
					classicMode(parent);
				break;
				case 1:
					quickMode(parent);
				break;
				case 2:
					manualMode(parent);
				break;
				case 3:
					luckyMode(parent);
				break;
				case 4:
					locate();
				break;
				case 5:
	                playStore();					
				break;
				case 6:
					email();
				break;
				case 7:
					settings(parent);
					break;
				default:
					TipsterActivity.this.toggle();
				break;
				}
			}
			
		});
    }	
	
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//	   com.actionbarsherlock.view.MenuInflater inflater = getSupportMenuInflater();
//	   inflater.inflate(R.menu.activity_main, (com.actionbarsherlock.view.Menu) menu);
//	   return super.onCreateOptionsMenu(menu);
//    }
    
    //Onclick menu options
    @Override
    public boolean onOptionsItemSelected(MenuItem item) { 
    	
        // Handle item selection
        switch (item.getItemId()) {
			case android.R.id.home:
    			Intent home = new Intent(this, SplashPage.class);
    			startActivity(home);
			return true;

            default:
                return super.onOptionsItemSelected(item);
        } 	
    }
    
    //Opens the playstore for rating -- Menu Item
    private void playStore(){
    	Uri uriUrl = Uri.parse("https://play.google.com/store/apps/details?id=com.apps.tipster"); 
    	Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
    	startActivity(launchBrowser); 
    }
    
    //Brings up the locations list -- Menu Item
    private void locate(){
		Intent intent = new Intent(this, Locate.class);
		startActivity(intent);
    }
    
    // Opens the e-mail dialogue -- Menu Item
    private void email(){
    	String aEmailList[] = { "CalmlyCoding@gmail.com"};  
    	Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND); 
    	emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, aEmailList);
    	emailIntent.setType("plain/text");    	
    	startActivity(emailIntent);
    }
    
    // Opens the settings Dialogue
    public void settings(View view){
		SettingsFragment details = new SettingsFragment(TipsterActivity.this);
		details.show();
    }
	
	//Launches Classic mode
	public void classicMode(View view){
		Intent intent = new Intent(this, ClassicMode.class);
		startActivity(intent);
		this.finish();
	}
	
	//Launches Quick mode
	public void quickMode(View view){
		Intent intent = new Intent(this, QuickMode.class);
		startActivity(intent);
		}
	
	//Launches lucky mode
	public void luckyMode(View view){
		Intent intent = new Intent(this, LuckyMode.class);
		startActivity(intent);
	}
	
	//Launches Manual mode
	public void manualMode(View view){
		Intent intent = new Intent(this, ManualMode.class);
		startActivity(intent);
	}

	//Clears the firsttime Tag
	public void clear(View view){
    	SharedPreferences mPreferences = getPreferences(0);
    	SharedPreferences.Editor e = mPreferences.edit();
    	
    	e.putBoolean("firstTime", true);	
    	e.commit();
	}
    
	//This improves the swiping gestures
    @Override 
    public boolean dispatchTouchEvent(MotionEvent me){ 
      this.detector.onTouchEvent(me);
     return super.dispatchTouchEvent(me); 
    }
    
	public void onSwipe(int direction) {	  
		Tipster global = (Tipster)getApplicationContext();
	  if(direction == SimpleGestureFilter.SWIPE_RIGHT && !getSlidingMenu().isBehindShowing() && (global.getBillAmount()!=null)){
		  this.toggle();
	  }
	  else if(direction == SimpleGestureFilter.SWIPE_LEFT && getSlidingMenu().isBehindShowing()){
		  this.toggle();
		  Toast.makeText(this, "LEFT & CLOSED", Toast.LENGTH_SHORT);
	  }
	}

	public void onDoubleTap() {		}
	
	public void addSideMenuItems(){
		SideMenuItem temp1 = new SideMenuItem();
		SideMenuItem temp2 = new SideMenuItem();
		SideMenuItem temp3 = new SideMenuItem();
		SideMenuItem temp4 = new SideMenuItem();
		SideMenuItem temp5 = new SideMenuItem();
		SideMenuItem temp6 = new SideMenuItem();
		SideMenuItem temp7 = new SideMenuItem();
		SideMenuItem temp8 = new SideMenuItem();
		temp1.menuText = "Help me!";
		temp1.menuIcon = this.getResources().getDrawable(R.drawable.classic);
		sideMenuItems.add(temp1);
		temp2.menuText = "Just do it";
		temp2.menuIcon = this.getResources().getDrawable(R.drawable.quickpercent);
		sideMenuItems.add(temp2);
		temp3.menuText = "Manual Entry";
		temp3.menuIcon = this.getResources().getDrawable(R.drawable.xpercent);
		sideMenuItems.add(temp3);
		temp4.menuText = "I'm feeling lucky";
		temp4.menuIcon = this.getResources().getDrawable(R.drawable.lucky);
		sideMenuItems.add(temp4);
		temp5.menuText = "My Location";
		temp5.menuIcon = this.getResources().getDrawable(R.drawable.location);
		sideMenuItems.add(temp5);
		temp6.menuText = "Rate";
		temp6.menuIcon = this.getResources().getDrawable(R.drawable.star);
		sideMenuItems.add(temp6);
		temp7.menuText = "Contact";
		temp7.menuIcon = this.getResources().getDrawable(R.drawable.contact);
		sideMenuItems.add(temp7);
		temp8.menuText = "Settings";
		temp8.menuIcon = this.getResources().getDrawable(R.drawable.settings);
		sideMenuItems.add(temp8);
	}
	
	@Override
	  public void onStart() {
	    super.onStart();
	    //STUFFFFFF
	    EasyTracker.getInstance().activityStart(TipsterActivity.this); // Add this method.
	  }

	  @Override
	  public void onStop() {
	    super.onStop();
	    // The rest of your onStop() code.
	    EasyTracker.getInstance().activityStop(TipsterActivity.this); // Add this method.
	  }
	
}
