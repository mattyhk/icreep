package icreep.app;

import icreep.app.location.Floor;
import java.util.ArrayList;
import java.util.Collection;

import com.radiusnetworks.ibeacon.IBeacon;

import android.app.Application;

public class ICreepApplication extends Application {
	
	private static ICreepApplication singleton;
	
	private boolean hasStartedRanging = false;
	private int currentLocation = -2;
	private String currentFloor = "";
	private long lastEntryID = -1;
	private long timeEntered;
//	private String bossID = "f7826da6-4fa2-4e98-8024-bc5b71e0893e";
	private int bossID = 0;
	private boolean isTrackingBoss = false;
	private Collection<IBeacon> currentBeacons;
	
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
		setCurrentFloor(this.currentLocation);
	}
	
	public String getCurrentFloor() {
		return currentFloor;
	}
	
	public void setCurrentFloor(int newFloor) {
		this.currentFloor = Floor.getFloor(newFloor);
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
	
	public void setBossID(int id) {
		this.bossID = id;
	}
	
	public int getBossID() {
		return this.bossID;
	}
	
	public boolean isTrackingBoss() {
		return this.isTrackingBoss;
	}
	
	public void setTrackingBoss(boolean tracking) {
		this.isTrackingBoss = tracking;
	}
	
	public void setBeaconList(Collection<IBeacon> beacons){
		this.currentBeacons = beacons;
	}
	
	public Collection<IBeacon> getBeaconList() {
		if (this.currentBeacons != null) {
			return this.currentBeacons;
		}
		else {
			return new ArrayList<IBeacon>();
		}
	}
	
	

}
