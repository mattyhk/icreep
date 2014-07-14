package icreep.app.report;

import icreep.app.ICreepApplication;
import icreep.app.SharedPreferencesControl;
import icreep.app.db.iCreepDatabaseAdapter;
import icreep.app.location.UserLocation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

public class MailerClass
{

	/*
	 * Pre-Conditions: None Post-conditions: > Send the mail using the default
	 * mailer for the user to add recipients if they want
	 */
	private Context c = null;
	private iCreepDatabaseAdapter adapt ;
	private int userID = -1;
	private UserLocation user;
	private ICreepApplication mApplication;
	
	public MailerClass(Context c)
	{
		this.c = c ;
		adapt = new iCreepDatabaseAdapter(c) ;
		SharedPreferencesControl spc = new SharedPreferencesControl(c);
		userID = spc.getUserID();
		user = new UserLocation(c);
		mApplication = (ICreepApplication) c.getApplicationContext();
	}
	public void sendMail()
	{		
		iCreepDatabaseAdapter adapt = new iCreepDatabaseAdapter(c);
		ArrayList<String> userDet = adapt.userDetails(userID);
		if (userDet==null)
		{
			icreep.app.Message.message(c, "There are no user details, thus we can't send email");
			return ;			
		}
		
		
		String defaultEmailaddress = userDet.get(3); // this will come from SQL or SP
		
		Intent i = new Intent(Intent.ACTION_SENDTO);
		i.setType("message/rfc822");
		i.setData(Uri.parse("mailto:"+ defaultEmailaddress));
		i.putExtra(Intent.EXTRA_SUBJECT, "Manual Location report");

		finalBuildEmailReport(); // this will create the latest report for data

		// File fie = new File(c.getCacheDir(), "Reports2.txt");
		// String t = fie.getAbsolutePath();

		File outFileDir = Environment.getExternalStorageDirectory();
		File outFile = new File(outFileDir, "LatestReport.txt");

		i.putExtra(Intent.EXTRA_TEXT, buildEmailBody());
		i.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(outFile));
		// The phone number is never used, not sure if I should keep it in
		// don't want to break anything ;)
		i.putExtra(Intent.EXTRA_PHONE_NUMBER, "0834483431");
		try {
			c.startActivity(Intent.createChooser(i, "Send mail..."));
			// outFile.deleteOnExit();
			// outFile.delete();
		} catch (android.content.ActivityNotFoundException ex) {
			Log.e("vince", "couldn't send");
		}
	}

	/*
	 * Pre-Conditions: None Post-conditions: > Build the report using string
	 * handling > Create a fully formatted text file to attach to the email
	 */
	@SuppressLint("SimpleDateFormat")
	private void finalBuildEmailReport()
	{
		String s = "This is the report for your daily statistics:";

		s = s + "\n";

		ArrayList<TimePlace> list = new ArrayList<TimePlace>();
		
		user.updateLocationOnDestroy(mApplication.getCurrentLocation(), mApplication.getLastEntryID());

		// This will be the array list that vinny's code generates
		list = adapt.getTimePlaces(userID);
		if (list == null)
		{
			Log.e("vince", "no activity");
			s = s + "\nNo Activity";
			list = new ArrayList<TimePlace>();
		}
		list = Sorting.join(list);
		int max = 0;
		for (TimePlace t : list) {
			if (max < t.getLocation().length()) {
				max = t.getLocation().length();
			}
		}
		boolean o = false ;
		boolean g = false;
		boolean f = false;
		boolean se = false;
		if (list.size() == 0)
		{
			s = s + "\n==================================================";
			s = s + "\nOutside";
			s = s + "\n--------------------------------------------------";
			s = s + "\n==================================================";
			s = s + "\nGround Floor";
			s = s + "\n--------------------------------------------------";
			s = s + "\n==================================================";
			s = s + "\nFirst Floor";
			s = s + "\n--------------------------------------------------";
			s = s + "\n==================================================";
			s = s + "\nSecond Floor";
			s = s + "\n--------------------------------------------------";
		}
		for (TimePlace t : list) {
			if (t.getFloor().equals("Outisde") && (o == false)) {
				s = s + "\n==================================================";
				s = s + "\nOutside";
				s = s + "\n--------------------------------------------------";
				o = true;
			}
			if (t.getFloor().equals("Ground Floor") && (g == false)) {
				if (o == false) {
					s = s + "\n==================================================";
					s = s + "\nOutside";
					s = s + "\n--------------------------------------------------";
					o = true;
				}
				s = s + "\n==================================================";
				s = s + "\nGround Floor";
				s = s + "\n--------------------------------------------------";
				g = true;
			}
			if (t.getFloor().equals("First Floor") && (f == false)) {
				if (o == false) {
					s = s + "\n==================================================";
					s = s + "\nOutside";
					s = s + "\n--------------------------------------------------";
					o = true;
				}
				if (g == false) {
					s = s + "\n==================================================";
					s = s + "\nGround Floor";
					s = s + "\n--------------------------------------------------";
					g = true;
				}
				s = s + "\n==================================================";
				s = s + "\nFirst Floor";
				s = s + "\n--------------------------------------------------";
				f = true;
			} else if (t.getFloor().equals("Second Floor") && (se == false)) {
				if (o == false) {
					s = s + "\n==================================================";
					s = s + "\nOutside";
					s = s + "\n--------------------------------------------------";
					o = true;
				}
				if (g == false) {
					s = s + "\n==================================================";
					s = s + "\nGround Floor";
					s = s + "\n--------------------------------------------------";
					g = true;
				}
				if (f == false) {
					s = s + "\n==================================================";
					s = s + "\nFirst Floor";
					s = s + "\n--------------------------------------------------";
					f = true;
				}
				s = s + "\n==================================================";
				s = s + "\nSecond Floor";
				s = s + "\n--------------------------------------------------";
				se = true;
			}
			int diff = max - t.getLocation().length();
			String locate = t.getLocation();
			for (int i = 0; i < diff; i++) {
				locate = locate + " ";
			}
			s = s + "\n" + locate;
			s = s + "\t \t \t \t";
			
			double time = t.getTimeSpent()*3600;
			int secondsIn = (int) (time % 60);
			int minutesIn = (int) ((time / 60) % 60);
			int hoursIn = (int) ((time / 3600) % 60);
			s = s + (hoursIn) + "hrs " + minutesIn + "min " + secondsIn + "secs";
		}
		s = s + "\n==================================================";
		s = s + "\n";

		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm");

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
	private void createReportTextFile(String in)
	{
		File outFileDir = Environment.getExternalStorageDirectory();
		// String dir = outFileDir.getAbsolutePath();
		// File ttt = Environment.getDataDirectory();
		// .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
		// File outFile = new File(outFileDir, "Reports2.txt");
		File outFile = new File(outFileDir, "LatestReport.txt");

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
	private String buildEmailBody()
	{		
		String needed = adapt.getUserDetails(userID);
		String[] user = null ;
		if (needed == null)
		{
			needed = "no user available";
		} else
		{
			user = needed.split(":");	
			needed = user[0];
		}
		
		String userName = needed; // can be retrieved from the DB
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

	/*
	 * Pre-Conditions: This is called by the broad coast receiver
	 * Post-conditions: > builds the email for auto email > sends the auto email
	 */
	public boolean sendAutoMail() throws Exception
	{	
		iCreepDatabaseAdapter adapt = new iCreepDatabaseAdapter(c);
		ArrayList<String> userDet = adapt.userDetails(userID);
		
		if (userDet==null)
		{
			icreep.app.Message.message(c, "There are no user details, thus we can't send email");
			return false ;			
		}
		
		String to = userDet.get(3); // this will come from SQL or SP
		String from = "iCreep";
		String subject = "Automatic Location report";
		String message = buildEmailBody();
		//message = message + "\nThis was done by auto mailer"; //the subject hints at this

		GMailAutoMailer mail = new GMailAutoMailer("icreepatopenbox@gmail.com",
				"OpenBoxSoftCreep");
		if (subject != null && subject.length() > 0) {
			mail.setSubject(subject);
		} else {
			mail.setSubject("Subject");
		}

		mail.setFrom(from);
		if (message != null && message.length() > 0) {
			mail.setBody(message);
		} else {
			mail.setBody("Message");
		}

		// String[] attachements = null ;
		mail.setTo(new String[] { to });
		/*
		 * if (attachements != null) { for (String attachement : attachements) {
		 * mail.addAttachment(attachement); } }
		 */
		// error over here >>> fixed
		finalBuildEmailReport();
		mail.addAttachment("LatestReport.txt", c);

		return mail.send();
	}

}
