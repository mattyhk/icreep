package icreep.app.beacon;

import java.util.LinkedList;

import com.radiusnetworks.ibeacon.IBeacon;


/**
 * Class representing a Beacon object. 
 * Handles storing received signals, updating windows, calculating averages.
 * @author mkerr
 *
 */
public class BeaconModel {
	
	private static final double DEFAULT_ACCURACY = 30;
	private static final int WINDOW_LENGTH = 5;
	private static final int MAX_NOT_FOUND = 3;
	private static final double DEFAULT_WEIGHT = 0.2;
	
	private int major;
	private int minor;
	
	private double accuracy = DEFAULT_ACCURACY;
	
	private LinkedList<Double> accuracyWindow = new LinkedList<Double>();
	
	private int notFoundCount = 0;
	
	public BeaconModel(int major, int minor) {
		
		this.major = major;
		this.minor = minor;
		
	}
	
	/********
	 * 
	 * Public and Private Methods to Modify the Beacon Model
	 * 
	 *******/
	
	
	/**
	 * Update the Beacon Model with its respective iBeacon.
	 * If the accuracy of the iBeacon is 0, or greater then the maximum accuracy, the beacon is determined 
	 * to not have been found. This prompts the call of the method updateNotFound()
	 * Else, the beacon is determined to have been found, and the accuracy window is updated
	 * @param iBeacon - the corresponding iBeacon
	 */
	public void updateBeaconModel(IBeacon iBeacon) {
		
		this.accuracy = iBeacon.getAccuracy() > 0 && iBeacon.getAccuracy() < DEFAULT_ACCURACY ? 
				iBeacon.getAccuracy() : DEFAULT_ACCURACY;
		
		if (this.accuracy != DEFAULT_ACCURACY) {
			
			updateAccuracyWindow();
			
		}
		
		else {
			
			updateNotFound();
			
		}
	}
	
	/**
	 * Method is called if the corresponding iBeacon has not been found, or if the accuracy value is not within
	 * the default parameters. Increments the number of times the beacon has not been found in a row, and if this
	 * value exceeds the threshold, it is assumed the beacon is no longer in the area. Calls resetAccuracy().
	 */
	public void updateNotFound() {
		
		this.notFoundCount++;
		if (this.notFoundCount > MAX_NOT_FOUND) {
			
			resetAccuracy();
			
		}
	}
	
	/**
	 * Method is called if it is determined that the user has left the immediate area of the beacon. 
	 * Its accuracy is reverted back to the default value, the window cleared, and its running notFoundCount
	 * updated to the maximum value.
	 */
	private void resetAccuracy() {
		
		this.accuracy = DEFAULT_ACCURACY;
		this.accuracyWindow.clear();
		this.notFoundCount = MAX_NOT_FOUND;
		
	}
	
	/**
	 * Method is called if the icreep.app.beacon has been found and its accuracy value is within the parameters.
	 * Checks if the window has exceeded its maximum length.
	 * If it has, pops the last value inserted and adds the newest value.
	 */
	private void updateAccuracyWindow() {
		
		this.notFoundCount = 0;
		
		if (this.accuracyWindow.size() >= WINDOW_LENGTH){
			
			this.accuracyWindow.poll();
			
		}
		
		this.accuracyWindow.add(this.accuracy);
		
	}
	

	/*******
	 * 
	 * Getters and Setters
	 * 
	 ******/
	
	/** 
	 * @return latest accuracy
	 */
	public double getAccuracy() {
		
		return this.accuracy;
		
	}
	
	/**
	 * Calculates a weighted average of the values in the window.
	 * If the window is of size 0, the beacon is out of range and the default accuracy is returned
	 * @return - a weighted average representing the accuracy
	 */
	public double getWeightedAccuracy() {
		
		if (this.accuracyWindow.size() == 0) {
			
			return this.accuracy;
			
		}
		
		double accuracy = 0;
		double weight = 0;
		
		for (int i = 0; i < this.accuracyWindow.size(); i++) {
			
			double tempAccuracy = this.accuracyWindow.get(i);
			double tempWeight = Math.exp(i * DEFAULT_WEIGHT);
			
			accuracy += tempAccuracy * tempWeight;
			weight += tempWeight;
					
		}
		
		return accuracy / weight;
	}
	
	public int getMajor() {
		
		return this.major;
	
	}
	
	public int getMinor() {
		
		return this.minor;
		
	}
}
