package icreep.app.location;


public class ZoneItem implements ListItem {
	
	private final String title;
	private final String subtitle;
	
	public ZoneItem(String zone, String location) {
		
		this.title = zone;
		this.subtitle = location;
		
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

}
