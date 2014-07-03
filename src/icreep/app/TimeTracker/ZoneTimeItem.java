package icreep.app.TimeTracker;

import icreep.app.Location.ListItem;

public class ZoneTimeItem implements ListItem {
	
	private final String title;
	private final String subtitle;
	private final String time;
	
	public ZoneTimeItem(String zone, String location, String time){
		this.title = zone;
		this.subtitle = location;
		this.time = time;
	}

	@Override
	public boolean isFloor() {
		return false;
	}

	@Override
	public String getTitle() {
		return title;
	}
	
	public String getSubtitle() {
		return subtitle;
	}
	
	public String getTime() {
		return time;
	}

}
