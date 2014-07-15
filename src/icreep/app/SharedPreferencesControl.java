package icreep.app;

import java.util.ArrayList;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesControl
{
	
	// might adapt this class to accept many different preference filenames
	String defaultFileName = "iCreepData"; 
	Context c = null ;
	private SharedPreferences sp;
	private SharedPreferences.Editor edit;
	
	public SharedPreferencesControl(Context c)
	{
		this.c = c;
		this.sp = c.getSharedPreferences(defaultFileName, Context.MODE_PRIVATE);
		this.edit = sp.edit();
	}
	
	public boolean sharedPrefTest()
	{
		int user = sp.getInt("userID", -1);
		boolean tc = (user == -1);
		return tc ;
	}
	
	public int getUserID()
	{
		int user = sp.getInt("userID", -1);
		return user ;
	}
	
	public boolean sharedProfilePicTest()
	{
		String user = sp.getString("profilePic", "");
		if (user.equals(""))
		{
			return false ;
		}else return true ;
	}
	
	public String getProfilePicFilename()
	{
		String user = sp.getString("profilePic", "");
		return user ;
	}
	
	public void writeNewUserID(int userID)
	{
		edit.putInt("userID", userID);
		edit.commit();
	}
	
	public void writeProfilePicName(String profilePicName)
	{
		edit.putString("profilePic", profilePicName);
		edit.commit();
	}
	
	public void clearSP()
	{
		edit.clear();
		edit.commit();
	}
	
	public ArrayList<String> getPingUserDetails() 
	{
		SharedPreferences sp = c.getSharedPreferences(defaultFileName, Context.MODE_PRIVATE);
		String name = sp.getString("bossName", "");
		ArrayList<String> list = new ArrayList<String>() ;
		String mac = sp.getString("bossMac", "");
		if (name.equals(""))
		{
			return list ;
		}
		if (mac.equals(""))
		{
			return list ;
		}
				
		list.add(name);
		list.add(mac);
		
		return list ;		
	}
	
	public void writeBossDetails(String name, String mac)
	{
		edit.putString("bossName", name);
		edit.putString("bossMac", mac);
		edit.commit();
	}
	
	public void removeBossDetails()
	{
		edit.remove("bossName");
		edit.remove("bossMac");
		edit.commit();
	}
	
	public String getBossBeaconDetails() {
		String uuid = sp.getString("bossUUID", "");
		return uuid;
	}
	
	public void writeBossBeaconDetails(String uuid) {
		edit.putString("bossUUID", uuid);
		edit.commit();
	}
	
	
	
	
}
