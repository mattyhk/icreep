package com.example.icreep;



import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class reportActivity extends Activity {

	Button auto, manual ;
	boolean automated = true ;
	float correctTextpixel = 16*getResources().getDisplayMetrics().density;
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.reports); 
	        auto = (Button) findViewById(R.id.autoButton);
	        manual = (Button) findViewById(R.id.manualButton) ;	        
	        TextView reports = (TextView) findViewById(R.id.textViewMain);
	        TextView userDescrip = (TextView) findViewById(R.id.userDescript);
	        	        
	        reports.setTextSize(correctTextpixel);
	        userDescrip.setTextSize(correctTextpixel);
	        auto.setTextSize(correctTextpixel);
	        manual.setTextSize(correctTextpixel);
	        addautoFragment();
	        
	        /*here I will add the code required to get the users info with using his id
	        * possibly from sp, sis
	        * userdescrip.setText("kobus")
	        */
	        
	        //adding button handler for the auto report
	        auto.setOnClickListener(new OnClickListener() {
				
				@SuppressLint("ResourceAsColor") @Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					switchtoautofrag();
					automated = false ;
					manual.setBackgroundColor(R.color.greyForBackrounds);
					auto.setBackgroundColor(R.color.lightBlueForLabels);
					
				}
			});
	        
	      //adding button handler for the manual report
	        manual.setOnClickListener(new OnClickListener() {
				
				@SuppressLint("ResourceAsColor") @Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					switchtomanualfrag();
					auto.setBackgroundColor(R.color.greyForBackrounds);
					manual.setBackgroundColor(R.color.lightBlueForLabels);
				}
			});
	    }
	 
	 
	 	//gain access to fragment
	 	public reportAutoFragment gainAccessToAutoFragment()
	 	{
	 	reportAutoFragment fragment = (reportAutoFragment) getFragmentManager().findFragmentById(R.id.autoFragLayout);
	 	
	 	return fragment;
	 	}
	 	//gain access to fragment
	 	public reportManualFragment gainAccessToManualFragment()
	 	{
	 	reportManualFragment fragment = (reportManualFragment) getFragmentManager().findFragmentById(R.id.reportManualLayout);
	 	return fragment;
	 	}
	 	
	 	
	 	public void addautoFragment()
	 	{
	 		android.app.FragmentManager fragmentManager = getFragmentManager();
	 		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
	 		reportAutoFragment frag = new reportAutoFragment();
	 		fragmentTransaction.add(R.id.autoFragLayout, frag);
	 	}
	 	
	 	public void switchtomanualfrag()
	 	{
	 		reportManualFragment frag = new reportManualFragment();
	 		FragmentTransaction transaction = getFragmentManager().beginTransaction();

	 		// Replace whatever is in the fragment_container view with this fragment,
	 		// and add the transaction to the back stack
	 		transaction.replace(R.id.reportManualLayout, frag);
	 		transaction.addToBackStack(null);

	 		// Commit the transaction
	 		transaction.commit();
	 	}
	 	public void switchtoautofrag()
	 	{
	 		reportAutoFragment frag = new reportAutoFragment();
	 		FragmentTransaction transaction = getFragmentManager().beginTransaction();

	 		// Replace whatever is in the fragment_container view with this fragment,
	 		// and add the transaction to the back stack
	 		transaction.replace(R.id.reportManualLayout, frag);
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
	        Log.d("onStart", "onStart");
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
