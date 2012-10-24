package com.apps.tipster;

import com.actionbarsherlock.view.MenuItem;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;

public class ClassicMode extends Activity {
	
	public static final double VALUE = 42.85714285714286;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Tipster globalApp = ((Tipster)getApplicationContext());        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    public boolean onOptionsItemSelected(android.view.MenuItem item) {    	
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.rate:
                playStore();
                return true;
            case R.id.email:
                email();
                return true;
            case R.id.location:
            	locate();
            default:
                return super.onOptionsItemSelected(item);
        } 	
    }
    
    private void playStore(){
    	Uri uriUrl = Uri.parse("https://play.google.com/store/apps/details?id=com.apps.tipster"); 
    	Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
    	startActivity(launchBrowser); 
    }
    
    private void locate(){
		Intent intent = new Intent(this, Locate.class);
		startActivity(intent);
    }
    
    private void email(){
    	String aEmailList[] = { "CalmlyCoding@gmail.com"};  
    	Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND); 
    	emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, aEmailList);
    	emailIntent.setType("plain/text");    	
    	startActivity(emailIntent);
    }
    
    public void onClick(View view){
    	Intent displayTotal = new Intent(this, DisplayTotals.class);
    	RatingBar mealSpeed = (RatingBar)findViewById(R.id.rtngMealSpeed);
    	RatingBar mealTaste = (RatingBar)findViewById(R.id.ratingBar1);
    	RatingBar serverCourtesy = (RatingBar)findViewById(R.id.ratingBar2);
    	RatingBar serverRating = (RatingBar)findViewById(R.id.ratingBar3);
    	RatingBar serverRush = (RatingBar)findViewById(R.id.ratingBar4);
    	RatingBar estabClean = (RatingBar)findViewById(R.id.ratingBar5);
    	RatingBar estabInviting = (RatingBar)findViewById(R.id.ratingBar6);
    	RadioButton yes = (RadioButton)findViewById(R.id.radio0);
    	RadioButton no = (RadioButton)findViewById(R.id.radio1);
    	
    	double mealSpeedValue = mealSpeed.getRating()*VALUE, mealTasteValue = mealTaste.getRating()*VALUE, serverCourtesyValue = serverCourtesy.getRating()*VALUE,
    			serverRatingValue = serverRating.getRating()*VALUE, serverRushValue = serverRush.getRating()*VALUE, estabCleanValue = estabClean.getRating()*VALUE,
    			estabInvitingValue = estabInviting.getRating()*VALUE, finalReturnValue;
    	
    	double total = mealSpeedValue + mealTasteValue + serverCourtesyValue + serverRatingValue + serverRushValue + estabCleanValue + estabInvitingValue;;
    	if(yes.isChecked()){
    		finalReturnValue = 5;
    	} else{
    		finalReturnValue = 2;
    	}

    	total = Math.round(total+finalReturnValue*100.0)/100.0;
    	
    	displayTotal.putExtra("percent", total);
    	startActivity(displayTotal);
    	
    }
}
