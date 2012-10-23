package com.apps.tipster;

import android.app.Application;

public class Tipster extends Application {
	private String locationName;
	
	public String getLocationName(){
		return locationName;		
	}
	
	public void setLocationName(String name){
		locationName = name;
	}
}
