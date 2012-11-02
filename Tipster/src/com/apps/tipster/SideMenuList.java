package com.apps.tipster;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.google.api.client.util.Key;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class SideMenuList implements Serializable {
	
	@Key
	public String status;

	@Key
	public List<Place> results;
	
}
