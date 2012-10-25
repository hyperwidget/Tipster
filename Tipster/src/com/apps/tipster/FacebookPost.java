package com.apps.tipster;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookActivity;
import com.facebook.FacebookException;
import com.facebook.GraphUser;
import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.RequestAsyncTask;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;


public class FacebookPost extends FacebookActivity {
	private Facebook facebook;

	  @Override
	  public void onCreate(Bundle savedInstanceState) {
	      super.onCreate(savedInstanceState);
	      setContentView(R.layout.facebookpost);
	      this.openSession();
	  }

	  protected void onSessionStateChange(SessionState state, Exception exception) {
		    // user has either logged in or not ...
		    if (state.isOpened()) {
		    	// make request to the /me API
		        Request request = Request.newMeRequest(this.getSession(), new Request.GraphUserCallback() {
		        // callback after Graph API response with user object
		        public void onCompleted(GraphUser user, Response response) {
		          if (user != null) {
		        	  publishFeedDialog(user);
		          }
		        }
		      });
		      Request.executeBatchAsync(request);
		    }
		  }
	  
	  private void publishFeedDialog(GraphUser user) {
		  Session session = Session.getActiveSession();
		  Tipster global = (Tipster)getApplicationContext();
		  String location = global.getLocationName();
		  Boolean returnTrip = global.getReturnTrip();
		  Double tipAmount = global.getTipAmount();
		  //Log.d("Location", location);
		  String message = user.getFirstName() + " just ate";
		  if(location == null){
			  message += " out";
		  } else{
			  message += " at " + location;
		  }
		  if(returnTrip == null){
			  if(tipAmount > 15){
				  message += " and tipped very well!";
			  } else if(tipAmount <= 15 && tipAmount >10){
				  message += " and gave a pretty nice tip!";
			  } else if(tipAmount <= 10 && tipAmount > 5){
				  message += " and gave a lower than average tip.";
			  } else if(tipAmount <= 5){
				  message += " and gave a super low tip!";
			  }
		  } else if(returnTrip == true){
			  if(tipAmount > 15){
				  message += " tipped very well, and can't wait to return!";				  
			  } else if(tipAmount <= 15 && tipAmount >10){
				  message += " gave a very nice tip, and is looking forward to returning!";
			  } else if(tipAmount <= 10 && tipAmount > 5){
				  message += " tipped a little on the low side, but would still return.";
			  } else if(tipAmount <= 5){
				  message += " gave a very small tip, but would still go back.";
			  }
		  } else if(returnTrip == false){
			  if(tipAmount > 15){
				  message += " and tipped very well, but wouldn't return - why not?";
			  } else if(tipAmount <= 15 && tipAmount >10){
				  message += " and tipped decently, but won't be returning.";
			  } else if(tipAmount <= 10 && tipAmount > 5){
				  message += " gave a small tip, and wouldn't recommend that you go there.";
			  } else if(tipAmount <= 5){
				  message += " and gave a small tip reflecting their poor experience there!";
			  }
		  }
		  
		  message += " Why don't you ask them all about it?";
		  
		    Bundle params = new Bundle();
		    params.putString("name", "Tipster for Android");
		    params.putString("caption", "Tip with confidence with Tipster");
		    //params.putString("description", "The Facebook SDK for Android makes it easier and faster to develop Facebook integrated Android apps.");
		    params.putString("message", message);
		    params.putString("link", "https://www.facebook.com/calmlycoding");
		    params.putString("picture", "https://github.com/rawkamatic/Tipster/blob/master/Tipster/res/drawable-xhdpi/tipster_launcher.png?raw=true");
		    
		    Request.Callback callback= new Request.Callback() {
	            public void onCompleted(Response response) {
	                JSONObject graphResponse = response
	                                           .getGraphObject()
	                                           .getInnerJSONObject();
	                String postId = null;
	                try {
	                    postId = graphResponse.getString("id");
	                } catch (JSONException e) {
	                    Log.i("TEMP",
	                        "JSON error "+ e.getMessage());
	                }
	                FacebookException error = response.getError();
	                if (error != null) {
	                    Toast.makeText(FacebookPost.this
	                         .getApplicationContext(),
	                         error.getMessage(),
	                         Toast.LENGTH_SHORT).show();
	                    } else {
	                    	AlertDialog alertDialog = new AlertDialog.Builder(FacebookPost.this).create();
	                		alertDialog.setTitle("Success!");
	                		alertDialog.setMessage("The tale of your tipping has been shared to your facebook feed!");
	                		alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Return", new DialogInterface.OnClickListener() {
	                		   public void onClick(DialogInterface dialog, int which) {
	                		      finish();
	                		   }
	                		});
	                		
	                		alertDialog.show();
	                }
	            }
	        };
		    
		    Request request = new Request(session, "me/feed", params, 
                    HttpMethod.POST, callback);

			RequestAsyncTask task = new RequestAsyncTask(request);
			task.execute();
      }
		
}