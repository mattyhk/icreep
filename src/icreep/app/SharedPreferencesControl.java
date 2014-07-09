package icreep.app;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesControl
{
	
	// might adapt this class to accept many different preference filenames
	String defaultFileName = "iCreepData"; 
	Context c = null ;
	public SharedPreferencesControl(Context c)
	{
		this.c = c ;
	}
	public boolean sharedPrefTest()
	{
		SharedPreferences sp = c.getSharedPreferences(defaultFileName, Context.MODE_PRIVATE);
		int user = sp.getInt("userID", -1);
		boolean tc = (user == -1);
		return tc ;
	}
	
	public int getUserID()
	{
		SharedPreferences sp = c.getSharedPreferences(defaultFileName, Context.MODE_PRIVATE);
		int user = sp.getInt("userID", -1);
		return user ;
	}
	
	public boolean sharedProfilePicTest()
	{
		SharedPreferences sp = c.getSharedPreferences(defaultFileName, Context.MODE_PRIVATE);
		String user = sp.getString("profilePic", "");
		if (user.equals(""))
		{
			return false ;
		}else return true ;
	}
	
	public String getProfilePicFilename()
	{
		SharedPreferences sp = c.getSharedPreferences(defaultFileName, Context.MODE_PRIVATE);
		String user = sp.getString("profilePic", "");
		return user ;
	}
	
	public void writeNewUserID(int userID)
	{
		SharedPreferences sp = c.getSharedPreferences(defaultFileName, Context.MODE_PRIVATE);
		SharedPreferences.Editor edit = sp.edit();
		edit.putInt("userID", userID);
		edit.commit();
	}
	
	public void writeProfilePicName(String profilePicName)
	{
		SharedPreferences sp = c.getSharedPreferences(defaultFileName, Context.MODE_PRIVATE);
		SharedPreferences.Editor edit = sp.edit();
		edit.putString("profilePic", profilePicName);
		edit.commit();
	}
	
	public void clearSP()
	{
		SharedPreferences sp = c.getSharedPreferences(defaultFileName, Context.MODE_PRIVATE);
		SharedPreferences.Editor edit = sp.edit();
		edit.clear();
		edit.commit();
	}
	
	
	
	
}