package icreep.app.location;

import icreep.app.R;
import icreep.app.R.id;
import icreep.app.R.layout;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class DailyMovementAdapter extends ArrayAdapter<ListItem> {
	
	private Context context;
	private ArrayList<ListItem> items;
	private LayoutInflater vi;
	
	public DailyMovementAdapter(Context context, ArrayList<ListItem> items) {
		
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
				
				ZoneItem zone = (ZoneItem) i;
				v = vi.inflate(R.layout.list_item_zone, null);
				final TextView title = (TextView) v.findViewById(R.id.daily_movement_list_item_zone_title);
				final TextView subtitle = (TextView) v.findViewById(R.id.daily_movement_list_item_zone_summary);
				
				if (title != null)
					title.setText(zone.getTitle());
				if (subtitle != null)
					subtitle.setText(zone.getSubtitle());
			}
			
		}
		
		return v;
	}

}
