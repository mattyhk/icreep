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
		for (int i = 1; i < a.size(); i++) {
			Log.d("TEST", "Trying to index at position " + i + " but size is " + a.size());
			TimePlace t = a.get(i);
			if (t.getFloor().equals("Ground Floor")) {
				list.add(0, t);
			} else if (t.getFloor().equals("Second Floor")) {
				list.add(list.size(), t);
			} else {
				int j = 0;
				while (!(list.get(j).getFloor().equals("First Floor"))
						&& (list.get(j) != null)) {
					j = i + 1;
				}
				list.add(j, t);

			}
		}
		ArrayList<TimePlace> listG = new ArrayList<TimePlace>();
		ArrayList<TimePlace> listF = new ArrayList<TimePlace>();
		ArrayList<TimePlace> listSE = new ArrayList<TimePlace>();

		for (TimePlace tt : list) {
			if (tt.getFloor().equals("Ground Floor")) {
				listG.add(tt);
			}
		}

		for (TimePlace tt : list) {
			if (tt.getFloor().equals("First Floor")) {
				listF.add(tt);
			}
		}

		for (TimePlace tt : list) {
			if (tt.getFloor().equals("Second Floor")) {
				listSE.add(tt);
			}
		}
		listG = secondarySortDescriptionAsc(listG);
		listF = secondarySortDescriptionAsc(listF);
		listSE = secondarySortDescriptionAsc(listSE);
		list.clear();
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
