package icreep.app.location;

import android.util.SparseArray;

public class Floor {
	
	private static final SparseArray<String> zoneToFloor = new SparseArray<String>();
	static {
		
		for (int i = 1; i <= 11; i++) {
			zoneToFloor.append(i, "Second Floor");
		}
		
		for (int i = 12; i <= 26; i++) {
			zoneToFloor.append(i, "Ground Floor");
		}
		
		for (int i = 27; i <= 48; i++) {
			
			if (i == 34) {
				zoneToFloor.append(i, "Ground Floor");
			}
			
			else {
				zoneToFloor.append(i, "First Floor");
			}
			
		}
		
		
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
