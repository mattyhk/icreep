/**
 *  THIS CLASS IS NOT BEING USED
 */

package icreep.app.bluetooth;

import java.util.ArrayList;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class DeviceGeneratorListenerUpdate extends BroadcastReceiver
{
	private DeviceListGenerator dl;
	private BossAlertActivity baa;
	
	public DeviceGeneratorListenerUpdate(DeviceListGenerator dlg,BossAlertActivity baa)
	{
		dl = dlg;
		this.baa = baa;
	}

	public void onReceive(Context context, Intent intent)
	{

		ArrayList<BlueToothDeviceModel> list = dl.afterDoneScanning();
		baa.updateList(list);
		if (list.size() != 0) {
			for (BlueToothDeviceModel bt : list) {
				String name = bt.cur.getName() + " " + bt.getDeviceUniqueID();
				Toast t = Toast.makeText(context, name, Toast.LENGTH_SHORT);
				t.show();
			}
		}

	}

}
