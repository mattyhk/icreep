package icreep.app;

import android.app.Application;

public class ICreepApplication extends Application {
	
	private static ICreepApplication singleton;
	
	int currentLocation = -1;
	int currentFloor = 2;
	
	public ICreepApplication getInstance(){
		return singleton;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		singleton = this;
		
		// Access shared preferences or DB to get current location
	}
	
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
	
	

}
