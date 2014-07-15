package icreep.app.beacon;

import icreep.app.AudioManagingController;
import icreep.app.ICreepApplication;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.content.Context;

import com.radiusnetworks.ibeacon.IBeacon;


/**
 * Class that holds a collection of every beacon in the tracking system. Handles updating the beacon objects
 * when the device receives new values from beacons in range
 * 
 * @author mkerr
 *
 */

public class BeaconCollection {
	
	private final static int MAJOR = 3;
	private final static int NUMBEACONS = 48;
	private final static double DEFAULT_ACCURACY = 30;
	private final static int OUTDOOR = -1;
	
	private int closestBeacon = OUTDOOR;
	
	private final List<BeaconModel> myBeacons = new ArrayList<BeaconModel>();
	private AudioManagingController audioController;
	private ICreepApplication mApplication;
	
	public BeaconCollection(Context context) {
		this.audioController = new AudioManagingController(context);
		this.mApplication = (ICreepApplication) context.getApplicationContext();
		
		for (int i = 0; i <= NUMBEACONS; i++) {
			
			BeaconModel tempBeacon = new BeaconModel(MAJOR, i);
			myBeacons.add(tempBeacon);
			
		}
	}
	
	/**
	 * Method to handle updating all of the MyBeacon objects, when receiving a collection
	 * of iBeacons in range. 
	 * Assumes that all iBeacons passed in have a minor key set within the maximum value of NUMBEACONS, and no two
	 * iBeacons share the same minor key.
	 * Modifies the closestBeacon field by first checking which of the beacons in range, if any,
	 * is the closest. Sets the field to the beacon's index, otherwise sets it to -1 representing
	 * Out of Office.
	 */
	public void processIBeacons(Collection<IBeacon> iBeacons) {
		
		String currentBoss = mApplication.getBossID();
		boolean inArea = false;
			
		List<IBeacon> iBeaconList = new ArrayList<IBeacon>(iBeacons); 
		
		/*
		 * Sort the beacons received by their minor ID
		 */
		Collections.sort(iBeaconList, new Comparator<IBeacon>() {
			public int compare(final IBeacon o1, final IBeacon o2){
				return o1.getMinor() - o2.getMinor();
			}
		});
		
		/*
		 * Iterate through each beacon in the system. If the beacon matches one in the list 
		 * of iBeacons found, update the beacon.
		 * If the beacon does not match any of the beacons found, update the beacon as a 
		 * missing beacon.
		 * Also, check which beacon is closest. If none of the beacons are in range, set 
		 * the index to -1. Else, set the index to the index of the closest beacon.
		 */
		
		int j = 0;
		double tempAcc = DEFAULT_ACCURACY;
		int closest = -1;
			
		for (int i = 0; i <= NUMBEACONS; i++) {
			
			BeaconModel mBeacon = myBeacons.get(i);
			
			if (j < iBeaconList.size()) {
				if (mBeacon.getMinor() == iBeaconList.get(j).getMinor()) {
					String uuid = iBeaconList.get(j).getProximityUuid();
					if (uuid.equals(currentBoss)) {
						inArea = true;
					}
					mBeacon.updateBeaconModel(iBeaconList.get(j));
					j++;	
				}
				
				else {
					mBeacon.updateNotFound();		
				}
			}
			
			else {
				mBeacon.updateNotFound();
			}
			
			double acc = mBeacon.getWeightedAccuracy();
			if (acc < tempAcc) {
				tempAcc = acc;
				closest = i;
			}
		}
		
		/*
		 * Set the closest beacon index
		 */
		setClosestBeacon(closest);
		
		for (int i = j; i < iBeaconList.size(); i++) {
			String uuid = iBeaconList.get(i).getProximityUuid();
			if (uuid.equals(currentBoss)) {
				inArea = true;
			}
		}
		
		if (inArea && mApplication.isTrackingBoss()) {
			audioController.fireRingTone();
		}

	}
	
	/**************************
	 * 
	 * Getters and Setters
	 * 
	 **************************/
	
	/**
	 * Set the index of the closest iBeacon as determined by weighted average
	 * If none of the beacons are in range, sets it to 'Outdoors', or -1
	 * @param i - index of closest beacon
	 */
	private void setClosestBeacon(int i) {
		
		this.closestBeacon = i;
	
	}
	
	/**
	 * Gets the Minor value of the closest beacon.
	 * If there are no beacons in range, returns -1.
	 * @return minor - the value of the minor of the closest beacon
	 */
	public int getClosestBeaconMinor() {
		
		if (this.closestBeacon == OUTDOOR) {
			return OUTDOOR;
		}
		
		else {
			return myBeacons.get(closestBeacon).getMinor();	
		}
		
	}
	
	@Override
	public String toString() {
		return myBeacons.toString();
	}
}
