package icreep.app.beacon;

import java.util.Collection;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

import com.radiusnetworks.ibeacon.IBeacon;
import com.radiusnetworks.ibeacon.IBeaconConsumer;
import com.radiusnetworks.ibeacon.IBeaconManager;
import com.radiusnetworks.ibeacon.RangeNotifier;
import com.radiusnetworks.ibeacon.Region;

public class BeaconService extends Service implements IBeaconConsumer,
		RangeNotifier {
	
	private Looper mServiceLooper;
	private ServiceHandler mServiceHandler;
	
	// Scan only for beacons with a major equal to 3
	private static final int MAJOR = 3;
	private static final Region BEACON_REGION = new Region("regionId", null, MAJOR, null);
	
	private IBeaconManager beaconManager;
	
	/*
	 *  Handles messages from the thread started by the service
	 *  The thread is separate from the main UI thread
	 */
	private final class ServiceHandler extends Handler {
		public ServiceHandler(Looper looper) {
			super(looper);
		}
		
		@Override
		public void handleMessage(Message msg) {
			Log.d("TEST", "On handle message");
			 
			// Initialise the service functions
			init();
		}
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d("TEST", "On start command");
		
		Toast.makeText(this, "service starting", Toast.LENGTH_SHORT).show();
		
		// Send message to thread handler to initiate new thread for service to run on
		Message msg = mServiceHandler.obtainMessage();
		msg.arg1 = startId;
		mServiceHandler.sendMessage(msg);
		
		return START_REDELIVER_INTENT;
	}
	
	@Override
	public void onCreate() {
		Log.d("TEST", "On create");
		
		// Start the background thread running the service
		HandlerThread thread = new HandlerThread("BeaconService", Process.THREAD_PRIORITY_BACKGROUND);
		thread.start();
		
		mServiceLooper = thread.getLooper();
		mServiceHandler = new ServiceHandler(mServiceLooper);
	}
	
	@Override
	public void onDestroy() {
		stopBeaconRanging();
		Toast.makeText(this, "service done", Toast.LENGTH_SHORT).show();
	}
	
	/**
	 *  Function is called when the beacon manager completes a cycle.
	 *  The iBeacons found in the cycle are passed in.
	 *  The found iBeacons should be processed and the current location determined
	 *  If the location has changed, the DB is updated
	 */
	@Override
	public void didRangeBeaconsInRegion(Collection<IBeacon> iBeacons,
			Region region) {
		
		// New thread
		// Probably not necessary
		
		Handler handler = new Handler(Looper.getMainLooper());
		
		handler.post(new Runnable() {
			
			@Override
			public void run() {
				Log.d("TEST", "Cycle of ranging");
			}
		});

	}
	
	/**
	 * Starts searching for beacons in the region defined by BEACON_REGION
	 */
	@Override
	public void onIBeaconServiceConnect() {
		try {
			this.beaconManager.startRangingBeaconsInRegion(BEACON_REGION);
		}
		
		catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	
	/**
	 * Called to intialise the service. Creates the beacon manager and starts the iBeacon service.
	 */
	private void init() {
		
		// Intialise beacon manager
		this.beaconManager = IBeaconManager.getInstanceForApplication(this);
		
		// Set the service to be notified by ranged beacons
		this.beaconManager.setRangeNotifier(this);

		startBeaconRanging();
	}
	
	/**
	 * Binds the beacon manager to the current thread. Effectively starts searching for iBeacons
	 */
	private void startBeaconRanging()
	{
		this.beaconManager.bind(this);
		Log.d("TEST", "Started Ranging");
	}

	/**
	 * Unbinds the beacon manager. Stops searching for iBeacons.
	 */
	private void stopBeaconRanging()
	{
		this.beaconManager.unBind(this);
		Log.d("TEST", "Stopped ranging");
	}

	/**
	 * The service is not bound to any application component. Therefore, return null.
	 */
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

}
