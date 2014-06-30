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
import android.widget.TimePicker;

public class reportAutoFragment extends Fragment {
		
		Switch switched;
		Button save ;
		TimePicker tp ;
		@Override
		public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		
		}
	    @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                             Bundle savedInstanceState) {
	        // Inflate the layout for this fragment
	      //  return inflater.inflate(R.layout., container, false);
	    	View v = inflater.inflate(R.layout.reportsautofragment, container,false) ;
	    	save = (Button) v.findViewById(R.id.saveButton);
	    	switched = (Switch) v.findViewById(R.id.switchBar);
	    	tp = (TimePicker) v.findViewById(R.id.timePicker);
	    	tp.setEnabled(false);
	    	switched.setOnCheckedChangeListener(new OnCheckedChangeListener() {
	    		
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					// TODO Auto-generated method stub
					if (isChecked == true)
					{
						tp.setEnabled(true);
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
				
				}
			});
	    	
	    	tp.setIs24HourView(true);
	    	
	    	
	    	return v ;
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
