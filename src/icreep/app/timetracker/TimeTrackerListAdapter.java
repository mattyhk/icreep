package icreep.app.timetracker;

import icreep.app.R;
import icreep.app.report.TimePlace;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class TimeTrackerListAdapter extends ArrayAdapter<TimePlace> {
	
	private ArrayList<TimePlace> zones;
	private LayoutInflater vi;
	
	public TimeTrackerListAdapter(Context context, ArrayList<TimePlace> zones) {
		
		super(context, 0, zones);
		
		this.zones = zones;
		vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);		
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View v = convertView;
		
		final TimePlace timePlace = zones.get(position);
		
		if (timePlace != null) {

				v = vi.inflate(R.layout.list_item_zone_time, null);
				final TextView title = (TextView) v.findViewById(R.id.time_tracker_list_item_zone_time_title);
				final TextView subtitle = (TextView) v.findViewById(R.id.time_tracker_list_item_zone_time_summary);
				final TextView time = (TextView) v.findViewById(R.id.time_tracker_list_item_zone_time);
				
				double timeSpent = timePlace.getTimeSpent() * 3600.0;
				int seconds = (int) (timeSpent % 60);
				int minutes = (int) ((timeSpent / 60) % 60);
				int hours = (int) ((timeSpent / 3600) % 60);
				
				if (title != null)
					title.setText(String.valueOf(timePlace.getZoneID()));
				if (subtitle != null)
					subtitle.setText(timePlace.getLocation());
				if (time != null)
					time.setText(String.format
							("%02d:%02d:%02d", hours, minutes, seconds));;
		}
		
		return v;
	}

}
