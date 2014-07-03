package icreep.app.timetracker;

import icreep.app.R;
import icreep.app.location.FloorItem;
import icreep.app.location.ListItem;
import icreep.app.location.ZoneItem;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class TimeTrackerListAdapter extends ArrayAdapter<ListItem> {
	
	private Context context;
	private ArrayList<ListItem> items;
	private LayoutInflater vi;
	
	public TimeTrackerListAdapter(Context context, ArrayList<ListItem> items) {
		
		super(context, 0, items);
		
		this.context = context;
		this.items = items;
		vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View v = convertView;
		
		final ListItem i = items.get(position);
		
		if (i != null) {
			
			if (i.isFloor()){
				FloorItem floor = (FloorItem) i;
				v = vi.inflate(R.layout.list_item_floor, null);
				
				v.setOnClickListener(null);
				v.setOnLongClickListener(null);
				v.setLongClickable(false);
				
				final TextView floorView = (TextView) v.findViewById(R.id.list_item_floor_text);
				floorView.setText(floor.getTitle());
				floorView.setTextColor(context.getResources().getColor(R.color.whiteColor));
				
			}
			
			else {
				
				ZoneTimeItem zone = (ZoneTimeItem) i;
				v = vi.inflate(R.layout.list_item_zone, null);
				final TextView title = (TextView) v.findViewById(R.id.time_tracker_list_item_zone_title);
				final TextView subtitle = (TextView) v.findViewById(R.id.time_tracker_list_item_zone_summary);
				final TextView time = (TextView) v.findViewById(R.id.time_tracker_list_item_zone_time);
				
				if (title != null)
					title.setText(zone.getTitle());
				if (subtitle != null)
					subtitle.setText(zone.getSubtitle());
				if (time != null)
					subtitle.setText(zone.getTime());
			}
			
		}
		
		return v;
	}

}
