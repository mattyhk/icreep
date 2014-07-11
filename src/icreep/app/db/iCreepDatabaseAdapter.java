package icreep.app.db;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import icreep.app.report.TimePlace;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class iCreepDatabaseAdapter {
	
	static iCreepHelper helper;
	Context c;
	
	String userDetails;
	
	public iCreepDatabaseAdapter(Context context){
		c = context;
		helper = new iCreepHelper(c);
	}
	
	/*
	 * Pre-Conditions: > A new user who wishes to be entered into database will supply relevant information and be entered into database
	 * Post-conditions: > Enter the user into the database and return ID to check if the insertion has been successful or not
	 */
	public long enterNewUser(String name,String surname,String position,String email,String photo){

		//define record to insert into DB
		ContentValues newUser = new ContentValues();
		newUser.put(iCreepHelper.NAME, name);
		newUser.put(iCreepHelper.SURNAME, surname);
		newUser.put(iCreepHelper.EMPLOYEE_POSITION, position);
		newUser.put(iCreepHelper.EMAIL, email);
		//for the time being let the photo be empty string
		
		newUser.put(iCreepHelper.PHOTO, photo);
		
		//now insert record into DB User table
		SQLiteDatabase db = helper.getWritableDatabase();
		long id = db.insert(iCreepHelper.TABLE_NAME5, null, newUser); //id to check if insertion was successful
		
		return id;				
	}
	
	/*
	 * Pre-Conditions: > For an already existing user to be able update/change his/her details in the database 
	 * Post-conditions: > update user details in the database and return true or false to check if the update has been successful or not
	 */
	public boolean updateUserDetails(String n, String s, String ep, String e,  String p,int UserID){
		
		ContentValues cVs = new ContentValues();
		SQLiteDatabase db = helper.getWritableDatabase();
		
		cVs.put(iCreepHelper.NAME, n);
		cVs.put(iCreepHelper.SURNAME, s);
		cVs.put(iCreepHelper.EMPLOYEE_POSITION, ep);
		cVs.put(iCreepHelper.EMAIL, e);		
		cVs.put(iCreepHelper.PHOTO, p);
		
		return db.update(iCreepHelper.TABLE_NAME5, cVs, iCreepHelper.USER_ID + " =" + UserID, null) > 0;
	}
	
	/*
	 * Pre-Conditions: > Go through database and retrieve user floor, location(description) total time spent.
	 * Post-conditions: > Return an ArrayList with floors, location(descriptions) and total time spent in location(description) or null
	 */
	public ArrayList<TimePlace> getTimePlaces(int UserID){
		
		ArrayList<TimePlace> timePlaces = new ArrayList<TimePlace>();
		
		//sql to get all of the user's locations
		//SELECT User_ID, Floor, Description, Time_Entered, Time_Left FROM User, Zone, Location WHERE User.User_ID = Location.User_ID, Location.User_ID = Zone.Location_ID
		
		SQLiteDatabase db = helper.getWritableDatabase();
		
		//get the current day's timePlaces		
		Calendar c = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String date = format.format(c.getTime());
		
		String query = "SELECT * FROM User, Zone, Location WHERE User.User_ID ="+UserID+" AND Location.Location_Date = " + date + " AND User.User_ID = Location.User_ID AND Location.Zone_ID = Zone.Zone_ID;";
		
		//String[] toReturn = {iCreepHelper.USER_ID,iCreepHelper.FLOOR, iCreepHelper.DESCRIPTION, iCreepHelper.TIME_ENTERED, iCreepHelper.TIME_LEFT};
		//String[] args = {""+UserID, "Location.User_ID"};
		Cursor cursor = db.rawQuery(query, null);//,  "User.User_ID =? AND User.User_ID =?",args,   ); //query, null);

		if(cursor != null){
			if(cursor.moveToFirst()){
				if (cursor.getInt(0) != 0) {
					do{
						//retrieve user name and position for Time Tracker "John Doe: Developer"
						userDetails = cursor.getString(cursor.getColumnIndex(iCreepHelper.NAME)) + " " + cursor.getString(cursor.getColumnIndex(iCreepHelper.SURNAME)) + ": " + cursor.getString(cursor.getColumnIndex(iCreepHelper.EMPLOYEE_POSITION));
						
						String loc = cursor.getString(cursor.getColumnIndex(iCreepHelper.DESCRIPTION));
						
						int id = cursor.getInt(cursor.getColumnIndex(iCreepHelper.ZONE_ID));
						
						//get times from DB and convert to int
						
						String timeL = cursor.getString(cursor.getColumnIndex(iCreepHelper.TIME_LEFT));	
						
						if (!timeL.equals("")){
							int hoursTimeLeft = Integer.parseInt((timeL.split(":"))[0]);
							int minsTimeLeft = Integer.parseInt((timeL.split(":"))[1]);
							int secsTimeLeft = Integer.parseInt((timeL.split(":"))[2]);
							
							String timeE = cursor.getString(cursor.getColumnIndex(iCreepHelper.TIME_ENTERED));
							int hoursTimeEntered = Integer.parseInt((timeE.split(":"))[0]);
							int minsTimeEntered = Integer.parseInt((timeE.split(":"))[1]);
							int secsTimeEntered = Integer.parseInt((timeE.split(":"))[2]);
							
							//subtract times;
							double totalHours = hoursTimeLeft - hoursTimeEntered;						
							double totalMins = minsTimeLeft - minsTimeEntered;
							double totalSecs = secsTimeLeft - secsTimeEntered;
							
							//calculate total time
							double totalTime = totalHours + totalMins/60 + totalSecs/3600;
									
							String floor = cursor.getString(cursor.getColumnIndex(iCreepHelper.FLOOR));
							TimePlace tp = new TimePlace(loc,totalTime,floor, id);
							
							timePlaces.add(tp);
						}						
					}while(cursor.moveToNext());
					
					return timePlaces;
				}
				else{
					return null;
				}
			}
			else{
				return null;
			}
		}
		else{
			return null;
		}		
	}
	
	/*
	 * Pre-Conditions: > Go through database and retrieve user name and position for Reports
	 * Post-conditions: > Return user details: "John Doe: Developer" or null
	 */
	public String getUserDetails(int userID){
		
		SQLiteDatabase db = helper.getWritableDatabase();
		//String query = "SELECT * FROM User, Reports WHERE User.User_ID ="+userID+" AND User.User_ID = Reports.User_ID;";
		String query = "SELECT * FROM User WHERE User.User_ID ="+userID+";";
		Cursor cursor = db.rawQuery(query, null);
		
		if(cursor != null){
			if(cursor.moveToFirst()){
				if(cursor.getInt(0) != 0){
					userDetails = cursor.getString(cursor.getColumnIndex(iCreepHelper.NAME)) + " " + cursor.getString(cursor.getColumnIndex(iCreepHelper.SURNAME)) + ": " + cursor.getString(cursor.getColumnIndex(iCreepHelper.EMPLOYEE_POSITION));
					return userDetails;
				}
				else{
					return null;
				}
			}
			else{
				return null;
			}
		}
		else{
			return null;
		}
	}
	
	/*
	 * Pre-Conditions: > Go through database and retrieve user name, surname, position and email
	 * Post-conditions: > Return user details in arrayList format
	 */
	public ArrayList<String> userDetails(int UserID){
		ArrayList<String> ud = new ArrayList<String>();
		
		String query = "SELECT * FROM User WHERE User.User_ID = "+UserID+";";
		
		SQLiteDatabase db = helper.getWritableDatabase();
		Cursor cursor = db.rawQuery(query, null);
		
		if(cursor != null){
			if(cursor.moveToFirst()){
				if(cursor.getInt(0) != 0){
					
					ud.add((cursor.getString(cursor.getColumnIndex(iCreepHelper.NAME))));
					ud.add((cursor.getString(cursor.getColumnIndex(iCreepHelper.SURNAME))));
					ud.add((cursor.getString(cursor.getColumnIndex(iCreepHelper.EMPLOYEE_POSITION))));
					ud.add((cursor.getString(cursor.getColumnIndex(iCreepHelper.EMAIL))));					
									
					return ud;
				}
				else{
					return null;
				}
			}
			else{
				return null;
			}
		}
		else{
			return null;
		}
	}
	
	/*
	 * Pre-Conditions: > Go through database and retrieve user's report auto-delivery time
	 * Post-conditions: > Return time in string format: "13:25" or null
	 */
	public String getReportTime(int userID){
		// SELECT Delivery_Time FROM Reports, User WHERE User.User_ID =
		// Reports.User_ID AND Auto_Delivery = true;

		//SQLiteDatabase db = helper.getWritableDatabase();

		String query = "SELECT Reports.Delivery_Time,Reports.Auto_Delivery FROM Reports, User WHERE User.User_ID ="+userID+" AND User.User_ID = Reports.User_ID;";
		SQLiteDatabase db = helper.getWritableDatabase();
		Cursor cursor = db.rawQuery(query, null);

		if (cursor != null) {
			if (cursor.moveToFirst()) {

				int index = cursor.getColumnIndex("Auto_Delivery");
				int val = cursor.getInt(index);
				String time = cursor.getString(cursor
						.getColumnIndex(iCreepHelper.DELIVERY_TIME));
				time = time + "+" + val;
				return time;
			} else {
				return null;
			}
		} else {
			return null;
		}

	}

	/*
	 * Pre-Conditions: > Go through database and change/ set report auto-delivery time
	 * Post-conditions: > update report delivery time in database
	 */
	public boolean updateDeliveryTime(String newTime,int userID,boolean in){
		
		int forDelivery = 0 ;
		if (in == true)
		{
			forDelivery = 1;
		}
		
		//String query = "UPDATE Reports SET Auto_Delivery = "+forDelivery+", Delivery_Time = '" + newTime + "' WHERE User.User_ID ="+userID+" AND User.User_ID = Reports.User_ID;";
		ContentValues cVs = new ContentValues();
		cVs.put(iCreepHelper.AUTO_DELIVERY, forDelivery);
		cVs.put(iCreepHelper.DELIVERY_TIME, newTime);
		cVs.put(iCreepHelper.USER_ID, userID);
		
		SQLiteDatabase db = helper.getWritableDatabase();
		try {
			//String[] args = {""+userID};
			db.update(iCreepHelper.TABLE_NAME6,cVs,iCreepHelper.USER_ID + "=" + userID,null);
			return true ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return false ;
		}	
	}
	
	public boolean addDeliveryTime(String newTime, int userID)
	{
		ContentValues cVs = new ContentValues();
		cVs.put(iCreepHelper.AUTO_DELIVERY, 1);
		cVs.put(iCreepHelper.DELIVERY_TIME, newTime);
		cVs.put(iCreepHelper.USER_ID, userID);
		
		SQLiteDatabase db = helper.getWritableDatabase();
		
		return db.insert(iCreepHelper.TABLE_NAME6,null, cVs) > 0;

	}
	
	
	public long addNewLocation(int userID, int zoneID, String time, String date) {
		ContentValues cV = new ContentValues();
		
		cV.put(iCreepHelper.TIME_ENTERED, time);
		cV.put(iCreepHelper.TIME_LEFT, "");
		cV.put(iCreepHelper.LOCATION_DATE, date);
		cV.put(iCreepHelper.ZONE_ID, zoneID);
		cV.put(iCreepHelper.USER_ID, userID);
		
		SQLiteDatabase db = helper.getWritableDatabase();
		
		long success = db.insert(iCreepHelper.TABLE_NAME4, null, cV);
		
		if (success > 0) {
			return success;
		}
		
		else {
			// Failed to enter
			return -1;
		}
	}
	
	public boolean updateExitTime(String time, long lastEntryID) {
		ContentValues cV = new ContentValues();
		
		cV.put(iCreepHelper.TIME_LEFT, time);
		
		SQLiteDatabase db = helper.getWritableDatabase();
		
		String query = "SELECT Location.Time_Left FROM Location WHERE Location.Location_ID ="+lastEntryID+";";
		Cursor cursor = db.rawQuery(query, null);
		
		if (cursor != null) {
			if (cursor.moveToFirst()) {
				String timeLeft = cursor.getString(cursor.getColumnIndex(iCreepHelper.TIME_LEFT));
				if (timeLeft.equals("")) {
					//String[] args = {""+lastEntryID};
					db.update(iCreepHelper.TABLE_NAME4, cV, iCreepHelper.LOCATION_ID + "=" + lastEntryID, null);
					return true;
				}
				else {
					Log.d("TEST", "Time Left was not empty");
				}
			}
			else {
				Log.d("TEST", "Cursor is empty");
			}
		}
		else {
			Log.d("TEST", "Cursor is null");
		}
		return false;
	}
	
	
	//this function clears the tables in the database
	public void clearDatabase(){
		SQLiteDatabase db = helper.getWritableDatabase();
		try {
			String[] tables = {iCreepHelper.TABLE_NAME1,iCreepHelper.TABLE_NAME3,iCreepHelper.TABLE_NAME4,iCreepHelper.TABLE_NAME5,iCreepHelper.TABLE_NAME6}; 
			for(int i=0; i< helper.tableCount;i++){
				db.delete(tables[i], null,null);
				String[] args = {tables[i]};
				db.delete("SQLITE_SEQUENCE", "name=?", args);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
		}
	}
	
	//function to create Beacons
	public static void createBeacons(){
		
		SQLiteDatabase db = helper.getWritableDatabase();
		
		ContentValues cV = new ContentValues();
		cV.put(iCreepHelper.BEACON_ID, -1);
		cV.put(iCreepHelper.MAJOR, -1);
		cV.put(iCreepHelper.MINOR, -1);
		
		db.insert(iCreepHelper.TABLE_NAME1, null, cV);
				
		int beacons = 11;
		int major = 3;
		
		for(int i=1; i<= beacons; i++){
			db = helper.getWritableDatabase();
			
			ContentValues cVs = new ContentValues();
			cVs.put(iCreepHelper.MAJOR, major);
			cVs.put(iCreepHelper.MINOR, i);
			
			db.insert(iCreepHelper.TABLE_NAME1, null, cVs);
		}		
	}
	
	//create zones and match with the relevant Beacon
	public static void createZone(){
		createBeacons();
		
		SQLiteDatabase db = helper.getWritableDatabase();
		
		ContentValues cVs = new ContentValues();
		
		cVs.put(iCreepHelper.ZONE_ID, -1);
		cVs.put(iCreepHelper.DESCRIPTION, "Outside");
		cVs.put(iCreepHelper.FLOOR,"Outside");
		cVs.put(iCreepHelper.BEACON_ID, -1);
		
		db.insert(iCreepHelper.TABLE_NAME3, null, cVs);
		
		String[] description = {"S3","Mens' Bathroom","Intern Zone","Denzil Zone","Focus Room","Kabir Zone","S2","S1","Second Floor Kitchen","Water Zone","Second Floor Corner"};
		
		for(int i=0; i<description.length; i++){
			
			ContentValues cV = new ContentValues();
			cV.put(iCreepHelper.DESCRIPTION, description[i]);
			cV.put(iCreepHelper.FLOOR,"Second Floor");
			int b_ID = i+1;
			cV.put(iCreepHelper.BEACON_ID, b_ID);
			
			db.insert(iCreepHelper.TABLE_NAME3, null, cV);
		}	
	}
	
	//database schema definition and creation 
	static class iCreepHelper extends SQLiteOpenHelper{
		//set db name and version
		private static final String DATABASE_NAME = "icreepdatabase";
		
		//version changes every time the structure of the db changes
		private static final int DATABASE_VERSION = 14;
		
		//define tables (1..6) in db
		
		// for easy access later define table column NAMES for each table here
		
		//create_Beacon_query = "CREATE TABLE " + TABLE_NAME1 + "( Beacon_ID INTEGER PRIMARY KEY AUTOMINCREMENT, Major INTEGER, Minor INTEGER);";
		private static final String TABLE_NAME1 = "Beacon";
		
		private static final String BEACON_ID = "Beacon_ID";
		private static final String MAJOR = "Major";
		private static final String MINOR = "Minor";
		
		/*
		//create_ZoneBeacon_query = "CREATE TABLE " + TABLE_NAME2 + "( ZoneBeacon_ID INTEGER PRIMARY KEY AUTOMINCREMENT, Beacon_ID INTEGER, Zone_ID INTEGER, ThreshholdValue FLOAT, FOREIGN KEY (Beacon_ID) REFERENCES Beacon(Beacon_ID), FOREIGN KEY (Zone_ID) REFERENCES Zone(Zone_ID));";
		private static final String TABLE_NAME2 = "ZoneBeacon";
		
		private static final String ZONEBEACON_ID = "ZoneBeacon_ID";
		private static final String THRESHHOLD_VALUE = "Threshhold_Value";
		*/
		
		//create_Zone_query = "CREATE TABLE " + TABLE_NAME3 + "( Zone_ID INTEGER PRIMARY KEY AUTOMINCREMENT, Description VARCHAR(255), Floor INTEGER);";
		private static final String TABLE_NAME3 = "Zone";
		
		private static final String ZONE_ID = "Zone_ID";
		private static final String DESCRIPTION = "Description";
		private static final String FLOOR = "Floor";
		
		//String create_Location_query = "CREATE TABLE " + TABLE_NAME4 + "( Location_ID INTEGER PRIMARY KEY AUTOINCREMENT, Zone_ID INTEGER, User_ID INTEGER, Time_Entered DATETIME, Time_Left DATETIME, Location_Date DATE, FOREIGN KEY (Zone_ID) REFERENCES Zone(Zone_ID), FOREIGN KEY (User_ID) REFERENCES User(User_ID));"; 
		private static final String TABLE_NAME4 = "Location";
		
		private static final String LOCATION_ID = "Location_ID";
		private static final String TIME_ENTERED = "Time_Entered";
		private static final String TIME_LEFT = "Time_Left";
		private static final String LOCATION_DATE = "Location_Date";
		
		
		//create_User_query = "CREATE TABLE " + TABLE_NAME5 + "( User_ID INTEGER PRIMARY KEY AUTOMINCREMENT, Name VARCHAR(75), Surname VARCHAR(75), Email VARCHAR(100), Employee_Position VARCHAR(50), Photo VARHCAR(255));";
		private static final String TABLE_NAME5 = "User";
		
		private static final String USER_ID = "User_ID";
		private static final String NAME = "Name";
		private static final String SURNAME = "Surname";
		private static final String EMAIL = "Email";
		private static final String EMPLOYEE_POSITION = "Employee_Position";
		private static final String PHOTO = "Photo";
		
		//create_Reprts_query = "CREATE TABLE " + TABLE_NAME6 + "( Report_ID INTEGER PRIMARY KEY AUTOMINCREMENT, User_ID INTEGER, Auto_Delivery BOOLEAN, Delivery_Time DATETIME, FOREIGN KEY (User_ID) REFERENCES User(User_ID));";
		private static final String TABLE_NAME6 = "Reports";
		
		private static final String REPORT_ID = "Report_ID";
		private static final String AUTO_DELIVERY = "Auto_Delivery";
		private static final String DELIVERY_TIME = "Delivery_Time";
			
		//CREATE_TABLE QUERIES for db		
		private static final String create_Beacon_query = "CREATE TABLE " + TABLE_NAME1 + "(" + BEACON_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + MAJOR + " INTEGER NOT NULL,"+ MINOR +" INTEGER NOT NULL);";
		//private static final String create_ZoneBeacon_query = "CREATE TABLE " + TABLE_NAME2 + "("+ ZONEBEACON_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+ BEACON_ID +" INTEGER,"+ ZONE_ID + " INTEGER," + THRESHHOLD_VALUE +" FLOAT NOT NULL, FOREIGN KEY (Beacon_ID) REFERENCES Beacon(Beacon_ID) ON DELETE CASCADE ON UPDATE CASCADE, FOREIGN KEY (Zone_ID) REFERENCES Zone(Zone_ID) ON DELETE CASCADE ON UPDATE CASCADE);";
		private static final String create_Zone_query = "CREATE TABLE " + TABLE_NAME3 + "(" + ZONE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + BEACON_ID +" INTEGER UNIQUE, " + DESCRIPTION + " VARCHAR(255) NOT NULL,"+ FLOOR + " VARCHAR(20) NOT NULL, FOREIGN KEY (Beacon_ID) REFERENCES Beacon(Beacon_ID) ON DELETE CASCADE ON UPDATE CASCADE);";
		private static final String create_Location_query = "CREATE TABLE " + TABLE_NAME4 + "(" + LOCATION_ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"+ ZONE_ID +" INTEGER, "+ USER_ID +" INTEGER,"+ TIME_ENTERED + " VARCHAR(10) NOT NULL, " + TIME_LEFT +" VARCHAR(10) NOT NULL, " + LOCATION_DATE+ " DATE NOT NULL, FOREIGN KEY (Zone_ID) REFERENCES Zone(Zone_ID) ON DELETE CASCADE ON UPDATE CASCADE, FOREIGN KEY (User_ID) REFERENCES User(User_ID) ON DELETE CASCADE ON UPDATE CASCADE);"; 
		private static final String create_User_query = "CREATE TABLE " + TABLE_NAME5 + "("+ USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + NAME + " VARCHAR(30) NOT NULL," + SURNAME +" VARCHAR(30) NOT NULL, "+ EMAIL +" VARCHAR(255) NOT NULL," + EMPLOYEE_POSITION +" VARCHAR(30) NOT NULL, "+ PHOTO +" VARHCAR(255));";
		private static final String create_Reprts_query = "CREATE TABLE " + TABLE_NAME6 + "(" + REPORT_ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"+ USER_ID + " INTEGER UNIQUE, " + AUTO_DELIVERY +" BOOLEAN NOT NULL, " + DELIVERY_TIME +" VARCHAR(10), FOREIGN KEY (User_ID) REFERENCES User(User_ID));";

		public int tableCount = 5;
		private int createTableQueryCount = 5;
		
		public iCreepHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// when creating the db for the first time (executing the CREATE TABLE SQL) - execute CREATE_TABLE queries here
			
			String[] createTableQueries = {create_Beacon_query,create_Zone_query,create_User_query,create_Location_query,create_Reprts_query};	
			
			for(int i=0; i<createTableQueryCount; i++){
				try {
					//create tables
					db.execSQL(createTableQueries[i]);
				} catch (SQLException e) {
					//display error on toast if appeared
				}
			}			
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// u can backup db online (after changes), alter table, drop table and such here...
			// on upgrade, drop older tables
			
			
			String[] tables = {TABLE_NAME1,TABLE_NAME3,TABLE_NAME4,TABLE_NAME5,TABLE_NAME6};
			
			//dropping existing tables upon db structure changes (if oldVersion != newVersion)
			for(int i=0;i<tableCount;i++){
				try {
					//carry out wanted query: Alter, DROP TABLEs, etc...
					//need to drop tables if DB structure changes
					db.execSQL("DROP TABLE IF EXISTS " + tables[i]);
				} catch (SQLException e) {
				}
			}
			
			//recreate tables
			onCreate(db);	
		}
	}
}

