package icreep.app.report;

public class TimePlace {
	private String location = "" ;
	private double hours = 0 ;
	private String floor = "" ;
	
		/*
		 * Pre-Conditions: The String Location and the amount of hours spent and floor
		 * Post-conditions: > assigns variables to the class variables
		 */
		public TimePlace(String loc, double h,String f)
		{
			location = loc ;
			hours = h; 
			floor = f;
		}
		
		/*
		 * Pre-Conditions: The added hours spent at this location
		 * Post-conditions: > adds H hours to the class hours
		 */
		public void increaseTimeSpent(double h)
		{
			hours = hours + h ;
		}
		
		/*
		 * Pre-Conditions: None
		 * Post-conditions: > returns the amount of hours spent at this location
		 */
		public double getTimeSpent()
		{
			return hours ;
		}
		
		/*
		 * Pre-Conditions: None
		 * Post-conditions: > returns the location
		 */
		public String getLocation()
		{
			return location ;
		}
		
		/*
		 * Pre-Conditions: None
		 * Post-conditions: > returns the floor
		 */
		public String getFloor()
		{
			return floor ;
		}
		
		
		

}
