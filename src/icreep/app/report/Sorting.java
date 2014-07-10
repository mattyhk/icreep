package icreep.app.report;

import java.util.ArrayList;

import android.util.Log;

public class Sorting
{

	/*
	 * Pre-Conditions: > ArrayList of time places that is unsorted.
	 * Post-conditions: > Sorts the arrayList of time places > The sort is done
	 * on the floor > items order: Ground Floor, First Floor, Second Floor
	 */
	public ArrayList<TimePlace> InsertionSort(ArrayList<TimePlace> a)
	{
		if (a == null)
		{
			return new ArrayList<TimePlace>();
		}
		ArrayList<TimePlace> list = new ArrayList<TimePlace>();
		
		if (a.size() == 0) {
			return new ArrayList<TimePlace>();
		}
		
		list.add(a.get(0));
		ArrayList<TimePlace> listOut = new ArrayList<TimePlace>();
		ArrayList<TimePlace> listG = new ArrayList<TimePlace>();
		ArrayList<TimePlace> listF = new ArrayList<TimePlace>();
		ArrayList<TimePlace> listSE = new ArrayList<TimePlace>();

		for (int i = 1; i < a.size(); i++) {
			TimePlace t = a.get(i);
			if (t.getFloor().equals("Outside"))
			{
				listOut.add(t);
			}
			else if (t.getFloor().equals("Ground Floor")) {
				listG.add(t);
			} else if (t.getFloor().equals("Second Floor")) {
				listF.add(t);
			} else {
				listSE.add(t);
			}
		}		
		listOut = secondarySortDescriptionAsc(listOut);
		listG = secondarySortDescriptionAsc(listG);
		listF = secondarySortDescriptionAsc(listF);
		listSE = secondarySortDescriptionAsc(listSE);
		list.clear();
		
		
		for (TimePlace tt : listOut) {
			list.add(tt);
		}
		
		for (TimePlace tt : listG) {
			list.add(tt);
		}

		for (TimePlace tt : listF) {
			list.add(tt);
		}

		for (TimePlace tt : listSE) {
			list.add(tt);
		}

		return list;
	}

	// descending
	public ArrayList<TimePlace> secondarySortDescriptiondesc(
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

	// Asc
	private ArrayList<TimePlace> secondarySortDescriptionAsc(
			ArrayList<TimePlace> a)
	{
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

}
