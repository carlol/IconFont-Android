package it.open.androidlab.db;

import it.open.androidlab.BaseApplication;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;


/**
 * 
 * @author c.luchessa
 *
 */
public class ExportDatabaseFileTask extends AsyncTask<String, Void, Boolean> {

	// can use UI thread here
	protected void onPreExecute() { }

	// automatically done on worker thread (separate from UI thread)
	protected Boolean doInBackground(String...databaseNames) {
		boolean isSuccess = true;
		for ( int i = 0; i < databaseNames.length; i++ ) {

			File dbFile = new File(Environment.getDataDirectory() 
					+ "/data/"+BaseApplication.context().getPackageName()+"/databases/"+databaseNames[i]);

			File exportDir = new File(Environment.getExternalStorageDirectory(), "Dump");
			if (!exportDir.exists()) {
				exportDir.mkdirs();
			}
			File file = new File(exportDir, dbFile.getName());

			try {
				file.createNewFile();
				this.copyFile(dbFile, file);
			} catch (IOException e) {
				Log.e("mypck", e.getMessage(), e);
				isSuccess = false;
			}
			if ( ! isSuccess ) break; // skip
		}		
		return isSuccess;
	}

	// can use UI thread here
	protected void onPostExecute(final Boolean success) {
		if (success) {
			Toast.makeText(BaseApplication.context(), "Export successful!", Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(BaseApplication.context(), "Export failed", Toast.LENGTH_SHORT).show();
		}
	}

	@SuppressWarnings("resource")
	void copyFile(File src, File dst) throws IOException {
		FileChannel inChannel = new FileInputStream(src).getChannel();
		FileChannel outChannel = new FileOutputStream(dst).getChannel();
		try {
			inChannel.transferTo(0, inChannel.size(), outChannel);
		} finally {
			if (inChannel != null)
				inChannel.close();
			if (outChannel != null)
				outChannel.close();
		}
	}
}