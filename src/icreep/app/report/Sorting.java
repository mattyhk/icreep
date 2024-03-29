package icreep.app.report;

import icreep.app.location.Floor;

import java.util.ArrayList;

public class Sorting
{

	/*
	 * Pre-Conditions: > ArrayList of time places that is unsorted.
	 * Post-conditions: > Sorts the arrayList of time places > The sort is done
	 * on the floor > items order: Ground Floor, First Floor, Second Floor
	 */
	public static ArrayList<TimePlace> InsertionSort(ArrayList<TimePlace> a)
	{
		if (a == null)
		{
			return new ArrayList<TimePlace>();
		}
		
		if (a.size() == 0) {
			return new ArrayList<TimePlace>();
		}
		
		ArrayList<TimePlace> list = new ArrayList<TimePlace>();

		ArrayList<TimePlace> listOut = new ArrayList<TimePlace>();
		ArrayList<TimePlace> listG = new ArrayList<TimePlace>();
		ArrayList<TimePlace> listF = new ArrayList<TimePlace>();
		ArrayList<TimePlace> listSE = new ArrayList<TimePlace>();

		for (int i = 0; i < a.size(); i++) {
			TimePlace t = a.get(i);
			if (t.getFloor().equals(Floor.getFloor(-1)))
			{
				listOut.add(t);
			}
			else if (t.getFloor().equals("Ground Floor")) {
				listG.add(t);
			} else if (t.getFloor().equals("First Floor")) {
				listF.add(t);
			} else {
				listSE.add(t);
			}
		}		
		
		listOut = secondarySortDescriptionAsc(listOut);
		listG = secondarySortDescriptionAsc(listG);
		listF = secondarySortDescriptionAsc(listF);
		listSE = secondarySortDescriptionAsc(listSE);
		
		list.addAll(listOut);
		list.addAll(listG);
		list.addAll(listF);
		list.addAll(listSE);
		

		return list;
	}

	// descending
	public static ArrayList<TimePlace> secondarySortDescriptiondesc(
			ArrayList<TimePlace> a)
	{
		
		ArrayList<TimePlace> toReturn = new ArrayList<TimePlace>();
		
		if (a.size() == 0) {
			return toReturn;
		}
		
		toReturn.add(a.get(0));

		for (int i = 0; i < a.size(); i++) {
			TimePlace toAdd = a.get(i);
			boolean added = false;
			for (int j = 1; j < toReturn.size(); j++) {
				String locAdd = toAdd.getLocation();
				TimePlace cur = toReturn.get(j);
				String locCur = cur.getLocation();
				if (locAdd.compareTo(locCur) < 0) {
					toReturn.add(j, toAdd);
					added = true;
					break;
				}
			}
			if (added == false) {
				toReturn.add(toAdd);
			}
		}
		return toReturn;

	}

	// Ascending
	private static ArrayList<TimePlace> secondarySortDescriptionAsc(
			ArrayList<TimePlace> a) {
		ArrayList<TimePlace> toReturn = new ArrayList<TimePlace>();
		
		if (a.size() == 0) {
			return toReturn;
		}
		
		toReturn.add(a.get(0));

		for (int i = 1; i < a.size(); i++) {
			TimePlace toAdd = a.get(i);
			boolean added = false;
			for (int j = 0; j < toReturn.size(); j++) {
				String locAdd = toAdd.getLocation();
				TimePlace cur = toReturn.get(j);
				String locCur = cur.getLocation();
				if (locAdd.compareTo(locCur) < 0) {
					toReturn.add(j, toAdd);
					added = true;
					break;
				}
			}
			if (added == false) {
				toReturn.add(toAdd);
			}
		}
		return toReturn;

	}
	
	public static ArrayList<TimePlace> join(ArrayList<TimePlace> in) {
		
		ArrayList<TimePlace> finalSortedTimePlaces = new ArrayList<TimePlace>();
		in = InsertionSort(in);
		
		if (in.size() == 0 || in.size() == 1) {
			return in;
		}
		
		else {
			
			TimePlace toAdd = in.get(0);
			in.remove(0);
			
			for(TimePlace tp : in)
			{
				
				if(tp.equals(toAdd))
				{
					toAdd.increaseTimeSpent(tp.getTimeSpent());
				} 
				else{
					finalSortedTimePlaces.add(toAdd);
					toAdd=tp;
				}
			}
			finalSortedTimePlaces.add(toAdd);
		}
			
		return finalSortedTimePlaces;
	}
	
	public boolean checkIfWorekdTooLong(ArrayList<TimePlace> in)
	{
			double total = 0;
			
			if(in != null){
				for(TimePlace tp: in){
					double time = tp.getTimeSpent();
					total+= time;
				}
			}
			
			int hours = (int) Math.floor((total / (1000*3600)));
			
			if (hours >=8)
				return true ;
		return false;
	}
	

}
