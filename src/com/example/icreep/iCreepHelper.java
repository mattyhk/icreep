package com.example.icreep;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class iCreepHelper extends SQLiteOpenHelper {
	
	//set db name and version
	private final static String DATABASE_NAME = "icreepdatabase";
	private final static int DATABASE_VERSION = 1;
	
	//define tables (1..6) in db
	
	

	public iCreepHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
