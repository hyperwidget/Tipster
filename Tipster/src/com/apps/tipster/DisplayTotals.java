package com.apps.tipster;

import java.text.NumberFormat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;

public class DisplayTotals extends TipsterActivity {
	
	private double value = 15.00;
	private double billAmount = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_display_total);
	    Intent intent = getIntent();
	    value = intent.getDoubleExtra("percent", 0);
		Tipster global = (Tipster)getApplicationContext();
		global.setTipAmount(value);
		
	    ActionBar actionBar = getSupportActionBar();
	    actionBar.setTitle(R.string.tipsterSays);
	    
	    TextView showAmount = (TextView)findViewById(R.id.showAmount);
	    billAmount = global.getBillAmount();
		showAmount.setText( "$" + Double.toString(Math.round((value/100 + 1) * billAmount *100.0)/100.0));   
	}    
	
	public void paymentTotal(){
		NumberFormat format = NumberFormat.getCurrencyInstance();		
		
		TextView finalMessage = (TextView)findViewById(R.id.finalMessage);
		TextView tipMessage = (TextView)findViewById(R.id.textView1);
		TextView tipValue = (TextView)findViewById(R.id.textView3);
		TextView txtPerson = (TextView)findViewById(R.id.textView4);
		TextView txtPeople = (TextView)findViewById(R.id.textView5);
		Spinner numSpin = (Spinner)findViewById(R.id.numberSpinner);
	    ImageButton shareButton = (ImageButton)findViewById(R.id.button2);
		int people = numSpin.getSelectedItemPosition() + 1;
		
		tipValue.setText(Double.toString(Math.round(value*100.0)/100.0) + "% or " + format.format(Math.round((value/100) * billAmount*100.0)/100.0));
		double finalAmount = Math.round((value/100 + 1) * billAmount *100.0)/100.0/people ;
		
		if(people == 1){
			txtPeople.setText("person");
			finalMessage.setText("Your total after tip is : ");
		} else {
			txtPeople.setText("people");
			finalMessage.setText("Each person's share is : ");
		}
		
		tipMessage.setVisibility(View.VISIBLE);
		finalMessage.setVisibility(View.VISIBLE);
		txtPerson.setVisibility(View.VISIBLE);
		numSpin.setVisibility(View.VISIBLE);
		txtPeople.setVisibility(View.VISIBLE);
		shareButton.setVisibility(View.VISIBLE);
	}
	
	public void detailsView(View view){		
		DetailsFragment details = new DetailsFragment(DisplayTotals.this);
		details.show();
	}
	
	public void facebookShare(View view){
		Intent intent = new Intent(this, FacebookPost.class);
		startActivity(intent);
	}	
}
