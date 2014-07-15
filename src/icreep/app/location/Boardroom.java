package icreep.app.location;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Boardroom {
	private final static Integer[] BOARDROOM_ID = new Integer[] {1, 7, 8, 12, 13, 14, 18, 19, 28, 29, 30, 32, 33};
	private final static Set<Integer> boardrooms = new HashSet<Integer>(Arrays.asList(BOARDROOM_ID));
	
	public static boolean isBoardroom(int location) {
		return boardrooms.contains(location);
	}
}
