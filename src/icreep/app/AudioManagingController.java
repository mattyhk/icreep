package icreep.app;

import android.content.Context;

import android.media.AudioManager;
import android.media.MediaPlayer;

public class AudioManagingController
{
	AudioManager am;
	Context c ;
	int originalMode;
	
	public AudioManagingController(Context c)
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
		if (am.getRingerMode() !=0)
		{
		int id = c.getResources().getIdentifier("bossalert", "raw", c.getPackageName());
		MediaPlayer mp = MediaPlayer.create(c,id);
		mp.setLooping(false);
		mp.start();
		}	
	}
	
	
	
}