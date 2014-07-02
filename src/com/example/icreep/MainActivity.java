package com.example.icreep;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class MainActivity extends Activity {
	
	//views to extract user details from
	EditText userName, userSurname, userPosition, userEmail;
	//find way to get photo
	ImageView userPhoto;
	
	String photo="";
	
	//create db helper object
	iCreepDatabaseAdapter icreepHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		userName = (EditText) findViewById(R.id.editText1_user_name);
		userSurname = (EditText) findViewById(R.id.editText2_user_surname);
		userPosition = (EditText) findViewById(R.id.editText4_user_position);
		userEmail = (EditText) findViewById(R.id.editText3_user_email);
						
		//rename helper for db management
		icreepHelper = new iCreepDatabaseAdapter(this);		
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
	
	//listener to adddUser event - let's add new user to db	
	public void saveDetails(View v){
		//get user details
		String name = userName.getText().toString();
		String surname = userSurname.getText().toString();
		String position = userPosition.getText().toString();
		String email = userEmail.getText().toString();
		
		//before entering user into DB - can send validation email first
		//if validation email bounces than user not entered in tDB else add to DB
		
		long id = icreepHelper.enterNewUser(name, surname, position, email, photo);	
		
		//check if insertion was successful
		if(id<0){
			Message.message(this, "User details saved");
		}else{
			Message.message(this, "User not details saved");
		}
	}
}
