package com.example.icreep;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class iCreepHelper extends SQLiteOpenHelper {
	
	//set db name and version
	private static final String DATABASE_NAME = "icreepdatabase";
	//version changes every time the structure of the db changes
	private static final int DATABASE_VERSION = 1;
	
	//define tables (1..6) in db
	private final String DATABASE_NAME1 = "Beacon";
	
	
	private final String DATABASE_NAME2 = "ZoneBeacon";
	
	
	private final String DATABASE_NAME3 = "Zone";
	
	
	private final String DATABASE_NAME4 = "Location";
	
	
	private final String DATABASE_NAME5 = "User";
	
	
	private final String DATABASE_NAME6 = "Reports";
	

	public iCreepHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// when creating the db for the first time (execuring the CREATE TABLE SQL) - this will be called
		
		//Beacon table
		String create_query = "CREATE TABLE " + DATABASE_NAME1 + "( BEACON_ID INTEGER PRIMARY KEY AUTOMINCREMENT, Major LONG, Minor LONG);";
		
		String
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// u can backup db online, alter table, drop table and such here...
		
	}

}
