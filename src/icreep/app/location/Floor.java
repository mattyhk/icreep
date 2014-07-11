package icreep.app.location;

import android.util.SparseArray;

public class Floor {
	
	private static final SparseArray<String> zoneToFloor = new SparseArray<String>();
	static {
		zoneToFloor.append(1, "S3");
		zoneToFloor.append(2, "Men's Bathroom");
		zoneToFloor.append(3, "Intern Zone");
		zoneToFloor.append(4, "Denzil Zone");
		zoneToFloor.append(5, "Focus Room");
		zoneToFloor.append(6, "Kabir Zone");
		zoneToFloor.append(7, "S2");
		zoneToFloor.append(8, "S1");
		zoneToFloor.append(9, "Second Floor Kitchen");
		zoneToFloor.append(10, "Water Zone");
		zoneToFloor.append(11, "Second Floor Corner");
	}
	
	public static String getFloor(int zone) {
		
		if (zone == -1) {
			return "Outside";
		}
		
		else if (zone == -2) {
			return "";
		}
		
		else {
			return zoneToFloor.get(zone);
		}
	}
}
