package icreep.app.report;

import android.support.v4.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;
import icreep.app.Message;
import icreep.app.R;
import icreep.app.SharedPreferencesControl;
import icreep.app.db.iCreepDatabaseAdapter;

public class ReportAutoFragment extends Fragment
{

	Switch switched;
	Button save;
	TimePicker tp;
	int hour = -1;
	int min = -1;
	boolean hasAuto = false;
	boolean checkerIfEmailed = false;
	AlarmControlClass acc = new AlarmControlClass();
	iCreepDatabaseAdapter adapt = null;
	int userID = -1;

	/*
	 * please note that the switch will depend on the following possibly, db,
	 * shared preferences or savedInstanceState
	 * 
	 * (non-Javadoc)
	 * 
	 * @see android.app.Fragment#onCreateView(android.view.LayoutInflater,
	 * android.view.ViewGroup, android.os.Bundle)
	 */

	/*
	 * Pre-Conditions: This method occurs when they fragment is added to a
	 * activity
	 * 
	 * Post-conditions: > all the listeners are added > the button sizes and
	 * font sizes are set using virtual pixels as a function of screen density >
	 * the control of enabling and disabling differnet views > the logical flow
	 * of events depending on other views ect. > return the view that was
	 * inflated by the inflater from the xml of this fragment > the changing of
	 * the sizes will automatically adjust the view
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		// Inflate the layout for this fragment
		// Return inflater.inflate(R.layout., container, false);
		adapt = new iCreepDatabaseAdapter(getActivity());
		View v = inflater.inflate(R.layout.fragment_reports_auto, container,
				false);
		acc = new AlarmControlClass();
		SharedPreferencesControl spc = new SharedPreferencesControl(getActivity());
		userID = spc.getUserID();
		
		save = (Button) v.findViewById(R.id.saveButton);
		save.setBackground(getResources().getDrawable(R.drawable.button_style_disabled));
		save.setEnabled(false); // no changes thus button shouldn't be enabled
		// the switched will default to not checked
		switched = (Switch) v.findViewById(R.id.switchBar);
		//switched.setBackground(getResources().getDrawable(R.color.whiteBackground));
		tp = (TimePicker) v.findViewById(R.id.timePicker);
		setTheHourMinuteSwitch(); // switch on switched >> this will
									// automatically update the timepicker
									// aswell
		// need to make the color of switched more visible
		// switched.setBackgroundColor(getResources().getColor(R.color.whiteBackground));

		

		// TextView deli = (TextView) v.findViewById(R.id.Delivery);
		// TextView deliTime = (TextView)v.findViewById(R.id.DeliveryTime);

		// tp.setEnabled(false);
		// float correctTextpixel =
	    	//float correctTextpixel = 16*getResources().getDisplayMetrics().density;
		// 16*getResources().getDisplayMetrics().density;

		// ensuring all text pixels are the correct size
		// save.setTextSize(correctTextpixel);
		// deli.setTextSize(correctTextpixel);
		// deliTime.setTextSize(correctTextpixel);
		// switched.setTextSize(correctTextpixel);

		// all the listeners using unnamed inner classes to avoid id checks
		switched.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked)
			{
				// TODO Auto-generated method stub
				if (isChecked == true) // set the default time of the users DB
				{
					setTheTimePicker();

					// if a auto has never been set, then if you enable, you
					// could save at
					// this exact current time
					if (hasAuto == true) {
						save.setEnabled(false);
						save.setBackground(getResources().getDrawable(R.drawable.button_style_disabled));
					} else
					{
						save.setEnabled(true);
						save.setBackground(getResources().getDrawable(R.drawable.button_style));
					}

				} else {
					// tp.setEnabled(false);
					setTheTimePicker();
					if (hasAuto == true) {
						save.setEnabled(true);
						save.setBackground(getResources().getDrawable(R.drawable.button_style));
					} else
					{
						save.setEnabled(false);
						save.setBackground(getResources().getDrawable(R.drawable.button_style_disabled));
					}
					// will have to remove the automatic time from the database
				}
			}
		});

		// this will get the time for the time picker and store it into the
		// user's profile
		save.setOnClickListener(new OnClickListener()
		{
			// listener for when you click save
			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub

				/*
				 * The whole idea is the save button will work like if checked
				 * is false then they don't want automated but had it before so
				 * save the new time as 0 to know it's not auto if checked is
				 * true then save the new automated time
				 */
				if (switched.isChecked() == true) // first test to see if it did
													// update then disable save
													// >> future
				{
					int storehour = tp.getCurrentHour();
					int storeminute = tp.getCurrentMinute();
					updateTheTableForAutoMail(storehour, storeminute);
					//addAlarm(storehour, storeminute); // thisi is for testing
														// purposes
					save.setEnabled(false);
					save.setBackground(getResources().getDrawable(R.drawable.button_style_disabled));
				} else {
					int storehour = 0;
					int storeminute = 0;
					save.setEnabled(false);
					save.setBackground(getResources().getDrawable(R.drawable.button_style_disabled));
					updateTheTableForAutoMail(storehour, storeminute);
				}
			}
		});

		tp.setIs24HourView(true);
		setTheTimePicker(); // setting it's time

		tp.setOnTimeChangedListener(new OnTimeChangedListener()
		{
			/*
			 * must store data of original time and then decide if i should
			 * enable button or not*also i must read from the DB for the users
			 * stored values*tp.setcurrenthour....*store those read values to
			 * checkit automatically defaults to the current time which is cool
			 */

			@Override
			public void onTimeChanged(TimePicker view, int hourOfDay, int minute)
			{
				// TODO Auto-generated method stub
				if (!((hourOfDay == hour) && (minute == min))) {
					save.setEnabled(true);
					save.setBackground(getResources().getDrawable(R.drawable.button_style));
					
				} else
				{
					save.setEnabled(false);
					save.setBackground(getResources().getDrawable(R.drawable.button_style_disabled));
				}

			}
		});

		return v;
	}

	/*
	 * Pre-Conditions: a hour and minute for the auto alarm Post-conditions: >
	 * This will add the alarm for the auto mailer > Will be used for adding the
	 * alarm when the app starts up...
	 */
	public void addAlarm(int hour, int min)
	{
		if (checkerIfEmailed == false) {
			acc.setAlarm(hour, min, getActivity());
			acc.sendAutoEmailRepeat();
			checkerIfEmailed = true;
		} else {
			//acc.turnOffAlarm();
		}
	}

	/*
	 * Pre-Conditions: none
	 * 
	 * Post-conditions: Need to check the database for auto report information
	 * the time that is in the database is what our picker will default to this
	 * will also enable the switcher if time has been set aka hour != 0 &&
	 * minute != 0
	 */
	private void setTheHourMinuteSwitch()
	{
		// if there is a auto time
		// isAuto = true ;

		String valueFromAdapt = adapt.getReportTime(userID); // this will equal to
														// something lie
												// adapt.dasdasda
		if (valueFromAdapt == null)
		{
			return ;
		}
		String[] reportArray = valueFromAdapt.split("\\+");
		boolean doIT = false ;
		String workWith ="";
		if (Integer.parseInt(reportArray[1]) == 1)
		{
			doIT = true;
			workWith= reportArray[0];
		}
		
		if (doIT == true) // means auto is on
		{
			hasAuto = true;
			String[] times = workWith.split(":");
			hour = Integer.parseInt(times[0]);
			min = Integer.parseInt(times[1]);
			switched.setChecked(true);
		}
		// reason for not have a else is because of default values set as global
		// variables
		// setTheTimePicker(); not needed
	}

	/*
	 * Pre-Conditions: The new hour and minute for the DB
	 * 
	 * Post-conditions: // this will be invoked when the save button is clicked
	 * and will // store the latest hour and minute that the user wants there
	 * auto report at
	 */
	private void updateTheTableForAutoMail(int stoh, int stom)
	{
		boolean newAuto = switched.isChecked();
		
		String newTime = "" + stoh + ":" ;
		String needed = "" + stom ;
		if (needed.length() == 1 )
		{
			newTime = newTime + "0"+stom ;
		}else
		{
			newTime = newTime + stom ;
		}
		// call the db update
		String reportTime = adapt.getReportTime(userID);
		boolean updated = false ;
		boolean wasntUpdated = false;
		if (reportTime != null) {
			
			if (adapt.updateDeliveryTime(newTime, userID, newAuto) == true) {
				hasAuto = newAuto;
				if (newAuto == true) {
					hour = stoh;
					min = stom;
					updated = true ;
					icreep.app.Message.message(getActivity(), "Update was successful");
				} else {
					hour = -1;
					min = -1;
					icreep.app.Message.message(getActivity(), "Update was successful");
				}
			}
		} else {
			if ( adapt.addDeliveryTime(newTime, userID) == true)
			{
				icreep.app.Message.message(getActivity(),"Save Successful");
				hasAuto = true ;
				hour = stoh; 
				min = stom;
				updated = true ;
				
			}else
			{
				icreep.app.Message.message(getActivity(),"Save Unsuccessful");
				wasntUpdated = true ;
			}
		}
		if (updated == true)
		{
			String time = adapt.getReportTime(userID);
			String[] splitted = time.split("\\+");
			time = splitted[0];
			if (time != null) {
				String[] times = time.split(":");
				int hour = Integer.parseInt(times[0]);
				int min = Integer.parseInt(times[1]);
				AlarmControlClass acc = new AlarmControlClass();
				acc.setAlarm(hour, min, getActivity());
				acc.sendAutoEmailRepeat();
				Message.message(getActivity(), "Alarm is set");
			}
		}else
		{
			if (wasntUpdated == false)
			{
			acc.setAlarm(0, 0, getActivity());
			acc.turnOffAlarmAuto();
			Message.message(getActivity(), "Alarm switched off");
			}
		}
	}

	/*
	 * Pre-Conditions: The hour and minute to set the time picker
	 * 
	 * Post-conditions: The timer picker hour and minute is changed if the
	 * switched is on which indicates the spinner is on. This allows it to
	 * default to the users values
	 */
	private void setTheTimePicker()
	{
		if (switched.isChecked() == true) {
			tp.setEnabled(true);
			if (hasAuto == true) {
				tp.setCurrentHour(hour);
				tp.setCurrentMinute(min);
			}
		} else
			tp.setEnabled(false);
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
