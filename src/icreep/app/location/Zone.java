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
		zoneToLocation.append(3, "CreepStars");
		zoneToLocation.append(4, "B.I. Joes");
		zoneToLocation.append(5, "Focus Room");
		zoneToLocation.append(6, "Rejects");
		zoneToLocation.append(7, "S2");
		zoneToLocation.append(8, "S1");
		zoneToLocation.append(9, "Kitchen");
		zoneToLocation.append(10, "Honey Badgers");
		zoneToLocation.append(11, "BAU");
		zoneToLocation.append(12, "G5");
		zoneToLocation.append(13, "G4");
		zoneToLocation.append(14, "G3");
		zoneToLocation.append(15, "Men's Bathroom");
		zoneToLocation.append(16, "Management");
		zoneToLocation.append(17, "HR Zone");
		zoneToLocation.append(18, "G2");
		zoneToLocation.append(19, "G1");
		zoneToLocation.append(20, "Game Zone");
		zoneToLocation.append(21, "Main Kitchen");
		zoneToLocation.append(22, "Auditorium");
		zoneToLocation.append(23, "Gym");
		zoneToLocation.append(24, "Inc. Station");
		zoneToLocation.append(25, "Spider Pigs");
		zoneToLocation.append(26, "Halaal Kitchen");
		zoneToLocation.append(27, "Reception");
		zoneToLocation.append(28, "F1");
		zoneToLocation.append(29, "F2");
		zoneToLocation.append(30, "F3");
		zoneToLocation.append(31, "Men's Bathroom");
		zoneToLocation.append(32, "F4");
		zoneToLocation.append(33, "F5");
		zoneToLocation.append(34, "Dining Area");
		zoneToLocation.append(35, "Open Lease");
		zoneToLocation.append(36, "Chill Zone");
		zoneToLocation.append(37, "Infrastructure");
		zoneToLocation.append(38, "ACT Team");
		zoneToLocation.append(39, "Brown Town");
		zoneToLocation.append(40, "ODT Team");
		zoneToLocation.append(41, "CDT Support 1");
		zoneToLocation.append(42, "CDT Support 2");
		zoneToLocation.append(43, "GR Team");
		zoneToLocation.append(44, "CDT Enhance.");
		zoneToLocation.append(45, "Kitchen");
		zoneToLocation.append(46, "Old Mutual");
		zoneToLocation.append(47, "Thunderbirds");
		zoneToLocation.append(48, "Finance");
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
