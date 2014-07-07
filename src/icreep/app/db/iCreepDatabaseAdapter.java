package icreep.app.db;

import java.util.ArrayList;

import icreep.app.Message;
import icreep.app.report.TimePlace;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class iCreepDatabaseAdapter {
	
	iCreepHelper helper;
	
	String userDetails;
	
	public iCreepDatabaseAdapter(Context context){
		helper = new iCreepHelper(context);
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
		photo = "";
		newUser.put(iCreepHelper.PHOTO, photo);
		
		//now insert record into DB User table
		SQLiteDatabase db = helper.getWritableDatabase();
		long id = db.insert(iCreepHelper.TABLE_NAME5, null, newUser); //id to check if insertion was successful
		
		return id;				
	}
	
	/*
	 * Pre-Conditions: > Go through database and retrieve user floor, location(description) total time spent.
	 * Post-conditions: > Return an ArrayList with floors, location(descriptions) and total time spent in location(description)
	 */
	public ArrayList<TimePlace> getTimePlaces(){
		
		ArrayList<TimePlace> timePlaces = new ArrayList<TimePlace>();
		
		//sql to get all of the user's locations
		//SELECT User_ID, Floor, Description, Time_Entered, Time_Left FROM User, Zone, Location WHERE User.User_ID = Location.User_ID, Location.User_ID = Zone.Location_ID
		
		SQLiteDatabase db = helper.getWritableDatabase();
		
		String query = "SELECT * FROM User, Zone, Location WHERE User.User_ID = 1 AND User.User_ID = Location.User_ID AND Location.Location_ID = Zone.Location_ID;";
		
		//String[] toReturn = {iCreepHelper.USER_ID,iCreepHelper.FLOOR, iCreepHelper.DESCRIPTION, iCreepHelper.TIME_ENTERED, iCreepHelper.TIME_LEFT};
		Cursor cursor = db.rawQuery(query, null);

		if(cursor != null){
			if(cursor.moveToFirst()){
				if (cursor.getInt(0) != 0) {
					do{
						//retrieve user name and position for Time Tracker "John Doe: Developer"
						userDetails = cursor.getString(cursor.getColumnIndex(iCreepHelper.NAME)) + " " + cursor.getString(cursor.getColumnIndex(iCreepHelper.SURNAME)) + ": " + cursor.getString(cursor.getColumnIndex(iCreepHelper.EMPLOYEE_POSITION));
						
						String loc = cursor.getString(cursor.getColumnIndex(iCreepHelper.DESCRIPTION));
						double totalTime =  cursor.getDouble(cursor.getColumnIndex(iCreepHelper.TIME_LEFT)) - cursor.getDouble(cursor.getColumnIndex(iCreepHelper.TIME_ENTERED));
						String floor = cursor.getString(cursor.getColumnIndex(iCreepHelper.FLOOR));
												
						TimePlace tp = new TimePlace(loc,totalTime,floor);
						
						timePlaces.add(tp);		
					}while(cursor.moveToNext());
				}
			}
			return timePlaces;
		}
		else{
			return null;
		}
		
	}
	
	/*
	 * Pre-Conditions: > Go through database and retrieve user name and position for Reports
	 * Post-conditions: > Return user details: "John Doe: Developer"
	 */
	public String getUserDetails(){
		
		SQLiteDatabase db = helper.getWritableDatabase();
		String query = "SELECT * FROM User, Reports WHERE User.User_ID = 1 AND User.User_ID = Reports.User_ID;";
		Cursor cursor = db.rawQuery(query, null);
		
		userDetails = cursor.getString(cursor.getColumnIndex(iCreepHelper.NAME)) + " " + cursor.getString(cursor.getColumnIndex(iCreepHelper.SURNAME)) + ": " + cursor.getString(cursor.getColumnIndex(iCreepHelper.EMPLOYEE_POSITION));

		return userDetails;
	}
	
	/*
	 * Pre-Conditions: > Go through database and retrieve user's report auto-delivery time
	 * Post-conditions: > Return time in string format: "13:25"
	 */
	public String getReportTime(){
		//SELECT Delivery_Time FROM Reports, User WHERE User.User_ID = Reports.User_ID AND Auto_Delivery = true;
		
		SQLiteDatabase db = helper.getWritableDatabase();

		String query = "SELECT Delivery_Time FROM Reports, User WHERE User.User_ID = 1 AND User.User_ID = Reports.User_ID;";
		Cursor cursor = db.rawQuery(query, null);
		
		if(cursor.getColumnIndex(iCreepHelper.AUTO_DELIVERY) == 1){
			String time = cursor.getString(cursor.getColumnIndex(iCreepHelper.DELIVERY_TIME));	
			return time;
		}else{
			return null;
		}		
	}
	
	/*
	 * Pre-Conditions: > Go through database and change/ set report auto-delivery time
	 * Post-conditions: > update report delivery time in database
	 */
	public void setDeliveryTime(String newTime){
		
		String query = "UPDATE Reports SET Auto_Delivery = 1, Delivery_Time = '" + newTime + "' WHERE User.User_ID = 1 AND User.User.ID = Reports.User_ID;";
		
		SQLiteDatabase db = helper.getWritableDatabase();
		db.rawQuery(query, null);
	
	}
	
	//this function clears the tables in the database
	public void clearDatabase(){
		SQLiteDatabase db = helper.getWritableDatabase();
		String[] tables = {iCreepHelper.TABLE_NAME1,iCreepHelper.TABLE_NAME2,iCreepHelper.TABLE_NAME3,iCreepHelper.TABLE_NAME4,iCreepHelper.TABLE_NAME5,iCreepHelper.TABLE_NAME6}; 
		for(int i=0; i< helper.tableCount;i++){
			db.delete(tables[i], null,null); 
		}
	}
	
	//database schema definition and creation 
	static class iCreepHelper extends SQLiteOpenHelper{
		//set db name and version
		private static final String DATABASE_NAME = "icreepdatabase";
		
		//version changes every time the structure of the db changes
		private static final int DATABASE_VERSION = 6;
		
		//define tables (1..6) in db
		
		// for easy access later define table column NAMES for each table here
		
		//create_Beacon_query = "CREATE TABLE " + TABLE_NAME1 + "( Beacon_ID INTEGER PRIMARY KEY AUTOMINCREMENT, Major INTEGER, Minor INTEGER);";
		private static final String TABLE_NAME1 = "Beacon";
		
		private static final String BEACON_ID = "Beacon_ID";
		private static final String MAJOR = "Major";
		private static final String MINOR = "Minor";
		
		//create_ZoneBeacon_query = "CREATE TABLE " + TABLE_NAME2 + "( ZoneBeacon_ID INTEGER PRIMARY KEY AUTOMINCREMENT, Beacon_ID INTEGER, Zone_ID INTEGER, ThreshholdValue FLOAT, FOREIGN KEY (Beacon_ID) REFERENCES Beacon(Beacon_ID), FOREIGN KEY (Zone_ID) REFERENCES Zone(Zone_ID));";
		private static final String TABLE_NAME2 = "ZoneBeacon";
		
		private static final String ZONEBEACON_ID = "ZoneBeacon_ID";
		private static final String THRESHHOLD_VALUE = "Threshhold_Value";
		
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
		private static final String create_ZoneBeacon_query = "CREATE TABLE " + TABLE_NAME2 + "("+ ZONEBEACON_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+ BEACON_ID +" INTEGER,"+ ZONE_ID + " INTEGER," + THRESHHOLD_VALUE +" FLOAT NOT NULL, FOREIGN KEY (Beacon_ID) REFERENCES Beacon(Beacon_ID), FOREIGN KEY (Zone_ID) REFERENCES Zone(Zone_ID));";
		private static final String create_Zone_query = "CREATE TABLE " + TABLE_NAME3 + "(" + ZONE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + LOCATION_ID +" INTEGER, " + DESCRIPTION + " VARCHAR(255) NOT NULL,"+ FLOOR + " INTEGER NOT NULL, FOREIGN KEY (Location_ID) REFERENCES Location(Location_ID));";
		private static final String create_Location_query = "CREATE TABLE " + TABLE_NAME4 + "(" + LOCATION_ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"+ ZONE_ID +" INTEGER, "+ USER_ID +" INTEGER,"+ TIME_ENTERED + " DATETIME NOT NULL, " + TIME_LEFT +" DATETIME NOT NULL, " + LOCATION_DATE+ " DATE NOT NULL, FOREIGN KEY (Zone_ID) REFERENCES Zone(Zone_ID), FOREIGN KEY (User_ID) REFERENCES User(User_ID));"; 
		private static final String create_User_query = "CREATE TABLE " + TABLE_NAME5 + "("+ USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + NAME + " VARCHAR(75) NOT NULL," + SURNAME +" VARCHAR(75) NOT NULL, "+ EMAIL +" VARCHAR(100) NOT NULL," + EMPLOYEE_POSITION +" VARCHAR(50) NOT NULL, "+ PHOTO +" VARHCAR(255));";
		private static final String create_Reprts_query = "CREATE TABLE " + TABLE_NAME6 + "(" + REPORT_ID +" INTEGER PRIMARY KEY AUTOINCREMENT,"+ USER_ID + " INTEGER, " + AUTO_DELIVERY +" BOOLEAN NOT NULL, " + DELIVERY_TIME +" VARCHAR(10), FOREIGN KEY (User_ID) REFERENCES User(User_ID));";

		public int tableCount = 6;
		private int createTableQueryCount = 6;
		
		private Context context;
		
		public iCreepHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			this.context = context;
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// when creating the db for the first time (executing the CREATE TABLE SQL) - execute CREATE_TABLE queries here
			
			String[] createTableQueries = {create_Beacon_query,create_Zone_query,create_ZoneBeacon_query,create_User_query,create_Location_query,create_Reprts_query};	
			
			for(int i=0; i<createTableQueryCount; i++){
				try {
					db.execSQL(createTableQueries[i]);
				} catch (SQLException e) {
					//display error on toast if appeared
					Message.message(context, ""+e);
				}
			}			
			//to see if onCreate was called
			Message.message(context, " onCreate called");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// u can backup db online (after changes), alter table, drop table and such here...
			// on upgrade, drop older tables
			
			//to see onUpgrade was called
			Message.message(context, " onUpgrade called");
			
			String[] tables = {TABLE_NAME1,TABLE_NAME2,TABLE_NAME3,TABLE_NAME4,TABLE_NAME5,TABLE_NAME6};
			
			//dropping existing tables upon db structure changes (if oldVersion != newVersion)
			for(int i=0;i<tableCount;i++){
				try {
					//carry out wanted query: Alter, DROP TABLEs, etc...
					//need to drop tables if DB structure changes
					db.execSQL("DROP TABLE IF EXISTS " + tables[i]);
				} catch (SQLException e) {
					//display error on toast if appeared
					Message.message(context, ""+e);
				}
			}
			
			//recreate tables
			onCreate(db);	
		}
	}
}

