package icreep.app;

import android.app.Application;

public class ICreepApplication extends Application {
	
	private static ICreepApplication singleton;
	
	private boolean hasStartedRanging = false;
	private int currentLocation = -2;
	private int currentFloor = 2;
	private long lastEntryID = -1;
	private long timeEntered;
	private String bossID;
	private boolean isTrackingBoss = false;
	
	public ICreepApplication getInstance(){
		return singleton;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		singleton = this;
		
		// Access shared preferences or DB to get current location
	}
	
	/**
	 * Location is determined by the minor of the closest beacon.
	 * Out of office returns -1.
	 * Unknown returns -2.
	 * @return location
	 */
	public int getCurrentLocation() {
		return currentLocation;
	}
	
	public void setCurrentLocation(int newLoc) {
		this.currentLocation = newLoc;
	}
	
	public int getCurrentFloor() {
		return currentFloor;
	}
	
	public void setCurrentFloor(int newFloor) {
		this.currentFloor = newFloor;
	}
	
	public boolean hasStartedRanging() {
		return this.hasStartedRanging;
	}
	
	public void startedRanging() {
		this.hasStartedRanging = true;
	}
	
	public void stoppedRanging() {
		this.hasStartedRanging = false;
	}
	
	public void setTime(long time) {
		this.timeEntered = time;
	}
	
	public long getTime() {
		return this.timeEntered;
	}
	
	public void setLastEntryID(long id) {
		this.lastEntryID = id;
	}
	
	public long getLastEntryID() {
		return this.lastEntryID;
	}
	
	public void setBossID(String id) {
		this.bossID = id;
	}
	
	public String getBossID() {
		return this.bossID;
	}
	
	public boolean isTrackingBoss() {
		return this.isTrackingBoss;
	}
	
	public void setTrackingBoss(boolean tracking) {
		this.isTrackingBoss = tracking;
	}
	
	

}
