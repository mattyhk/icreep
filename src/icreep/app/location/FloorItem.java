package icreep.app.location;


public class FloorItem implements ListItem {
	
	private final String title;
	
	public FloorItem(String floor){
		this.title = floor;
	}
	
	@Override
	public boolean isFloor() {
		return true;
	}
	@Override
	public String getTitle() {
		return title;
	}

}
