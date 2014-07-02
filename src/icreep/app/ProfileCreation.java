package icreep.app;

import icreep.app.db.iCreepDatabaseAdapter;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;


public class ProfileCreation extends Activity {
	
	//views to extract user details from
	EditText userName, userSurname, userPosition, userEmail;
	//find way to get photo
	ImageView userPhoto;
		EditText name = (EditText) findViewById(R.id.editText1_user_name);
		EditText surname = (EditText) findViewById(R.id.editText2_user_surname);
		EditText email = (EditText) findViewById(R.id.editText3_user_email);
		EditText position = (EditText) findViewById(R.id.editText4_user_position);
		
		String sname = name.getText().toString(); 
		String ssurname = surname.getText().toString(); 
		String semail  = email.getText().toString();
		String sposition  = position.getText().toString();
		
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
	
	//upload image
	public void uploadImage(View view){
    	
    	//Intent intent = new Intent(this, ProfilePicture.class);
		
    	photo = "";
	}
    	
	
	
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
			
		long id = icreepHelper.enterNewUser(name, surname, position, email, photo);	
		
		
		//check if insertion was successful
		if(id<0){
			Message.message(this, "User details saved");
		}else{
			Message.message(this, "User not details saved");
		}		   	
    }//saveDetails method	
}
