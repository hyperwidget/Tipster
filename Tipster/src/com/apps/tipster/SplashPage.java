package com.apps.tipster;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

public class SplashPage extends TipsterActivity {
	
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
		alertDialog.setMessage("Tipster currently has four main modules:\n" +
				"Help Me! - a mode that asks you questions about your dining experience" +
				"and advises you on how much you should tip based on your responses.\n");
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
		alertDialog.setMessage("15% - This mode assigns a standard 15% tip to your bill.\n" +
				"Manual Mode - Here you have full control of your tip amount and all of your totals. \n" +
				"Lucky Mode - This mode randomly assigns a tip value from 5% to 20% and applies " +
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
				"You can choose your current location by clicking the 'Find Location' button at the top of the " +
				"screen. \n" +
				"After you've determined your total including tip, you now have the option to share your story" +
				"via your facebook feed \n" +
				"If you have suggestions for questions or feedback please provide it via the app's menu" +
				", Google Play comments or email me at: calmlyCoding@gmail.com \n\n" +
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
}

