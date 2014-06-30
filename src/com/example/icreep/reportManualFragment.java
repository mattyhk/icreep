package com.example.icreep;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class reportManualFragment extends Fragment{

	
	Button sendreport ;
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	
	 @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                             Bundle savedInstanceState) {
		 
		 View v = inflater.inflate(R.layout.reportsmanualfragment, container,false) ;
		 sendreport = (Button) v.findViewById(R.id.sendReportButton);
		 sendreport.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		 
		 
		 return container;	 
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
