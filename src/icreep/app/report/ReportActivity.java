package icreep.app.report;

import icreep.app.R;
import icreep.app.R.color;
import icreep.app.R.id;
import icreep.app.R.layout;
import icreep.app.R.menu;
import icreep.app.SwitchButtonListener;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Layout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class ReportActivity extends FragmentActivity {

	Button auto, manual;
	ImageButton home;
	boolean automated = true;

	/*
	 * Pre-Conditions: A bundle in case we need a saved state if we switch
	 * screens and want to keep old information The trigger is that you have
	 * switched to this activity using an intent and now this activity is
	 * created Post-conditions: > This will set all the listeners for the
	 * different views > This will ensure the correct sizing of views and their
	 * text as a function of the screen density > This ensures the logical flow
	 * of events and will cover the different combination of inputs
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reports);
		auto = (Button) findViewById(R.id.autoButton);
	        manual = (Button) findViewById(R.id.manualButton);
	        home = (ImageButton) findViewById(R.id.home_button_report);
		TextView reports = (TextView) findViewById(R.id.textViewMain);
		TextView userDescrip = (TextView) findViewById(R.id.userDescript);
		String foruserdesc = buildUserDescription();
		// will do this userDescrip.setText(foruserdesc) ;

		float correctTextpixel = 16 * getResources().getDisplayMetrics().density;
		reports.setTextSize(correctTextpixel);
		userDescrip.setTextSize(correctTextpixel);
		auto.setTextSize(correctTextpixel);
		manual.setTextSize(correctTextpixel);

	        // This is called because we want this fragment to be added auto when we create this activity
		// create this activity
		addautoFragment(savedInstanceState);

	        // Adding button handler for the auto report
		auto.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				switchtoautofrag();
				// need to change the style to the button_style_blue
				manual.setBackgroundColor(getResources().getColor(
						R.color.greyForBackrounds));
				auto.setBackgroundColor(getResources().getColor(
						R.color.lightGreenForLabels));

			}
		});

	        // Adding button handler for the manual report
		manual.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				switchtomanualfrag();
				// need to change the style to the button_style_blue
				manual.setBackgroundColor(getResources().getColor(
						R.color.lightGreenForLabels));
				auto.setBackgroundColor(getResources().getColor(
						R.color.greyForBackrounds));
				
			}
		});
	        
	        // Adding home button listener
	        home.setOnClickListener(new SwitchButtonListener(this, "icreep.app.IcreepMenu"));

	}
	
	
	
	/*
	 * Pre-Conditions: none
	 * 
	 * Post-conditions: This will give us access to the auto fragment in case we
	 * need information from it.
	 */
	public ReportAutoFragment gainAccessToAutoFragment() {
		ReportAutoFragment fragment = (ReportAutoFragment) getSupportFragmentManager()
				.findFragmentById(R.id.autoFragLayout);

		return fragment;
	}

	/*
	 * Pre-Conditions: none
	 * 
	 * Post-conditions: This will give us access to the manual fragment in case
	 * we need information from it.
	 */
	public ReportManualFragment gainAccessToManualFragment() {
		ReportManualFragment fragment = (ReportManualFragment) getSupportFragmentManager()
				.findFragmentById(R.id.reportManualLayout);
		return fragment;
	}

	/*
	 * Pre-Conditions: none
	 * 
	 * Post-conditions return a string that will get the users name and surname
	 * together with his/her job position to develop our interface for reports
	 */
	public String buildUserDescription() {

		return "";
	}

	/*
	 * Pre-Conditions: A bundle in case we need a saved state if we switch
	 * screens and want to keep old information The trigger is that you need the
	 * default fragment to be added when this activity is created
	 * Post-conditions: > This will add the auto fragment to the activity > this
	 * will ensure that old fragment data is kept and that the view for the
	 * activity is still active
	 */
	public void addautoFragment(Bundle save) {
		Log.e("vince", "" + (R.id.autoFragLayout));
		View t = findViewById(R.id.report);
		if (t != null) {
			Log.e("vince", "In here");
			if (save != null) {
				return;
			}
			// Create a new Fragment to be placed in the activity layout
			// always ensure that you do the correct important!! LESSON LEARNT !
			ReportAutoFragment frag = new ReportAutoFragment();

			FragmentManager fragmentManager = getSupportFragmentManager();
			FragmentTransaction fragmentTransaction = fragmentManager
					.beginTransaction();

			fragmentTransaction.add(R.id.report, frag, "auto");
			fragmentTransaction.commit();
		}
	}

	/*
	 * Pre-Conditions: This method is invoked when the manual button is pressed
	 * requires no parameters
	 * 
	 * Post-conditions: > this will remove the old fragment which will be the
	 * auto fragment > switch it to the manual fragment > this is done via
	 * transaction > never forget commits
	 */
	public void switchtomanualfrag() {

		Fragment frag = new ReportManualFragment();
		FragmentTransaction transaction = getSupportFragmentManager()
				.beginTransaction();
		transaction.addToBackStack(null);
		// Replace whatever is in the fragment_container view with this
		// fragment,
		// and add the transaction to the back stack
		transaction.replace(R.id.report, frag);
		transaction.addToBackStack(null);

		// Commit the transaction
		transaction.commit();
	}

	/*
	 * Pre-Conditions: This method is invoked when the auto button is pressed
	 * requires no parameters
	 * 
	 * Post-conditions: > this will remove the old fragment which will be the
	 * manual fragment > switch it to the auto fragment > this is done via
	 * transaction > never forget commits
	 */
	public void switchtoautofrag() {
		ReportAutoFragment frag = new ReportAutoFragment();
		FragmentTransaction transaction = getSupportFragmentManager()
				.beginTransaction();
		transaction.addToBackStack(null);
		// Replace whatever is in the fragment_container view with this
		// fragment,
		// and add the transaction to the back stack
		transaction.replace(R.id.report, frag);
		transaction.addToBackStack(null);

		// Commit the transaction
		transaction.commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

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

	}

	@Override
	protected void onStart() {
		super.onStart();
		Log.d("vince", "onStart");
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.d("onResume", "onResume");
	}

	@Override
	protected void onPause() {
		super.onPause();
		Log.d("onPause", "onPause");
	}

	@Override
	protected void onStop() {
		super.onStop();
		Log.d("vince", "onStop");
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		Log.d("onRestart", "onRestart");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.d("vince", "onDestroy");
	}

	@Override
	protected void onSaveInstanceState(Bundle out) {
		super.onSaveInstanceState(out);

	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onRestoreInstanceState(savedInstanceState);

	}

}
