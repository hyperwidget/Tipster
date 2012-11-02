package com.apps.tipster;

import java.text.NumberFormat;
import java.util.Random;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

public class LuckyMode extends TipsterActivity {
	
	private double value;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.lucky_mode);
	    ActionBar actionBar = getSupportActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(true);
	    TextView message = (TextView)findViewById(R.id.finalMessage);
	    TextView tipMessage = (TextView)findViewById(R.id.textView1);
	    TextView txtPerson = (TextView)findViewById(R.id.textView4);
	    TextView txtPeople = (TextView)findViewById(R.id.textView5);
	    Spinner numSpin = (Spinner)findViewById(R.id.numberSpinner);
	    ImageButton shareButton = (ImageButton)findViewById(R.id.button2);
	    message.setVisibility(View.GONE);
	    tipMessage.setVisibility(View.GONE);
	    txtPerson.setVisibility(View.GONE);
	    txtPeople.setVisibility(View.GONE);
	    numSpin.setVisibility(View.GONE);
	    shareButton.setVisibility(View.GONE);
	    
	    
		Random generator = new Random();
		value = generator.nextDouble()*15+5;
		
		Tipster global = (Tipster)getApplicationContext();
		global.setTipAmount(value);
	    
	    //Spinner	    	    
	    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, 
	    		R.array.splitArray, android.R.layout.simple_spinner_item);
	    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);	    
	    numSpin.setAdapter(adapter);
	    MyOnItemSelectedListener spinListener = new MyOnItemSelectedListener();
	    numSpin.setOnItemSelectedListener(spinListener);
	
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
	
    public boolean onCreateOptionsMenu(Menu menu) {
    	MenuInflater inflater = getSupportMenuInflater();
        inflater.inflate(R.menu.activity_main, menu);
        return true;
    }
    
    public boolean onOptionsItemSelected(MenuItem item) {    	
        // Handle item selection
        switch (item.getItemId()) {
        	case android.R.id.home:
        		goHome();
        		return true;
            case R.id.rate:
                playStore();
                return true;
            case R.id.email:
                email();
                return true;
            case R.id.location:
            	locate();
            	return true;
            default:
                return super.onOptionsItemSelected(item);
        } 	
    }
    
    private void goHome(){
    	Intent intent = new Intent(this, SplashPage.class);
    	intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    	startActivity(intent);
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
    	String aEmailList[] = { "CalmlyCoding@gmail.com" };  
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
			TextView tipValue = (TextView)findViewById(R.id.textView3);
			TextView txtPerson = (TextView)findViewById(R.id.textView4);
			TextView txtPeople = (TextView)findViewById(R.id.textView5);
			Spinner numSpin = (Spinner)findViewById(R.id.numberSpinner);
		    ImageButton shareButton = (ImageButton)findViewById(R.id.button2);
			int people = numSpin.getSelectedItemPosition() + 1;
			
			tipValue.setText(Double.toString(Math.round(value*100.0)/100.0) + "% or " + format.format(Math.round((value/100) * Double.parseDouble(billAmount.getText().toString())*100.0)/100.0));
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
			shareButton.setVisibility(View.VISIBLE);
			
			InputMethodManager imm = (InputMethodManager)getSystemService(
				      Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(finalTotal.getWindowToken(), 0);
		}	
	}
	
	public void facebookShare(View view){
		Intent intent = new Intent(this, FacebookPost.class);
		startActivity(intent);
	}	
}
