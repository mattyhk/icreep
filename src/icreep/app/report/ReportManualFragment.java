package icreep.app.report;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import icreep.app.R;
import icreep.app.R.id;
import icreep.app.R.layout;

public class ReportManualFragment extends Fragment {

	Button sendreport;

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
			Bundle savedInstanceState) {
		float correctTextpixel = 32 * getResources().getDisplayMetrics().density;
		View v = inflater.inflate(R.layout.fragment_reports_manual, container,
				false);
		sendreport = (Button) v.findViewById(R.id.sendReportButton);
		sendreport.setTextSize(correctTextpixel);
		
		sendreport.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// this will call the method for the manual mailer
				// i will just use the embedded two options for mailing, mail or
				// gmail.
				// hopefully the user will chose a proper option and do this
				// properly >>> to test
				sendMail();
			}
		});

		return v;
	}

	/*
	 * Pre-Conditions: None Post-conditions: > Send the mail using the default
	 * mailer for the user to add recipients if they want
	 */
	public void sendMail() {
		Intent i = new Intent(Intent.ACTION_SENDTO);
		i.setType("message/rfc822");
		i.setData(Uri.parse("mailto:vreid@openboxsoftware.com"));
		i.putExtra(Intent.EXTRA_SUBJECT, "Manual Location report");

		finalBuildEmailReport(); // this will create the latest report for data

		File fie = new File(getActivity().getCacheDir(), "Report.txt");
		String t = fie.getAbsolutePath();

		File outFileDir = Environment.getExternalStorageDirectory();
		String dir = outFileDir.getAbsolutePath();
		File outFile = new File(outFileDir, "Reports.txt");

		i.putExtra(Intent.EXTRA_TEXT, buildEmailBody());
		i.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(outFile));

		i.putExtra(Intent.EXTRA_PHONE_NUMBER, "0834483431");
		try {
			startActivity(Intent.createChooser(i, "Send mail..."));
		} catch (android.content.ActivityNotFoundException ex) {
			Log.e("vince", "couldn't send");
		}
	}

	/*
	 * Pre-Conditions: None Post-conditions: > Build the report using string
	 * handling > Create a fully formatted text file to attach to the email
	 */
	public void finalBuildEmailReport() {
		String s = "This is the report for your daily statistics:";

		s = s + "\n";

		// mock data
		TimePlace tp = new TimePlace("Kitchen", 2, "First");
		TimePlace tp2 = new TimePlace("S3", 3, "Second");
		TimePlace tp3 = new TimePlace("G3", 4, "Ground");
		TimePlace tp4 = new TimePlace("G8", 4, "Ground");
		TimePlace tp7 = new TimePlace("Bathroom", 50.46, "Second");
		TimePlace tp5 = new TimePlace("Kitchen", 4, "Ground");

		ArrayList<TimePlace> list = new ArrayList<TimePlace>();
		list.add(tp);
		list.add(tp2);
		list.add(tp4);
		list.add(tp3);
		list.add(tp5);
		list.add(tp7);

		Sorting sorter = new Sorting() ;
		list = sorter.InsertionSort(list);

		int max = 0;
		for (TimePlace t : list) {
			if (max < t.getLocation().length()) {
				max = t.getLocation().length();
			}
		}

		boolean g = false;
		boolean f = false;
		boolean se = false;
		boolean should = false;
		for (TimePlace t : list) {
			if (t.getFloor().equals("Ground") && (g == false)) {
				s = s + "\n============================================";
				s = s + "\nGround Floor";
				s = s + "\n--------------------------------------------";
				g = true;
			}
			if (t.getFloor().equals("First") && (f == false)) {
				if (g == false) {
					s = s + "\n============================================";
					s = s + "\nGround Floor";
					s = s + "\n--------------------------------------------";
					g = true;
				}
				s = s + "\n============================================";
				s = s + "\nFirst Floor";
				s = s + "\n--------------------------------------------";
				f = true;
			} else if (t.getFloor().equals("Second") && (se == false)) {
				if (f == false) {
					s = s + "\n============================================";
					s = s + "\nGround Floor";
					s = s + "\n--------------------------------------------";
					f = true;
				}
				s = s + "\n============================================";
				s = s + "\nSecond Floor";
				s = s + "\n--------------------------------------------";
				se = true;
			}
			int diff = max - t.getLocation().length();
			String locate = t.getLocation();
			for (int i = 0; i < diff; i++) {
				locate = locate + " ";
			}
			s = s + "\n" + locate;
			s = s + "\t \t \t \t";

			s = s + (t.getTimeSpent() * 1.00) + " hrs";
		}
		s = s + "\n============================================";
		s = s + "\n";

		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm");
		String Dated = sdf.format(d);

		s = s + "\nOpen Box Software - iCreep app";
		s = s + "\nThere when you least expect it ;)";
		s = s + "\n";
		s = s + "\nSent on the " + sdf.format(d) + " at " + sdf2.format(d);
		createReportTextFile(s);

	}

	/*
	 * Pre-Conditions: The fully formatted string that needs to be written
	 * Post-conditions: > Write string to a textFile > Create a fully formatted
	 * text file to attach to the email
	 */
	public void createReportTextFile(String in) {
		File outFileDir = Environment.getExternalStorageDirectory();
		String dir = outFileDir.getAbsolutePath();
		File ttt = Environment.getDataDirectory();
		String fileName = "Reports.txt";
		// .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
		// File outFile = new File(outFileDir, "Reports2.txt");
		File outFile = new File(dir + File.separator + fileName);

		OutputStream os = null;
		try {
			os = new FileOutputStream(outFile);
			os.write(in.getBytes());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (os != null) {

				try {
					os.flush();
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/*
	 * Pre-Conditions: None Post-conditions: > build the emails body that will
	 * be attached to the intent
	 */
	public String buildEmailBody() {
		String userName = "Logan Dunbar"; // can be retrieved from the DB
		String s = "";
		s = s + "Hi there " + userName;
		s = s + "\n";
		s = s + "\nYour daily statistics can be found as an attachment.";
		s = s
				+ "\nFor the best view on your statistics, please use MS Office or Wordpad.";
		s = s + "\n";
		s = s + "\nRegards";
		s = s + "\nOpen Box Software iCreep team";

		return s;
	}

	

	@Override
	public void onStart() {
		super.onStart();
		Log.d("onStart", "onStart");
	}

	@Override
	public void onResume() {
		super.onResume();
		Log.d("onResume", "onResume");
	}

	@Override
	public void onPause() {
		super.onPause();
		Log.d("onPause", "onPause");
	}

	@Override
	public void onStop() {
		super.onStop();
		Log.d("vince", "onStop");
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d("vince", "onDestroy");
	}

	@Override
	public void onSaveInstanceState(Bundle out) {
		super.onSaveInstanceState(out);

	}
}
