package icreep.app;

import java.io.IOException;
import java.util.ArrayList;

import icreep.app.db.iCreepDatabaseAdapter;
import android.app.ActionBar;
import icreep.app.location.UserLocation;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class ProfileCreationActivity extends Activity
{
	private static final int ENABLE_BLUETOOTH_REQUEST = 1;
	private static final int IMAGE_PICKER_SELECT = 999;
	
	private Button save_button;
	private ImageButton home_button;
	private ImageView profilePicture ;
	private Bitmap originalProfile= null ;
	private Bitmap profilePic = null ;
	// Views to extract user details from
	private EditText userName, userSurname, userPosition, userEmail;
	private ArrayList<String> listDetails  = null;

	private int userID = -1;
	// Create db helper object
	private iCreepDatabaseAdapter icreepHelper;
	private SharedPreferencesControl spc;
	
	private UserLocation user;
	private ICreepApplication mApplication;
	
	private String invalidEntry;
	private int chars;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile_creation);
		ActionBar actionBar = getActionBar();
		actionBar.removeAllTabs();
		
		actionBar.setDisplayHomeAsUpEnabled(false);
		actionBar.setDisplayShowHomeEnabled(true);
		actionBar.setHomeButtonEnabled(false);
		user = new UserLocation(this);
		mApplication = (ICreepApplication) getApplicationContext();

		// Testing to see if the shared pref exists, thus disable home button
		// ect.
		profilePicture = (ImageView) findViewById(R.id.imageView1_profile_picture);
		profilePicture.setOnClickListener(new OnClickListener()
		{
			
			@Override
			public void onClick(View v)
			{
		        Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		        startActivityForResult(i, IMAGE_PICKER_SELECT);
			}
		});
		
		
		spc = new SharedPreferencesControl(this);
		home_button = (ImageButton) findViewById(R.id.home_button_profile);
		if (spc.hasNoUser() == true) {
			home_button.setEnabled(false);
			home_button.setVisibility(View.INVISIBLE);
		} else {
			home_button.setEnabled(true);
			home_button.setVisibility(View.VISIBLE);
			home_button.setOnClickListener(new SwitchButtonListener(this,
					"icreep.app.MainMenuActivity"));
			userID = spc.getUserID();
		}

		// Prevents the keyboard from automatically popping up
		this.getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		
		// initializing of all views
		userName = (EditText) findViewById(R.id.editText1_user_name);
		userSurname = (EditText) findViewById(R.id.editText2_user_surname);
		userPosition = (EditText) findViewById(R.id.editText4_user_position);
		userEmail = (EditText) findViewById(R.id.editText3_user_email);
		
		userName.setText("");
		userSurname.setText("");
		userPosition.setText("");
		userEmail.setText("");
		// rename helper for db management
		icreepHelper = new iCreepDatabaseAdapter(this);
		
		if (userID != -1) {
			// set default values >>> from DB
			listDetails= icreepHelper.userDetails(userID);
		}
		if ((listDetails == null) && (userID != -1))
		{							
			doMessage("No details were obtained about the user, will default to blank details");
		}
		
		if ((listDetails == null))
		{		
			listDetails = new ArrayList<String>();
			for (int i = 0; i < 4; i++) 
			{
				listDetails.add("");
			}
		}else 
		{
			userName.setText(listDetails.get(0));
			userSurname.setText(listDetails.get(1));
			userPosition.setText(listDetails.get(2));
			userEmail.setText(listDetails.get(3));
			if (spc.sharedProfilePicTest() == true)
			{
			BitmapController bmc = new BitmapController();
			originalProfile = bmc.getbitmapImage(); // might need resizing
			profilePicture.setImageBitmap(originalProfile);
			}
		}
		
		save_button = (Button) findViewById(R.id.button2_save_user_details);
		save_button.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// get user details
				String name = userName.getText().toString();
				String surname = userSurname.getText().toString();
				String position = userPosition.getText().toString();
				String email = userEmail.getText().toString();
				// must still add check for profile picture
				if ((name.equals(listDetails.get(0)) == true)
					&& (surname.equals(listDetails.get(1)) == true)
					&& (position.equals(listDetails.get(2)) == true)
					&& (email.equals(listDetails.get(3)) == true)
					&& (originalProfile == profilePic) == true) 
				{

					doMessage("You have made no changes, thus you can't save");

					return;
				}
				
				if ((name.equals("") == true) || (surname.equals("") == true)
						|| (position.equals("") == true)
						|| (email.equals("") == true)) {
					doMessage("One of your details isn't valid as it's blank");
					return;
				}

				// before entering user into DB - can send or validate email
				// first
				// if validation email bounces than user not entered in DB else
				// add to DB
				if (userID == -1) {
					doNewInsertionOfData(name, surname, position, email);
				} else 
				{
					doUpdateOfProfile(name, surname, position, email);
				}
			}
		});

	}// onCreate
	
	@Override
	protected void onResume() {
		super.onResume();
		
		registerBluetoothReceiver();

		BluetoothAdapter bluetoothAdapter = BluetoothAdapter
				.getDefaultAdapter();
		if (bluetoothAdapter == null || !bluetoothAdapter.isEnabled()) {
			Intent enableBtIntent = new Intent(
					BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(enableBtIntent, ENABLE_BLUETOOTH_REQUEST);
		}
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		
		unregisterBluetoothReceiver();
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		user.updateLocationOnDestroy(mApplication.getCurrentLocation(), mApplication.getLastEntryID());
		
	}
	
	public void doUpdateOfProfile(String name, String surname,
			String position,String email) {
		
		if (checkDetails(name, surname, position, email)) {
			if (isValidEmail(email)) {
				if (isValidInput(name)) {
					if (isValidInput(surname)) {
						if (isValidInput(position)) {
							if (icreepHelper.updateUserDetails(name, surname,
									position, email, "profilePic.png", userID) == false) // will
																							// add
																							// the
																							// correct
																							// pp
																							// name
																							// later
							{
								doMessage("Update unsuccessful, please contact Admin");
							} else {
								if (profilePic != null) {
									BitmapController bmc = new BitmapController();
									bmc.storeImage(profilePic);
									originalProfile = profilePic;
									if (spc.sharedProfilePicTest() == false) {
										spc.writeProfilePicName("profilePic.png");
									}
								}
								listDetails.clear();
								listDetails.add(name);
								listDetails.add(surname);
								listDetails.add(position);
								listDetails.add(email);
								doMessage("Update successful");
							}
						} else {
							doMessage("Invalid position - please enter valid employee position.");
							doMessage("Example: Developer or Aanalys Developer");
						}
					} else {
						doMessage("Invalid surname - surname cannot contain any other special characters other than spaces and hyphens (-).");
					}
				} else {
					doMessage("Invalid name - name cannot contain any other special characters other than spaces and hyphens (-).");
				}
			} else {
				doMessage("Invalid email address - please use a valid email address");
				doMessage("Example: user1@gmail.com");
			}
		} else {
			doMessage("Invalid " + invalidEntry + " - " + invalidEntry
					+ " length cannot exceed " + chars + " characters.");
		}
	}

	
	public void doNewInsertionOfData(String name, String surname,
			String position, String email)
	{
		if (checkDetails(name, surname, position, email)) {
			if (isValidEmail(email)) {
				if (isValidInput(name)) {
					if (isValidInput(surname)) {
						if (isValidInput(position)) {
							long id = icreepHelper.enterNewUser(name, surname,
									position, email, "profilePic.png"); // default
																		// profile
																		// pic
																		// filename

							// check if insertion was successful
							if (id > 0) {
								if (profilePic != null) {
									BitmapController bmc = new BitmapController();
									bmc.storeImage(profilePic);
									originalProfile = profilePic;
									spc.writeProfilePicName("profilePic.png");
								}

								doMessage("User details saved");
								spc.writeNewUserID((int) id);
								iCreepDatabaseAdapter.createZone();
								switchToMainMenu();
							} else {
								doMessage("User details not saved, please contact Admin");
								return;
							}
						} else {
							doMessage("Invalid employee position, please enter valid employee position");
							doMessage("example: Developer or Analyst");
						}
					} else {
						doMessage("Invalid surname - surname cannot contain any other special characters other than spaces and hyphens (-). Please enter a valid surname");
					}
				} else {
					doMessage("Invalid name - name cannot contain any other special characters other than spaces and hyphens (-). Please enter a valid name");
				}
			} else {
				doMessage("Invalid email address, please use a valid email address");
				doMessage("Example: user1@gmail.com");
			}
		} else {
			doMessage("Invalid " + invalidEntry + " - " + invalidEntry
					+ " length cannot exceed " + chars + " characters");
		}
	}

	//validate entered details character lengths
	public boolean checkDetails(String name, String surname, String position, String email){
		String[] checks = {name,surname,position, email};
		String[] holders = {"name", "surname", "Employee Position","Email address"};
		int[] lengths = {30,30,30,255};
				
		for(int i=0; i<checks.length; i++){
			if(checks[i].length() > lengths[i]){
				invalidEntry = holders[i];
				chars = lengths[i];
				return false;				
			}
		}		
		return true;
	}
	
	// function to validate email addresses against an email Regular Expression
	public boolean isValidEmail(String email)
	{
		String emailRegex = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

		if (email.matches(emailRegex)) {
			return true;
		}
		return false;
	}
	
	// function to validate name against a name Regular Expression
	public boolean isValidInput(String name){
		//if(!(name.get(0).charAt()).isDigit())
		//if true - Capitalize first char of every word and then check against regex
		String nameRegex = "[a-zA-z]+([ '-][a-zA-Z]+)*";
		if(name.matches(nameRegex)){
			return true;
		}
		return false;
	}

	/*
	// function to validate employee position against a employee position Regular Expression
	public boolean isValidPosition(String position){
		String employeePosRegex = "[a-zA-z]+([ '-][a-zA-Z]+)*";
		if(position.matches(employeePosRegex)){
			return true;
		}
		return false;
	}*/

	public void doMessage(String mess)
	{
		Message.message(this, mess);
	}

	private void switchToMainMenu()
	{
		// Go to main menu if insertion is successful
		// apon testing, this might not work...so thus my alternative is
//		save_button = (Button) findViewById(R.id.button2_save_user_details);
//		save_button.setOnClickListener(new SwitchButtonListener(this,
//				"icreep.app.MainMenuActivity"));		
		Intent i = new Intent();
		i.setClassName(this, "icreep.app.MainMenuActivity");
		startActivity(i);
	}	
	
	// need to do some test to defensive code against failed attempts at selecting a picture
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == IMAGE_PICKER_SELECT  && resultCode == Activity.RESULT_OK) {
            Bitmap bitmap = getBitmapFromCameraData(data, this);
            //first have to force image to be horizontal
            if (bitmap == null)
            {
            	Message.message(this, "Sorry the file you selected isn't a picture");
            	return ;
            }
            //ExifInterface exif = new ExifInterface(bitmap);
            //You can then grab the orientation of the image:
            //double orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
            
            profilePicture.setImageBitmap(bitmap);
            profilePic = bitmap ;
        }
        
        switch (requestCode)
		{
			case ENABLE_BLUETOOTH_REQUEST:
				if (resultCode != Activity.RESULT_OK) {
					finishActivityWithMessage("Bluetooth must be on");
				}
				break;
			default:
				break;
		}
    }
	
	private static Bitmap getBitmapFromCameraData(Intent data, Context context){
        Uri selectedImage = data.getData();
        String[] filePathColumn = { MediaStore.Images.Media.DATA };
        Cursor cursor = context.getContentResolver().query(selectedImage,filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String picturePath = cursor.getString(columnIndex);
        cursor.close();
     
        
        //check that the file is picture!!!        
        String[] checker = picturePath.split("\\.");
        if ((checker[1].equals("jpg")) || (checker[1].equals("png")) || (checker[1].equals("GIF")) || 
        		(checker[1].equals("tif")) || (checker[1].equals("JPG")) || (checker[1].equals("PNG")) 
        		|| (checker[1].equals("gif")))
        {
        	double orientation = -1 ;
        	 try {
				ExifInterface exif = new ExifInterface(picturePath);
				//You can then grab the orientation of the image:
	            orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
			} catch (IOException e) {
				e.printStackTrace();
			}
        	Bitmap generated = BitmapFactory.decodeFile(picturePath);
        	Bitmap finalBit = null ;
        	Matrix matrix = new Matrix();
            matrix.postRotate(90);
            
            // http://sylvana.net/jpegcrop/exif_orientation.html >> USEFUL
            if (orientation == 6)
            {            	
                finalBit = Bitmap.createBitmap(generated,0,0,generated.getWidth(),generated.getHeight(),matrix,true);
            }else if (orientation == 8)
            {
            	matrix.postRotate(-180);
                finalBit = Bitmap.createBitmap(generated,0,0,generated.getWidth(),generated.getHeight(),matrix,true);
            }else if (orientation == 3)
            {
            	matrix.postRotate(-90);
            	finalBit = Bitmap.createBitmap(generated,0,0,generated.getWidth(),generated.getHeight(),matrix,true);
            }
            else
            {
            	finalBit = generated;            	
            }
            finalBit = Bitmap.createScaledBitmap(finalBit, 320, 240, false);
        	return finalBit;
        }
        
        return null ;
    }
	
	/*********************
	 * 
	 * Bluetooth Checker Makes Sure the Bluetooth functionality is on. May need
	 * to move it into every activity
	 * 
	 ********************/
	private void finishActivityWithMessage(String message)
	{
		// Notify the user of the problem
		Toast toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
		toast.show();

		// End the activity
		finish();
	}

	private void registerBluetoothReceiver()
	{
		final IntentFilter filter = new IntentFilter();
		filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);

		registerReceiver(this.bluetoothChangedReceiver, filter);
	}

	private void unregisterBluetoothReceiver()
	{
		unregisterReceiver(this.bluetoothChangedReceiver);
	}

	private BroadcastReceiver bluetoothChangedReceiver = new BroadcastReceiver()
	{

		@Override
		public void onReceive(Context context, Intent intent)
		{

			final String action = intent.getAction();
			if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
				final int state = intent.getIntExtra(
						BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR);
				switch (state)
				{
					case BluetoothAdapter.STATE_TURNING_ON:
						break;
					case BluetoothAdapter.STATE_ON:
						break;
					case BluetoothAdapter.STATE_TURNING_OFF:
						finishActivityWithMessage("Requires Bluetooth");
						break;
					case BluetoothAdapter.STATE_OFF:
						break;
					case BluetoothAdapter.ERROR:
						finishActivityWithMessage("Bluetooth Error");
						break;
				}
			}
		}
	};
	
	@Override
	public void onBackPressed() 
	{
		if (userID != -1)
		{
	    Intent myIntent = new Intent(this, MainMenuActivity.class);
	    startActivity(myIntent);
	    super.onBackPressed();
		}
	}
}
