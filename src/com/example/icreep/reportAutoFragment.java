package com.example.icreep;

import android.R.bool;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;

public class reportAutoFragment extends Fragment {
		
		Switch switched;
		Button save ;
		TimePicker tp ;
		int hour = 0 ;
		int min = 0 ;
		//the following is how you get your text pixels to the correct size depending on the screen
    	//16*getResources().getDisplayMetrics().density
		float correctTextpixel = 16*getResources().getDisplayMetrics().density;
		@Override
		public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		
		}
		
		/* please note that the switch will depend on the following possibly, db, shared preferences or 
		 * savedInstanceState
		 *  
		 * (non-Javadoc)
		 * @see android.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
		 */
	    @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                             Bundle savedInstanceState) {
	        // Inflate the layout for this fragment
	    	// Return inflater.inflate(R.layout., container, false);
	    	
	    	View v = inflater.inflate(R.layout.reportsautofragment, container,false) ;
	    	
	    	
	    	//getting all the components for use and shaping of sizes 
	    	save = (Button) v.findViewById(R.id.saveButton);
	    	switched = (Switch) v.findViewById(R.id.switchBar);	    	
	    	tp = (TimePicker) v.findViewById(R.id.timePicker);	    	
	    	TextView deli = (TextView) v.findViewById(R.id.Delivery);	    	
	    	TextView deliTime = (TextView)v.findViewById(R.id.DeliveryTime);
	    	tp.setEnabled(false);
	    	
	    	//ensuring all text pixels are the correct size
	    	save.setTextSize(correctTextpixel);
	    	deli.setTextSize(correctTextpixel);
	    	deliTime.setTextSize(correctTextpixel);
	    	switched.setTextSize(correctTextpixel);
	    	
	    	
	    	//all the listeners using unnamed inner classes to avoid id checks
	    	switched.setOnCheckedChangeListener(new OnCheckedChangeListener() {
	    		
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					// TODO Auto-generated method stub
					if (isChecked == true)
					{
						tp.setEnabled(true);
						
						
					}else
					{
						tp.setEnabled(false);
						save.setEnabled(false);
						// will have to remove the automatic time from the database
					}
				}
			});
	    	
	    	
	    	//this will get the time for the time picker and store it into the user's profile
	    	save.setOnClickListener(new OnClickListener() {
				//listener for when you click save
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
				save.setEnabled(false);
				int storehour = tp.getCurrentHour();
				int storeminute = tp.getCurrentMinute();
				writeToDB(storehour, storeminute);
				
				
				}
			});
	    	
	    	tp.setIs24HourView(true);
	    	setTheTimePicker() ; //setting it's time
	    	
	    	
	    	tp.setOnTimeChangedListener(new OnTimeChangedListener() {	    		
	    		/*must store data of original time and then decide if i should enable button or not
	    		 **also i must read from the DB for the users stored values
	    		 **tp.setcurrenthour....
	    		 **store those read values to check
	    		 *it automatically defaults to the current time which is cool*/
	    		
				@Override
				public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
					// TODO Auto-generated method stub
				if (!((hourOfDay == hour) && (minute == min)))
				{
				save.setEnabled(true);	
				}else save.setEnabled(false);
				
				}
			});
	    	
	    	
	    	
	    	
	    	return v ;
	    }	
	    
	  //setting of the time will be here, note is in the set below
	    private void setTheTimePicker()
	    {
	    	if (switched.isChecked() == true)
	    	{
	    		getAutoTime();
	    		tp.setCurrentHour(hour);
	    		tp.setCurrentMinute(min);
	    	}
	    }
	    
	    //connect to DB and get automated report time from the table
	    private void getAutoTime()
	    {
	    	
	    }	    
	    
	    // this will be invoked when the save button is clicked and will 
	    // store the latest hour and minute that the user wants there auto report at
	    private void writeToDB(int stoh,int stom)
	    {
	    	
	    }
	
	 @Override
	public void onStart() 
	    {
	        super.onStart();
	        Log.d("onStart", "onStart");
	    }    
	    
	    @Override
	    public void onResume() 
	    {
	        super.onResume();
	        Log.d("onResume", "onResume");  
	    }    
	      
	    @Override
	    public void onPause() 
	    {
	        super.onPause();
	        Log.d("onPause", "onPause");
	    }    

	    @Override
	    public void onStop() 
	    {
	        super.onStop();
	        Log.d("vince", "onStop");
	    }    
	    
	    
	    @Override
	    public void onDestroy() 
	    {
	    	super.onDestroy();
	    	Log.d("vince", "onDestroy");
	    }
	    
	   @Override
	   public void onSaveInstanceState(Bundle out) 
	    {
	    	super.onSaveInstanceState(out);
	    	
	    }
	    
	    
	
	
}
