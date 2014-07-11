package icreep.app;

import android.content.Context;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;

public class AudioManagingClass
{
	AudioManager am;
	Context c ;
	int originalMode;
	
	public AudioManagingClass(Context c)
	{
		// TODO Auto-generated constructor stub
		this.c = c ;
		am=(AudioManager)c.getSystemService(Context.AUDIO_SERVICE);
		originalMode = am.getRingerMode() ;
	}
	
	public void changeToSilent()
	{
		am.setRingerMode(0);
	}
	
	public void changeToVibrate()
	{
		am.setRingerMode(1);
	}
	
	public void changeToLoud()
	{
		am.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
	}
	
	public void changeBackToUserDefault()
	{
		am.setRingerMode(originalMode);
	}
	
	
	//something cool
	public void fireRingTone()
	{
		Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
		Ringtone r = RingtoneManager.getRingtone(c.getApplicationContext(), notification);
		r.play();
	}
}
