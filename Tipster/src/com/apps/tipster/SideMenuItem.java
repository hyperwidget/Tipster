package com.apps.tipster;

import java.io.Serializable;

import android.graphics.drawable.Drawable;

public class SideMenuItem implements Serializable {
	
	public Drawable menuIcon;	
	public String menuText;
	
	public SideMenuItem(){
		//EMPTY CONSTRUCTOR
	}
	
	public SideMenuItem(Drawable menuIconIn, String menuTextIn){
		menuText = menuTextIn;
		menuIcon = menuIconIn;
	}	

}
