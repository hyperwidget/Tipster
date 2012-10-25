package com.apps.tipster;

import android.app.Application;

public class Tipster extends Application {
	private String locationName;
	private Double tipAmount;
	private Boolean returnTrip;
	
	public String getLocationName(){
		return locationName;		
	}
	
	public void setLocationName(String name){
		locationName = name;
	}
	
	public void setTipAmount(Double tipIn){
		if(tipIn != null){
			tipAmount = tipIn;
		}
	}

	public void setReturnTrip(Boolean returnTripIn){
		if(returnTripIn != null){
			returnTrip = returnTripIn;
		}
	}

	public Double getTipAmount(){
		return tipAmount;
	}

	public Boolean getReturnTrip(){
		return returnTrip;
	}
}
