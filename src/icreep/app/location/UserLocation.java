package icreep.app.location;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A class that handles the current location of the user. The object receives 
 * the results of processing the beacon signals, and updates the current location of the
 * user. The class also handles updating the DB with the required information.
 * 
 * @author mkerr
 * 
 */
public class UserLocation {
	
	private final static int MAX_COUNT = 5;
	
	private int currentLocation;
	private int currentLocationCount;
	private int currentTempLocation;

	public UserLocation() {
		this.currentLocation = -1;
		this.currentLocationCount = 0;
	}
	
	
	/**
	 * Updates the current (temporary) location of the user. Depending on the number of times
	 * the user has been in the same current location, different updates will take place.
	 * If the user has been in the current temporary location for less than the MAX_COUNT times,
	 * the currentLocationCount is incremented.
	 * If the user has been in the current temporary location for more than the MAX_COUNT times,
	 * it is assumed the user is not changing locations and has been in the same place. The temporary
	 * and actual current locations are the same.
	 * If the user has been in the current temporary location for the same amount of times as MAX_COUNT,
	 * and the temporary location is different than the assumed current location, it is determined
	 * that the user has changed locations and all necessary updates need to take place.
	 * @param newLoc - the current location as determined by iBeacon signals
	 */
	
	public void updateTempLocation(int newLoc) {
		
		if (newLoc != this.currentTempLocation) {
			
			this.currentTempLocation = newLoc;
			this.currentLocationCount = 0;
			
		}
		
		else {
			
			this.currentLocationCount ++;
			
			if (this.currentLocationCount == 5) {
				if (currentTempLocation != currentLocation) {
					changedLocation();
				}
			}
		}
		
	}

	/**
	 * The user has been in the current temp location enough times that it is determined
	 * the user has changed locations. The current location is updated, and the necessary
	 * DB entries are made.
	 */
	private void changedLocation() {
		
		this.currentLocation = this.currentTempLocation;
		
		/*
		 *  If there is a previous Location DB entry without an exit time, update the time.
		 *  Insert a new Location DB entry with the current time.
		 */
		
	}

	/**
	 * Starts tracking the user's location Starts a Handler to schedule the
	 * repeating task. Checks if a user's zone has not changed for at least some
	 * minimum number of cycles If the zone has not changed, the user is
	 * determined to be in that zone Checks if that zone is different from
	 * currentZone If it is different, the location database is updated to
	 * reflect the change by editing the exit time of the last entry, and
	 * inserting a new entry with the entry time
	 */
	public void startTracking() {

	}

	/**
	 * Stops tracking the user's location The last entry in the location
	 * database is updated with the exit time
	 */
	public void stopTracking() {

	}
	
	/*******************
	 * 
	 * Getters and Setters
	 * 
	 ******************/
	
	public int getCurrentLocation() {
		
		return this.currentLocation;
		
	}
	
	public void setCurrentLocation(int i) {
		
		this.currentLocation = i;
	
	}

}
