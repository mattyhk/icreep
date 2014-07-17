/**
 *  THIS CLASS IS NOT BEING USED
 */

package icreep.app.bluetooth;

import android.bluetooth.BluetoothDevice;

public class BlueToothDeviceModel
{
	public BluetoothDevice cur ;
	
	
		public BlueToothDeviceModel(BluetoothDevice cur)
		{
			this.cur = cur ;
		}
		
		public String getDeviceUniqueID()
		{
			return cur.getAddress();
			
		}
		
		public String getDeviceName()
		{
			return cur.getName();
		}
}
