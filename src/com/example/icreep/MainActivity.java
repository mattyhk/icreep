package com.example.icreep;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
			
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
		// comment
	}

	@Override
	protected void onStart() {
		super.onStart();
		Log.d("onStart", "onStart");
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
		
	//upload image	
	public void uploadImage(View view){
		//String photo = userPhoto...
	}
	
}
