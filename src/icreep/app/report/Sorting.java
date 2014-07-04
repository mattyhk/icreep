package icreep.app.report;

import java.util.ArrayList;

public class Sorting {

	/*
	 * Pre-Conditions: > ArrayList of time places that is unsorted.
	 * Post-conditions: > Sorts the arrayList of time places > The sort is done
	 * on the floor > items order: Ground Floor, First Floor, Second Floor
	 */
	public ArrayList<TimePlace> InsertionSort(ArrayList<TimePlace> a) {
		ArrayList<TimePlace> list = new ArrayList<TimePlace>();
		if (a.size() == 0) {
			return new ArrayList<TimePlace>();
		}
		list.add(a.get(0));
		for (int i = 1; i < a.size(); i++) {
			TimePlace t = a.get(i);
			if (t.getFloor().equals("Ground")) {
				list.add(0, t);
			} else if (t.getFloor().equals("Second")) {
				list.add(list.size(), t);
			} else {
				int j = 0;
				while (!(list.get(j).getFloor().equals("First"))
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
			if (tt.getFloor() == "Ground") {
				listG.add(tt);
			}
		}

		for (TimePlace tt : list) {
			if (tt.getFloor() == "First") {
				listF.add(tt);
			}
		}

		for (TimePlace tt : list) {
			if (tt.getFloor() == "Second") {
				listSE.add(tt);
			}
		}
		listG = secondarySortDescription(listG);
		listF = secondarySortDescription(listF);
		listSE = secondarySortDescription(listSE);
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

	private ArrayList<TimePlace> secondarySortDescription(ArrayList<TimePlace> a) 
	{
		int in, out;

		for (out = 1; out < a.size(); out++) {
			TimePlace temp = a.get(out);
			in = out;

			while (in > 0
					&& a.get(in-1).getLocation().compareTo(temp.getLocation()) > 0) {
				a.set(in, a.get(in-1));
				--in;
			}
			a.set(in, temp);
		}
		return a ;
	}
	
}