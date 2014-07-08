package icreep.app;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

public class BitmapController
{
	private String defaultBitmapFilename = "profilePic.png";
	
	public void storeImage(Bitmap image) {
	    File outFileDir = Environment.getExternalStorageDirectory();
	    File pictureFile  = new File(outFileDir, defaultBitmapFilename);
	    try {
	        FileOutputStream fos = new FileOutputStream(pictureFile);
	        image.compress(Bitmap.CompressFormat.PNG, 90, fos);
	        fos.flush();
	        fos.close();
	    } catch (FileNotFoundException e) {
	        Log.d("vince", "File not found: " + e.getMessage());
	    } catch (IOException e) {
	        Log.d("vince", "Error accessing file: " + e.getMessage());
	    }  
	}	
	
	public Bitmap getbitmapImage()
	{
		Bitmap thumbnail = null;
		if (thumbnail == null) {
			try {
				File outFileDir = Environment.getExternalStorageDirectory();
				File filePath = new File(outFileDir, defaultBitmapFilename);
				FileInputStream fi = new FileInputStream(filePath);
				thumbnail = BitmapFactory.decodeStream(fi);
			} catch (Exception ex) {
				Log.e("getThumbnail() on internal storage", ex.getMessage());
			}
		}

		return thumbnail;
	}
	
}
