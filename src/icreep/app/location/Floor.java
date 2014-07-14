package icreep.app.location;

import android.util.SparseArray;

public class Floor {
	
	private static final SparseArray<String> zoneToFloor = new SparseArray<String>();
	static {
		zoneToFloor.append(1, "Second Floor");
		zoneToFloor.append(2, "Second Floor");
		zoneToFloor.append(3, "Second Floor");
		zoneToFloor.append(4, "Second Floor");
		zoneToFloor.append(5, "Second Floor");
		zoneToFloor.append(6, "Second Floor");
		zoneToFloor.append(7, "Second Floor");
		zoneToFloor.append(8, "Second Floor");
		zoneToFloor.append(9, "Second Floor");
		zoneToFloor.append(10, "Second Floor");
		zoneToFloor.append(11, "Second Floor");
	}
	
	public static String getFloor(int zone) {
		
		if (zone == -1) {
			return "Outside";
		}
		
		else if (zone == -2) {
			return "";
		}
		
		else {
			return zoneToFloor.get(zone, "Unknown");
		}
	}
}
