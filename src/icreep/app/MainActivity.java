package icreep.app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;
import icreep.app.R;

public class MainActivity extends FragmentActivity {
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		SharedPreferences sp = getSharedPreferences("iCreepData", Context.MODE_PRIVATE);
		String user = sp.getString("userID", "");
		if (user.equals(""))
		{
			Intent i = new Intent();
			i.setClassName(this, "icreep.app.ProfileCreationActivity");
			//i.putExtra("sharedPreferFileName", "iCreepData"); //seems unnecessary since we are defining the iCreepData File
			startActivity(i);
//			SharedPreferences.Editor editor = sp.edit();
//			editor.putString("userID", "vincent");			
//			editor.commit();
//			makeToast("YAY SAVED NEW USER");			
		}else
		{
		Intent i = new Intent();
		i.setClassName(this, "icreep.app.IcreepMenu");
		startActivity(i);
		}
		// Check for Bluetooth capability
//		if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
//			finishActivityWithMessage("Device does not support Bluetooth LE");
//		}
		
		
	}
	
	
	
	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	protected void onRestart() {
		super.onRestart();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
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
	
	private void finishActivityWithMessage(String message)
	{
		// Notify the user of the problem
		Toast toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
		toast.show();

		// End the activity
		finish();
	}

		
	
	
}
