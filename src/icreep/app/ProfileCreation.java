package icreep.app;

import icreep.app.db.iCreepDatabaseAdapter;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;


public class ProfileCreation extends Activity {
	
	Button save_button;
	
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
		setContentView(R.layout.activity_profile_creation);		
		

		userName = (EditText) findViewById(R.id.editText1_user_name);
		userSurname = (EditText) findViewById(R.id.editText2_user_surname);
		userPosition = (EditText) findViewById(R.id.editText4_user_position);
		userEmail = (EditText) findViewById(R.id.editText3_user_email);
					
		//rename helper for db management
		icreepHelper = new iCreepDatabaseAdapter(this);	
		
	}//onCreate method
	
	
	public void uploadImage(View view){
    	
    	//Intent intent = new Intent(this, ProfilePicture.class);
		
    	photo = "";
    	
	}//uploadImage
	
	//listener to adddUser event - let's add new user to db	
	public void saveDetails(View view){
    	
    	//Intent intent = new Intent(this, SaveUserDetails.class);
    	
    	//EditText editText = (EditText) findViewById(R.string.);
		
		//get user details
		String name = userName.getText().toString();
		String surname = userSurname.getText().toString();
		String position = userPosition.getText().toString();
		String email = userEmail.getText().toString();
		
		//before entering user into DB - can send validation email first
		//if validation email bounces than user not entered in DB else add to DB		
		if(isValidEmail(email)){				
			long id = icreepHelper.enterNewUser(name, surname, position, email, photo);	
			
			//check if insertion was successful
			if(id<0){
				Message.message(this, "User details saved");
				
				//Go to main menu if insertion is successful
				save_button = (Button) findViewById(R.id.button2_save_user_details);
				save_button.setOnClickListener(new SwitchButtonListener(this, "icreep.app.IcreepMenu"));
				
			}else{
				Message.message(this, "User details not  saved");
			}		
	    }
		else{
			Message.message(this,"invalid email address");
		}			
		
	}//saveDetails method
	
	//function to validate email addresses against an email Regular Expression
    public boolean isValidEmail(String email){
	    
    	String emailRegex ="^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	    
    	if(email.matches(emailRegex))
	    {
	        return true;
	    }
	    return false;
    }
}
