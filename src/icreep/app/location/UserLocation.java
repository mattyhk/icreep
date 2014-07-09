package icreep.app.location;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import icreep.app.SharedPreferencesControl;
import icreep.app.db.iCreepDatabaseAdapter;


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
	private int userID;
	private long lastLocationID = 0;
	private Boolean hasQueriedDatabase = false;
	private String lastQueriedDate;
	private Set<Integer> visitedZones = new HashSet<Integer>();
	
	private iCreepDatabaseAdapter db;
	
	public UserLocation(Context context) {
		this.currentLocation = UNKNOWN;
		this.entryCount = 0;
		this.exitCount = 0;
		this.db = new iCreepDatabaseAdapter(context);
		this.lastQueriedDate = getDate();
		
		SharedPreferencesControl spc = new SharedPreferencesControl(context);
		this.userID = spc.getUserID();
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
			// Need to make sure the DB has a last entry with empty exit time
			if (this.lastLocationID > 0) {
				
				String time = getTime();
				
				if (this.db.updateExitTime(time, this.lastLocationID)) {
					Log.d("TEST", "Exit time was updated correctly");
					this.lastLocationID = 0;
				}
				
				else {
					Log.d("TEST", "Exit time was not updated correctly");
				}
			}
			
			// The user's current location has changed
			this.currentLocation = UNKNOWN;
			
		}
	
	}
	
	/**
	 * The user is entering a new location.
	 * If the user has been in the new location for more than the MAX_ENTRY_COUNT,
	 * the current location is updated and the DB appended.
	 * Also, checks if the location has been added to the set of visited locations. If not,
	 * adds the location.
	 */
	private void updateEnterNewLocation() {
		
		// Need to implement adding location to the set of visited locations
		
		if (this.entryCount == MAX_ENTRY_COUNT) {
			
			this.currentLocation = this.currentTempLocation;
			
			if (this.currentLocation != UNKNOWN) {
				
				String time = getTime();
				String date = getDate();
				
				long tempID = this.db.addNewLocation(this.userID, this.currentLocation, time, date);
				
				// Update the DB - ensure the DB is in order before adding new location entry
				if (tempID > 0) {
					Log.d("TEST", "Enter New Location Successfully");
					this.lastLocationID = tempID;
					
				}
				else {
					Log.d("TEST", "New Location was not entered successfully");
				}
			}		
		}
	}
	
	/**
	 * Returns the current time in 24 hour format as "22:10"
	 * @return time
	 */
	private String getTime() {
		Calendar c = Calendar.getInstance();
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);
		String time = "" + hour + ":" + minute;
		return time;
	}
	
	/**
	 * Returns the current date
	 * @return date
	 */
	@SuppressLint("SimpleDateFormat") 
	private String getDate() {
		Calendar c = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String formatted = format.format(c.getTime());
		return formatted;
	}
	
	/**
	 * If the DB has not been queried that day,
	 * queries the DB to find all of the locations the User has visited that day.
	 * Else, has no effect.
	 * Modifies the set of visited zones.
	 */
	private void findVisitedZones() {
		
		if (lastQueriedDate != getDate() || hasQueriedDatabase == false) {
			// Check database for visited locations
			
			this.lastQueriedDate = getDate();
			this.hasQueriedDatabase = true;
		}
		

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
	
	public Set<Integer> getVisitedZones() { 
		
		findVisitedZones();
		
		return this.visitedZones;
	}

}
