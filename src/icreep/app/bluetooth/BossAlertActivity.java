package icreep.app.bluetooth;


import icreep.app.R;
import icreep.app.SharedPreferencesControl;
import icreep.app.SwitchButtonListener;
import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

public class BossAlertActivity extends Activity
{
	TextView bossTrackingValue;
	ListView listingoffoundvalues;
	Switch switched ;
	SharedPreferencesControl spc ;
	Button save ;
	int userID ;
	ImageButton home;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_boss);
		spc = new SharedPreferencesControl(this);
		bossTrackingValue = (TextView) findViewById(R.id.bossTrackingValue);
		switched = (Switch) findViewById(R.id.switchBarBoss);
		save = (Button) findViewById(R.id.bossSaveButton);
		userID = spc.getUserID();	
		listingoffoundvalues = (ListView) findViewById(R.id.boss_listview);
		
		home = (ImageButton)findViewById(R.id.home_button_boss);
		home.setOnClickListener(new SwitchButtonListener(this, "icreep.app.MainMenuActivity"));

	}
}
