package com.example.icreep;

import java.util.HashMap;
import java.util.Map;

/**
 * A class that handles all of the location tracking functionality required by the application
 * The object should be instantiated each time an activity calls the service that processes the tracking functionality
 * The object checks the greater application object upon creation to determine what the last known zone is
 * @author mkerr
 *
 */
public class UserLocation {
	
	private int currentZone;
	private static final HashMap<Integer, String> zones = new HashMap<Integer, String>(){
		{
			put(0, "Outside");
		}
	};
	
	public UserLocation(int zone) {
		currentZone = zone;
	}
	
	/**
	 * Starts tracking the user's location
	 * Starts a Handler to schedule the repeating task 
	 * Checks if a user's zone has not changed for at least some minimum number of cycles
	 * If the zone has not changed, the user is determined to be in that zone
	 * Checks if that zone is different from currentZone
	 * If it is different, the location database is updated to reflect the change by editing the exit time of the last 
	 * entry, and inserting a new entry with the entry time
	 */
	public void startTracking(){
		
	}
	
	/**
	 * Stops tracking the user's location
	 * The last entry in the location database is updated with the exit time
	 */
	public void stopTracking(){
		
	}
	
	/**
	 * Determines the current zone of the user as determined by the signals given by the iBeacon service
	 * @return zone - returns the calculated zone
	 */
	private int findCurrentZone(){
		
	}
	
}
