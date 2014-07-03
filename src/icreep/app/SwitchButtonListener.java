package icreep.app;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * Listener that will handle switching between activities
 * @author mkerr
 *
 */

public class SwitchButtonListener implements OnClickListener {
	
	Context c;
	String destination;
	
	/**
	 * Listener object that will be instantiated for every button that switches between activities
	 * Menu buttons
	 * Home buttons
	 * @param c - the current activity
	 * @param s - the new activity
	 */
	
	public SwitchButtonListener(Context c, String s){
		this.c = c;
		this.destination = s;
	}
	
	@Override
	public void onClick(View v) {
		Intent i = new Intent();
		i.setClassName(c, destination);
		c.startActivity(i);
	}

}
