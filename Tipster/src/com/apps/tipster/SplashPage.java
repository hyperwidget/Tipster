package com.apps.tipster;

import com.actionbarsherlock.app.SherlockActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

public class SplashPage extends SherlockActivity {
		
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashpage);
        
    	SharedPreferences mPreferences = getPreferences(0);
        boolean firstTime = mPreferences.getBoolean("firstTime", true);
        if (firstTime) { 
        	showFirstTimeMessage();
            SharedPreferences.Editor editor = mPreferences.edit();
            editor.putBoolean("firstTime", false);
            editor.commit();            
        }
        
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics ();
        display.getMetrics(outMetrics);

        float density  = getResources().getDisplayMetrics().density;
        float dpHeight = outMetrics.heightPixels;
        float dpWidth  = outMetrics.widthPixels;
        int buttonParams = (int) (dpWidth/2.3);
        ImageButton btn1 = (ImageButton)findViewById(R.id.ImageButton01);
        ImageButton btn2 = (ImageButton)findViewById(R.id.ImageButton02);
        ImageButton btn3 = (ImageButton)findViewById(R.id.ImageButton03);
        ImageButton btn4 = (ImageButton)findViewById(R.id.ImageButton04);
        android.view.ViewGroup.LayoutParams params = btn1.getLayoutParams();
        params.width = buttonParams;
        params.height = buttonParams;
        btn1.setLayoutParams(params);
        
        params = btn2.getLayoutParams();
        params.width = buttonParams;
        params.height = buttonParams;
        btn2.setLayoutParams(params);
        
        params = btn3.getLayoutParams();
        params.width = buttonParams;
        params.height = buttonParams;
        btn3.setLayoutParams(params);
        
        params = btn4.getLayoutParams();
        params.width = buttonParams;
        params.height = buttonParams;
        btn4.setLayoutParams(params);
    }
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
	   com.actionbarsherlock.view.MenuInflater inflater = getSupportMenuInflater();
	   inflater.inflate(R.menu.activity_main, (com.actionbarsherlock.view.Menu) menu);
	   return super.onCreateOptionsMenu(menu);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {    	
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
	
	private void showFirstTimeMessage() {
		AlertDialog alertDialog = new AlertDialog.Builder(this).create();
		alertDialog.setTitle("Welcome!");
		alertDialog.setMessage("I noticed this was your first time using Tipster, would you like a quick explanation?");
		alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Yes,  please!", new DialogInterface.OnClickListener() {
		   public void onClick(DialogInterface dialog, int which) {
		      startInstructions();
		   }
		});
		alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "No thank you", new DialogInterface.OnClickListener() {
			   public void onClick(DialogInterface dialog, int which) {
			      // here you can add functions
			   }
			});
		alertDialog.show();
	}
	
	public void startInstructions(){
		AlertDialog alertDialog = new AlertDialog.Builder(this).create();
		alertDialog.setTitle("A quick overview");
		alertDialog.setMessage("Tipster currently consists of three main modules:\n" +
				"Help Me! - a mode that asks you questions about your dining experience" +
				"and provides a value that you should tip based on your answers.\n");
		alertDialog.setButton(alertDialog.BUTTON_POSITIVE, "Next", new DialogInterface.OnClickListener() {
		   public void onClick(DialogInterface dialog, int which) {
			   secondInstructions();
		   }
		});
		alertDialog.setButton(alertDialog.BUTTON_NEGATIVE, "Skip", new DialogInterface.OnClickListener() {
			   public void onClick(DialogInterface dialog, int which) {
			   }
			});
		alertDialog.show();
	}
	
	public void secondInstructions(){
		AlertDialog alertDialog = new AlertDialog.Builder(this).create();
		alertDialog.setTitle("Not much further now...");
		alertDialog.setMessage("15% - This mode assigns a standard 15% tip to your bill\n" +
				"Lucky Mode - This mode randomly assigns a tip value from 0% to 20% and applies " +
				"it to your bill's total.");
		alertDialog.setButton(alertDialog.BUTTON_POSITIVE, "Next", new DialogInterface.OnClickListener() {
		   public void onClick(DialogInterface dialog, int which) {
		      lastInstructions();
		   }
		});
		alertDialog.setButton(alertDialog.BUTTON_NEGATIVE, "Back", new DialogInterface.OnClickListener() {
			   public void onClick(DialogInterface dialog, int which) {
				   startInstructions();
			   }
			});
		alertDialog.show();
	}
	
	public void lastInstructions(){
		AlertDialog alertDialog = new AlertDialog.Builder(this).create();
		alertDialog.setTitle("Some final thoughts");
		alertDialog.setMessage("Every mode allows you to split your bill between up to five people.\n" +
				"If you have suggestions for questions or feedback please provide it via Google Play comments or " +
				"email me at: calmlyCoding@gmail.com \n\n" +
				"Thank you again for trying Tipster!");
		alertDialog.setButton(alertDialog.BUTTON_POSITIVE, "Done", new DialogInterface.OnClickListener() {
		   public void onClick(DialogInterface dialog, int which) {
		      
		   }
		});
		alertDialog.setButton(alertDialog.BUTTON_NEGATIVE, "Back", new DialogInterface.OnClickListener() {
			   public void onClick(DialogInterface dialog, int which) {
				   startInstructions();
			   }
			});
		alertDialog.show();
	}

	public void classicMode(View view){
		Intent intent = new Intent(this, ClassicMode.class);
		startActivity(intent);
	}
	
	public void quickMode(View view){
		Intent intent = new Intent(this, QuickMode.class);
		startActivity(intent);
		}

	public void luckyMode(View view){
		Intent intent = new Intent(this, LuckyMode.class);
		startActivity(intent);
	}
	
	public void manualMode(View view){
		Intent intent = new Intent(this, ManualMode.class);
		startActivity(intent);
	}

	public void clear(View view){
    	SharedPreferences mPreferences = getPreferences(0);
    	SharedPreferences.Editor e = mPreferences.edit();
    	
    	e.putBoolean("firstTime", true);	
    	e.commit();
	}
	
}
