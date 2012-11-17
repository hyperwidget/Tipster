package com.apps.tipster;

import android.app.Application;

public class Tipster extends Application {
	private String locationName;
	private Double tipAmount;
	private Boolean returnTrip;
	private Double billAmount;
	
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
	
	public void setBillAmount(Double billIn){
		if(billIn != null){
			billAmount = billIn;
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
	
	public Double getBillAmount(){
		return billAmount;
	}

	public Boolean getReturnTrip(){
		return returnTrip;
	}
}
