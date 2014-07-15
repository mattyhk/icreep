package icreep.app.bluetooth;

import java.util.ArrayList;


import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class TestingReceiver extends BroadcastReceiver
{
	private DeviceListGenerator dl;

	public TestingReceiver(DeviceListGenerator dlg)
	{
		dl = dlg;
	}

	public void onReceive(Context context, Intent intent)
	{

		ArrayList<BlueToothDeviceModel> list = dl.afterDoneScanning();

		if (list.size() != 0) {
			for (BlueToothDeviceModel bt : list) {
				String name = bt.cur.getName() + " " + bt.getDeviceUniqueID();
				Toast t = Toast.makeText(context, name, Toast.LENGTH_SHORT);
				t.show();
			}
		}

	}

}
