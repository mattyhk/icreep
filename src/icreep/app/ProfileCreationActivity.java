package icreep.app;

import java.util.ArrayList;

import icreep.app.db.iCreepDatabaseAdapter;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

public class ProfileCreationActivity extends Activity
{

	private Button save_button;
	private ImageButton home_button;

	// Views to extract user details from
	EditText userName, userSurname, userPosition, userEmail;
	ArrayList<String> listDetails  = null;
	// Find way to get photo
	ImageView userPhoto;

	String photo = "";
	int userID = -1;
	// Create db helper object
	iCreepDatabaseAdapter icreepHelper;
	sharedPrefControl spc;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile_creation);

		// Testing to see if the shared pref exists, thus disable home button
		// ect.

		spc = new sharedPrefControl(this);
		home_button = (ImageButton) findViewById(R.id.home_button_profile);
		if (spc.sharedPrefTest() == true) {
			home_button.setEnabled(false);
			home_button.setVisibility(View.INVISIBLE);
		} else {
			home_button.setEnabled(true);
			home_button.setVisibility(View.VISIBLE);
			home_button.setOnClickListener(new SwitchButtonListener(this,
					"icreep.app.IcreepMenu"));
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

		// rename helper for db management
		icreepHelper = new iCreepDatabaseAdapter(this);
		
		if (userID != -1) {
			// set default values >>> from DB
			listDetails= icreepHelper.userDetails();
		}
		if ((listDetails == null) && (userID == -1))
		{							
			doMessage("No details were obtained about the user, will default to blank details");
		}
		
		if ((listDetails == null))
		{							
			for (int i = 0; i < 4; i++) 
			{
				listDetails.add("");
			}
		}		
		
		save_button = (Button) findViewById(R.id.button2_save_user_details);
		save_button.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				// get user details
				String name = userName.getText().toString();
				String surname = userSurname.getText().toString();
				String position = userPosition.getText().toString();
				String email = userEmail.getText().toString();
				// must still add check for profile picture
				if ((name.equals(listDetails.get(0)) == true)
					|| (surname.equals(listDetails.get(1)) == true)
					|| (position.equals(listDetails.get(2)) == true)
					|| (email.equals(listDetails.get(3)) == true)) 
				{
					if (listDetails.get(0).equals("") == true) 
					{
						doMessage("One of your inputs is still the default blank input, please correct");
					} else 
					{
						doMessage("You have made no changes, thus you can't save");
					}
					return;
				}
				/*
				if ((name.equals("") == false) || (surname.equals("") == false)
						|| (position.equals("") == false)
						|| (email.equals("") == false)) {
					doMessage("One of your details isn't valid as it's blank");
					return;
				}*/

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
	
	public void doUpdateOfProfile(String name, String surname,
			String position, String email)
	{
		if (icreepHelper.updateUserDetails(name, surname, position, email, "") == false) //will add the correct pp name later
		{
			doMessage("The updating of profile was unsuccessful, please contact admin");			
		}else
		{

			listDetails.clear();
			listDetails.add(name);
			listDetails.add(surname);
			listDetails.add(position);
			listDetails.add(email);
			doMessage("Updating of your proile was successful");
		}
	}
	
	public void doNewInsertionOfData(String name, String surname,
			String position, String email)
	{
		if (isValidEmail(email)) {
			long id = icreepHelper.enterNewUser(name, surname, position, email,
					photo);

			// check if insertion was successful
			if (id != -1 || id < 0) {
				doMessage("User details saved");
				spc.writeNewUserID((int)id);
				switchToMainMenu();
			} else {
				doMessage("User details not  saved, please contact ADMIN");
				return;
			}
		} else {
			doMessage("Invalid email address, please use valid email address");
			doMessage("example: user1@gmail.com");
			return;
		}
	}

	public void uploadImage(View view)
	{

		// Intent intent = new Intent(this, ProfilePicture.class);

		photo = "";

	}// uploadImage

	// listener to adddUser event - let's add new user to db
	/*
	 * public void saveDetails(View view){
	 * 
	 * //Intent intent = new Intent(this, SaveUserDetails.class);
	 * 
	 * //get user details String name = userName.getText().toString(); String
	 * surname = userSurname.getText().toString(); String position =
	 * userPosition.getText().toString(); String email =
	 * userEmail.getText().toString();
	 * 
	 * //before entering user into DB - can send or validate email first //if
	 * validation email bounces than user not entered in DB else add to DB
	 * if(isValidEmail(email)){ long id = icreepHelper.enterNewUser(name,
	 * surname, position, email, photo); //userID =(int) id ; //check if
	 * insertion was successful if(id != -1 || id < 0){ Message.message(this,
	 * "User details saved"); switchToMainMenu(); // I'm not sure that this
	 * would work, test >>> possibly needs direct intent, not listener }else{
	 * Message.message(this, "User details not  saved. Check the DB"); } } else{
	 * Message.message(this,"invalid email address"); }
	 * 
	 * }//saveDetails method
	 */

	// function to validate email addresses against an email Regular Expression
	public boolean isValidEmail(String email)
	{

		String emailRegex = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

		if (email.matches(emailRegex)) {
			return true;
		}
		return false;
	}

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
//				"icreep.app.IcreepMenu"));		
		Intent i = new Intent();
		i.setClassName(this, "icreep.app.IcreepMenu");
		startActivity(i);
	}
}
