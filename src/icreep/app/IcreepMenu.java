package icreep.app;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.os.Build;
import icreep.app.R;
import icreep.app.Location.LocationActivity;
import icreep.app.Report.reportActivity;

public class IcreepMenu extends Activity {
	
	Button proximity_button, time_tracker_button, reports_button, profile_button;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_icreep_menu);
		addListenerOnButton();

	}//onCreate
	
	private void addListenerOnButton() {
		
		final Context context = this;
		 
		proximity_button = (Button) findViewById(R.id.button1);
		time_tracker_button = (Button) findViewById(R.id.button2);
		reports_button = (Button) findViewById(R.id.button3);
		reports_button = (Button) findViewById(R.id.button4);		
 
		proximity_button.setOnClickListener(new OnClickListener() {
 
			@Override
			public void onClick(View arg0) {
 
			    Intent intent = new Intent(context, LocationActivity.class); //Correct proximity class needed here
                            startActivity(intent);   
 
			}//onClick
 
		});
		
		time_tracker_button.setOnClickListener(new OnClickListener() {
			 
			@Override
			public void onClick(View arg0) {
 
			    Intent intent = new Intent(context, LocationActivity.class); //Correct time tracker class needed here
                            startActivity(intent);   
 
			}//onClick
 
		});
		
		reports_button.setOnClickListener(new OnClickListener() {
			 
			@Override
			public void onClick(View arg0) {
 
			    Intent intent = new Intent(context, reportActivity.class); //Correct reports class needed here
                            startActivity(intent);   
 
			}//onClick
 
		});
		
		profile_button.setOnClickListener(new OnClickListener() {
			 
			@Override
			public void onClick(View arg0) {
 
			    Intent intent = new Intent(context, ProfileCreation.class); 
                            startActivity(intent);   
 
			}//onClick
 
		});
		
	}//addListenerOnButton
	 
////////////////////////////////////////////////////////////////////////////////////////////////	
	/*
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.icreep_menu, menu);
		return true;
		
	}//onCreateOptionsMenu 

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		
		if (id == R.id.action_settings) {
			
			return true;
			
		}//if
		
		return super.onOptionsItemSelected(item);
		
	}//onOptionsItemSelected
	*/

	/**
	 * A placeholder fragment containing a simple view.
	 */
	
	/*
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_icreep_menu,
					container, false);
			return rootView;
		}
	}
*/
	
}//IcreepMenu class
