package icreep.app.location;

import icreep.app.R;
import icreep.app.report.TimePlace;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class DailyMovementAdapter extends ArrayAdapter<TimePlace> {
	
	private ArrayList<TimePlace> locs;
	private LayoutInflater vi;
	
	public DailyMovementAdapter(Context context, ArrayList<TimePlace> locs) {
		
		super(context, 0, locs);
		
		this.locs = locs;
		vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View v = convertView;
		
		final TimePlace tp = locs.get(position);
		
		if (tp != null) {
				
				v = vi.inflate(R.layout.list_item_zone, null);
				final TextView title = (TextView) v.findViewById(R.id.daily_movement_list_item_zone_title);
				final TextView subtitle = (TextView) v.findViewById(R.id.daily_movement_list_item_zone_summary);
				
				if (title != null)
					title.setText(String.valueOf(tp.getZoneID()));
				if (subtitle != null)
					subtitle.setText(tp.getLocation());
			
		}
		
		return v;
	}

}
