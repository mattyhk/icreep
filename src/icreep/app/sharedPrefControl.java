package icreep.app;

import android.content.Context;
import android.content.SharedPreferences;

public class sharedPrefControl
{
	
	// might adapt this class to accept many different preference filenames
	String defaultFileName = "iCreepData"; 
	Context c = null ;
	public sharedPrefControl(Context c)
	{
		this.c = c ;
	}
	public boolean sharedPrefTest()
	{
		SharedPreferences sp = c.getSharedPreferences(defaultFileName, Context.MODE_PRIVATE);
		int user = sp.getInt("userID", -1);
		if (user == -1)
		{
			return false ;
		}else return true ;
	}
	
	public int getUserID()
	{
		SharedPreferences sp = c.getSharedPreferences(defaultFileName, Context.MODE_PRIVATE);
		int user = sp.getInt("userID", -1);
		return user ;
	}
	
	public void writeNewUserID(int userID)
	{
		SharedPreferences sp = c.getSharedPreferences(defaultFileName, Context.MODE_PRIVATE);
		SharedPreferences.Editor edit = sp.edit();
		edit.putInt("userID", userID);
		edit.commit();
	}
	
}
