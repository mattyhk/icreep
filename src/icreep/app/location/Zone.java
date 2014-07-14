package icreep.app.location;

import android.util.SparseArray;

/**
 * Map for zone ID to location String
 * 
 * @author mkerr
 * 
 */
public class Zone {
	
	private static final SparseArray<String> zoneToLocation = new SparseArray<String>();
	static {
		zoneToLocation.append(1, "S3");
		zoneToLocation.append(2, "Men's Bathroom");
		zoneToLocation.append(3, "Intern Zone");
		zoneToLocation.append(4, "Denzil Zone");
		zoneToLocation.append(5, "Focus Room");
		zoneToLocation.append(6, "Kabir Zone");
		zoneToLocation.append(7, "S2");
		zoneToLocation.append(8, "S1");
		zoneToLocation.append(9, "Second Floor Kitchen");
		zoneToLocation.append(10, "Water Zone");
		zoneToLocation.append(11, "Second Floor Corner");
	}
	
	public static String getLocation(int zone) {
		
		if (zone == -1) {
			return "Outside";
		}
		
		else if (zone == -2) {
			return "";
		}
		
		else {
			return zoneToLocation.get(zone, "Unknown");
		}
	}
}
