package com.apps.tipster;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;


public class FacebookPost extends Activity {


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.facebookpost);
	    //if (android.os.Build.VERSION.SDK_INT > 9) {
	    //    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	    //    StrictMode.setThreadPolicy(policy);
	    //  }
		
	}
}