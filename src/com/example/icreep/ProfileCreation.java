package com.example.icreep;

import com.example.dummyapp.R;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.os.Build;
import android.content.Intent;


public class ProfileCreation extends Activity {
	
    public void uploadImage(View view){
    	
    	
    	
    }//uploadImage
	
	public void saveDetails(View view){
    	
		
		EditText name = (EditText) findViewById(R.id.editText1_user_name);
		EditText surname = (EditText) findViewById(R.id.editText2_user_surname);
		EditText email = (EditText) findViewById(R.id.editText3_user_email);
		EditText position = (EditText) findViewById(R.id.editText4_user_position);
		
		String sname = name.getText().toString(); 
		String ssurname = surname.getText().toString(); 
		String semail  = email.getText().toString();
		String sposition  = position.getText().toString();
		
    }//saveDetails method
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile_creation);

		/*
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
					
		}
	*/	
		
	}//onCreate method


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);	
		
	}//onOptionsItemSelected method
	
	
	

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
			View rootView = inflater.inflate(
					R.layout.fragment_profile_creation, container, false);
			return rootView;
		}
		
		}
		*/

}
