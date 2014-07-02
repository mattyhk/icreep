package icreep.app;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import icreep.app.R;
import icreep.app.Location.LocationActivity;

public class IcreepMenu extends Activity {
	
	Intent intent;

    public void userProximity(View view){
    	
    	Intent intent = new Intent(this, LocationActivity.class);
        startActivity(intent);
    	
    }//selectProximity 

	public void timeTracker(View view){
		
		
	}//timeTracker 
	
	public void userReports(View view){
		
		
	}//userReports 
	
	public void userProfile(View view){
		
		
	}//userProfile 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_icreep_menu);
/*
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
			
		}//if
		*/
	}//onCreate 

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
}
