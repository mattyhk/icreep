package icreep.app.report;

import java.io.File;

import android.support.v4.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import icreep.app.R;

public class ReportManualFragment extends Fragment
{

	Button sendreport;
	File outFile = null;

	/*
	 * Pre-Conditions: A bundle in case we need a saved state if we switch
	 * screens and want to keep old information The trigger is that you have
	 * switched to this activity using an intent and now this activity is
	 * created Post-conditions: > This will set all the listeners for the
	 * different views > This will ensure the correct sizing of views and their
	 * text as a function of the screen density > This ensures the logical flow
	 * of events and will cover the different combination of inputs
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState){
		//float correctTextpixel = 32 * getResources().getDisplayMetrics().density;
		// float correctTextpixel = 32 *
		// getResources().getDisplayMetrics().density;
		View v = inflater.inflate(R.layout.fragment_reports_manual, container,
				false);
		sendreport = (Button) v.findViewById(R.id.sendReportButton);
		//sendreport.setTextSize(correctTextpixel);

		sendreport.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				// TODO Auto-generated method stub
				// this will call the method for the manual mailer
				// i will just use the embedded two options for mailing, mail or
				// gmail.
				// hopefully the user will chose a proper option and do this
				// properly >>> to test
				MailerClass mail = new MailerClass(getActivity());
				mail.sendMail();
				// might want to cancel alarm, busy testing alarm
			}
		});

		return v;
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
