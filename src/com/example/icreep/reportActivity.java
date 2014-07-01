package com.example.icreep;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Layout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class reportActivity extends FragmentActivity {

	Button auto, manual ;
	boolean automated = true ;
	
	
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.reports); 
	        auto = (Button) findViewById(R.id.autoButton);
	        manual = (Button) findViewById(R.id.manualButton) ;	        
	        TextView reports = (TextView) findViewById(R.id.textViewMain);
	        TextView userDescrip = (TextView) findViewById(R.id.userDescript);
	        	        
	        float correctTextpixel = 16*getResources().getDisplayMetrics().density;
	        reports.setTextSize(correctTextpixel);
	        userDescrip.setTextSize(correctTextpixel);
	        auto.setTextSize(correctTextpixel);
	        manual.setTextSize(correctTextpixel);        
	        
	        addautoFragment(savedInstanceState);
	      /*  
	        reportAutoFragment frag = new reportAutoFragment();
	        android.app.FragmentManager fragmentManager = getFragmentManager();
	 		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
	 		
	 		fragmentTransaction.add(R.id.autoFragLayout, frag,"auto");
	 		fragmentTransaction.commit();
	 		*/
	        /*here I will add the code required to get the users info with using his id
	        * possibly from sp, sis
	        * userdescrip.setText("kobus")
	        */
	        
	       
	        
	        //adding button handler for the auto report
	        auto.setOnClickListener(new OnClickListener() {
				
			 @Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					switchtoautofrag();
					automated = false ;
					manual.setBackgroundColor(getResources().getColor(R.color.greyForBackrounds));
					auto.setBackgroundColor(getResources().getColor(R.color.lightBlueForLabels));
					
				}
			});
	        
	      //adding button handler for the manual report
	        manual.setOnClickListener(new OnClickListener() {
				
	        	@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					switchtomanualfrag();
					manual.setBackgroundColor(getResources().getColor(R.color.lightBlueForLabels));
					auto.setBackgroundColor(getResources().getColor(R.color.greyForBackrounds));
				}
			});
	        
	    }
	 
	 
	 	
	 	//gain access to fragment
	 	public reportAutoFragment gainAccessToAutoFragment()
	 	{
	 	reportAutoFragment fragment = (reportAutoFragment) getSupportFragmentManager().findFragmentById(R.id.autoFragLayout);
	 	
	 	return fragment;
	 	}
	 	//gain access to fragment
	 	public reportManualFragment gainAccessToManualFragment()
	 	{
	 	reportManualFragment fragment = (reportManualFragment) getSupportFragmentManager().findFragmentById(R.id.reportManualLayout);
	 	return fragment;
	 	}
	 	
	 	
	 	public void addautoFragment(Bundle save)
	 	{
	 		Log.e("vince", "" + (R.id.autoFragLayout));
	 		View t =  findViewById(R.id.report);
	 		if (t != null)
	 		{
	 			Log.e("vince", "In here");
	 			if (save != null)
	 			{
	 				return;
	 			}
	 		// Create a new Fragment to be placed in the activity layout	
	 		
	 		
	 		reportAutoFragment frag = new reportAutoFragment();
	 		
	 		FragmentManager fragmentManager = getSupportFragmentManager();
	 		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
	 		
	 		fragmentTransaction.add(R.id.report, frag,"auto");
	 		fragmentTransaction.commit();
	 		}
	 	}
	 	
	 	public void switchtomanualfrag()
	 	{
	 		
	 		Fragment frag = new reportManualFragment();
	 		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
	 		transaction.addToBackStack(null);
	 		// Replace whatever is in the fragment_container view with this fragment,
	 		// and add the transaction to the back stack
	 		transaction.replace(R.id.report, frag);
	 		transaction.addToBackStack(null);

	 		// Commit the transaction
	 		transaction.commit();
	 	}
	 	public void switchtoautofrag()
	 	{
	 		reportAutoFragment frag = new reportAutoFragment();
	 		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
	 		transaction.addToBackStack(null);
	 		// Replace whatever is in the fragment_container view with this fragment,
	 		// and add the transaction to the back stack
	 		transaction.replace(R.id.report, frag);
	 		transaction.addToBackStack(null);

	 		// Commit the transaction
	 		transaction.commit();
	 	}


	    @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        
	        // Inflate the menu; this adds items to the action bar if it is present.
	        getMenuInflater().inflate(R.menu.main, menu);
	        return true;
	    }

	    @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	        // Handle action bar item clicks here. The action bar will
	        // automatically handle clicks on the Home/Up button, so long
	        // as you specify a parent activity in AndroidManifest.xml.
	        int id = item.getItemId();
	        if (id == R.id.action_settings) {
	            return true;
	        }
	        return super.onOptionsItemSelected(item);

	    }
	    
	    @Override
	    protected void onStart() 
	    {
	        super.onStart();
	        Log.d("vince", "onStart");
	    }    
	    
	    @Override
	    protected void onResume() 
	    {
	        super.onResume();
	        Log.d("onResume", "onResume");  
	    }    
	      
	    @Override
	    protected void onPause() 
	    {
	        super.onPause();
	        Log.d("onPause", "onPause");
	    }    

	    @Override
	    protected void onStop() 
	    {
	        super.onStop();
	        Log.d("vince", "onStop");
	    }    
	    
	    @Override
	    protected void onRestart() 
	    {
	        super.onRestart();
	        Log.d("onRestart", "onRestart");
	    }    
	    
	    @Override
	    protected void onDestroy() 
	    {
	    	super.onDestroy();
	    	Log.d("vince", "onDestroy");
	    }
	    
	   @Override
	   protected void onSaveInstanceState(Bundle out) 
	    {
	    	super.onSaveInstanceState(out);
	    	
	    }
	    
	    @Override
	    protected void onRestoreInstanceState(Bundle savedInstanceState) {
	    	// TODO Auto-generated method stub
	    	super.onRestoreInstanceState(savedInstanceState);
	    	
	    }
	
}
