package com.apps.tipster;

import java.text.NumberFormat;
import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class ManualMode extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.manual_mode);
	    TextView message = (TextView)findViewById(R.id.finalMessage);
	    TextView tipMessage = (TextView)findViewById(R.id.textView1);
	    TextView txtPerson = (TextView)findViewById(R.id.textView4);
	    TextView txtPeople = (TextView)findViewById(R.id.textView5);
	    Spinner numSpin = (Spinner)findViewById(R.id.numberSpinner);
	    Spinner tipSpin = (Spinner)findViewById(R.id.tipSpin);
	    message.setVisibility(View.GONE);
	    tipMessage.setVisibility(View.GONE);
	    txtPerson.setVisibility(View.GONE);
	    txtPeople.setVisibility(View.GONE);
	    numSpin.setVisibility(View.GONE);
	    tipSpin.setVisibility(View.GONE);
	    
	    //SplitSpinner	    	    
	    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, 
	    		R.array.splitArray, android.R.layout.simple_spinner_item);
	    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);	    
	    numSpin.setAdapter(adapter);
	    MyOnItemSelectedListener spinListener = new MyOnItemSelectedListener();
	    numSpin.setOnItemSelectedListener(spinListener);
	    
	    adapter = ArrayAdapter.createFromResource(this, R.array.tipPercentage, 
	    		android.R.layout.simple_spinner_item);
	    tipSpin.setAdapter(adapter);
	    tipSpin.setOnItemSelectedListener(spinListener);
	
	    EditText entered = (EditText)findViewById(R.id.editText1);
	    entered.setOnEditorActionListener(
	            new EditText.OnEditorActionListener() {
	                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
	                    if (actionId == EditorInfo.IME_ACTION_DONE) {
	                        paymentTotal(v);
	                        return true;
	                    }
	                    return false;
	                }
	            });	    
	    
	    getWindow().setSoftInputMode(
	    		   WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
	    // TODO Auto-generated method stub
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
            default:
                return super.onOptionsItemSelected(item);
        } 	
    }
    
    private void playStore(){
    	Uri uriUrl = Uri.parse("https://play.google.com/store/apps/details?id=com.apps.tipster"); 
    	Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
    	startActivity(launchBrowser); 
    }
    
    private void email(){
    	String aEmailList[] = { "CalmlyCoding@gmail.com"};  
    	Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND); 
    	emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, aEmailList);
    	emailIntent.setType("plain/text");    	
    	startActivity(emailIntent);
    }
	
	public class MyOnItemSelectedListener implements OnItemSelectedListener{

		public void onItemSelected(AdapterView<?> parent, View view, int pos,
				long id) {
			//Recalculate final			
			paymentTotal(view);
		}

		public void onNothingSelected(AdapterView parent) {
			// Do Nothing			
		}		
	}

	public void paymentTotal(View view){
		EditText billAmount = (EditText)findViewById(R.id.editText1);			

		NumberFormat format = NumberFormat.getCurrencyInstance();		
		
		if(billAmount.getText().toString().length() == 0){
			billAmount.setError("You need to enter a bill in order for me to calculate");
		} else{
			TextView finalTotal = (TextView)findViewById(R.id.finalTotal);
			TextView finalMessage = (TextView)findViewById(R.id.finalMessage);
			TextView tipMessage = (TextView)findViewById(R.id.textView1);
			//TextView tipValue = (TextView)findViewById(R.id.textView3);
			TextView txtPerson = (TextView)findViewById(R.id.textView4);
			TextView txtPeople = (TextView)findViewById(R.id.textView5);
			Spinner numSpin = (Spinner)findViewById(R.id.numberSpinner);
			Spinner tipSpin = (Spinner)findViewById(R.id.tipSpin);
			int people = numSpin.getSelectedItemPosition() + 1;
			double value = tipSpin.getSelectedItemPosition() + 5;
			
			//tipValue.setText(Double.toString(Math.round(value*100.0)/100.0) + "% or " + format.format(Math.round((value/100) * Double.parseDouble(billAmount.getText().toString())*100.0)/100.0));
			double finalAmount = Math.round((value/100 + 1) * Double.parseDouble(billAmount.getText().toString())*100.0)/100.0/people ;
			
			if(people == 1){
				txtPeople.setText("person");
				finalMessage.setText("Your total after tip is : ");
			} else {
				txtPeople.setText("people");
				finalMessage.setText("Each person's share is : ");
			}
			finalTotal.setText((format.format(finalAmount)));
			tipMessage.setVisibility(View.VISIBLE);
			finalMessage.setVisibility(View.VISIBLE);
			txtPerson.setVisibility(View.VISIBLE);
			numSpin.setVisibility(View.VISIBLE);
			txtPeople.setVisibility(View.VISIBLE);
			tipSpin.setVisibility(View.VISIBLE);
			
			InputMethodManager imm = (InputMethodManager)getSystemService(
				      Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(finalTotal.getWindowToken(), 0);
		}		
	}
}
