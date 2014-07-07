package icreep.app.beacon;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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
	private final static int NUMBEACONS = 15;
	
	private final List<BeaconModel> myBeacons = new ArrayList<BeaconModel>();
	
	public BeaconCollection() {
		
		for (int i = 0; i <= NUMBEACONS; i++) {
			
			BeaconModel tempBeacon = new BeaconModel(MAJOR, i);
			myBeacons.add(tempBeacon);
			
		}
	}
	
	/**
	 * Method to handle updating all of the MyBeacon objects, when receiving a collection
	 * of iBeacons in range. 
	 * Assumes that all iBeacons passed in have a minor key set within the maximum value of NUMBEACONS, and no two
	 * iBeacons share the same minor key
	 */
	public List<BeaconModel> processIBeacons(Collection<IBeacon> iBeacons) {
			
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
		 */
		
		int j = 0;
		
		if (iBeaconList.size() == 0) {
			
			for (int i = 0; i <= NUMBEACONS; i++) {
				
				BeaconModel mBeacon = myBeacons.get(i);
				mBeacon.updateNotFound();
				
			}
		}
		
		else {
			
			for (int i = 0; i <= NUMBEACONS; i++) {
				
				BeaconModel mBeacon = myBeacons.get(i);
				
				if (j < iBeaconList.size()) {
					if (mBeacon.getMinor() == iBeaconList.get(j).getMinor()) {
						
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
			}
		}
		
		
		return myBeacons;
	}
}
