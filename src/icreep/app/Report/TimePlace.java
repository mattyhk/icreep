package icreep.app.Report;

public class TimePlace {
	private String location = "" ;
	private double hours = 0 ;
	private String floor = "" ;
	
		public TimePlace(String loc, double h,String f)
		{
			location = loc ;
			hours = h; 
			floor = f;
		}
		
		public void increaseTimeSpent(double h)
		{
			hours = hours + h ;
		}
		
		public double getTimeSpent()
		{
			return hours ;
		}
		
		public String getLocation()
		{
			return location ;
		}
		
		public String getFloor()
		{
			return floor ;
		}
		
		
		

}
