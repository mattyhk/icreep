package icreep.app.location;


/**
 * A class that handles the current location of the user. The object receives 
 * the results of processing the beacon signals, and updates the current location of the
 * user. The class also handles updating the DB with the required information.
 * 
 * Current Location has following definitions:
 * -1 - the current location is outdoors
 * -2 - the current location is unknown
 * > 0 - the current location is defined
 * @author mkerr
 * 
 */
public class UserLocation {
	
	private final static int MAX_ENTRY_COUNT = 5;
	private final static int MAX_EXIT_COUNT = 5;
	private final static int UNKNOWN = -2;
	
	private int currentLocation;
	private int entryCount;
	private int exitCount;
	private int currentTempLocation;
	
	public UserLocation() {
		this.currentLocation = UNKNOWN;
		this.entryCount = 0;
		this.exitCount = 0;
	}
	
	
	/**
	 * Updates the current (temporary) location of the user. Depending on the number of times
	 * the user has been in the same current location, different updates will take place.
	 * If the user is not in the same the current location, the exitCount is incremented.
	 * Else, the exitCount is reset.
	 * If the new location is the same as the temporary location, the entryCount is incremented.
	 * Else, the entryCount is reset.
	 */
	
	public void updateLocation(int newLoc) {
		
		if (newLoc != this.currentLocation) {
		
			// Left current location		
			this.exitCount++;		
			// update leaving location
			updateLeftCurrentLocation();
			
			if (newLoc != this.currentTempLocation) {
				// Moving through locations
				this.entryCount = 0;				
			}
			
			else {				
				this.entryCount++;			
				// Entering a new location
				updateEnterNewLocation();				
			}	
		}
		
		else {
			
			// Staying (or returned) to current location
			
			this.exitCount = 0;
			
			if (newLoc != this.currentTempLocation) {				
				// Returned to a previous location
				this.entryCount = 0;			
			}
			
			else {				
				// Staying in the same location
				this.entryCount = MAX_ENTRY_COUNT + 1;				
			}
		}
		
		this.currentTempLocation = newLoc;
		
	}

	/**
	 * The user is no longer in the same current location. 
	 * Check if the user has left the current location for long enough that the current location 
	 * has changed, and make the necessary DB updates.
	 */
	private void updateLeftCurrentLocation() {
		
		if (this.exitCount == MAX_EXIT_COUNT) {
			
			// Update the DB - update the last location entry's exit time with the current time
			// Need to make sure the DB has a last entry
			
			// The user's current location has changed
			this.currentLocation = UNKNOWN;
			
		}
	
	}
	
	/**
	 * The user is entering a new location.
	 * If the user has been in the new location for more than the MAX_ENTRY_COUNT,
	 * the current location is updated and the DB appended.
	 */
	private void updateEnterNewLocation() {
		
		if (this.entryCount == MAX_ENTRY_COUNT) {
			
			this.currentLocation = this.currentTempLocation;
			
			// Update the DB - ensure the DB is in order before adding
		}
		
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
