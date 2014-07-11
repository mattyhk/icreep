package icreep.app.location;

import java.util.HashMap;

/**
 * An enum type representing the predefined Zones that exist
 * 
 * @author mkerr
 * 
 */
public class Zone {
	
	private static final HashMap<Integer, String> zoneToLocation = new HashMap<Integer, String>();
	static {
		zoneToLocation.put(1, "S3");
		zoneToLocation.put(2, "Men's Bathroom");
		zoneToLocation.put(3, "Intern Zone");
		zoneToLocation.put(4, "Denzil Zone");
		zoneToLocation.put(5, "Focus Room");
		zoneToLocation.put(6, "Kabir Zone");
		zoneToLocation.put(7, "S2");
		zoneToLocation.put(8, "S1");
		zoneToLocation.put(9, "Second Floor Kitchen");
		zoneToLocation.put(10, "Water Zone");
		zoneToLocation.put(11, "S3");
	}
}
