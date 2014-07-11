package icreep.app.report;

import icreep.app.location.Floor;
import icreep.app.location.Zone;

public class TimePlace
{
	private String location = "";
	private double hours = 0;
	private String floor = "";
	private int zoneID;

	/*
	 * Pre-Conditions: The String Location and the amount of hours spent and
	 * floor Post-conditions: > assigns variables to the class variables
	 */
	public TimePlace(String loc, double h, String f)
	{
		location = loc;
		hours = h;
		floor = f;
	}
	
	public TimePlace(String loc, double h, String f, int zID)
	{
		location = loc;
		hours = h;
		floor = f;
		zoneID = zID;
	}
	
	public TimePlace(double h, int zoneID) {
		
		this.zoneID = zoneID;
		this.hours = h;
		this.location = Zone.getLocation(zoneID);
		this.floor = Floor.getFloor(zoneID);
		
	}

	/*
	 * Pre-Conditions: The added hours spent at this location Post-conditions: >
	 * adds H hours to the class hours
	 */
	public void increaseTimeSpent(double h)
	{
		hours = hours + h;
	}

	/*
	 * Pre-Conditions: None Post-conditions: > returns the amount of hours spent
	 * at this location
	 */
	public double getTimeSpent()
	{
		return hours;
	}

	/*
	 * Pre-Conditions: None Post-conditions: > returns the location
	 */
	public String getLocation()
	{
		return location;
	}

	/*
	 * Pre-Conditions: None Post-conditions: > returns the floor
	 */
	public String getFloor()
	{
		return floor;
	}
	
	public void setZoneID(int id) {
		this.zoneID = id;
	}
	
	public int getZoneID() {
		return this.zoneID;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		
		final TimePlace other = (TimePlace) obj;
		if (zoneID == other.getZoneID()) {
			return true;
		}
		else {
			return false;
		}
	}
	
	@Override
	public int hashCode() {
		return this.zoneID;
	}

}
