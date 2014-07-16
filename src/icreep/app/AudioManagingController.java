package icreep.app;

import android.content.Context;

import android.media.AudioManager;
import android.media.MediaPlayer;

public class AudioManagingController
{
	private AudioManager am;
	private int originalMode;
	private int id;
	private MediaPlayer mp;
	
	public AudioManagingController(Context c)
	{
		this.am=(AudioManager)c.getSystemService(Context.AUDIO_SERVICE);
		this.originalMode = am.getRingerMode();
		this.id = c.getResources().getIdentifier("bossalert", "raw", c.getPackageName());
		this.mp = MediaPlayer.create(c,id);
	}
	
	public void changeToSilent()
	{
		originalMode = am.getRingerMode();
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
		if (am.getRingerMode() !=0)	{
			if (!mp.isPlaying()){
				mp.setLooping(false);
				mp.start();
			}
		}	
	}
	
	
	
}
