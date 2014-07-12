package it.open.androidlab.util;


import it.open.androidlab.constants.BaseFlags;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.Settings;
import android.provider.Settings.Secure;
import android.text.Html;
import android.util.Log;
import android.util.TypedValue;

/**
 * 
 * @author c.luchessa
 *
 */
public final class Utils {

	public static final int NETWORK_WIFI = 1;
	public static final int NETWORK_3G = 2;


	public static void debug(String tag, String message) {
		if (BaseFlags.ENABLE_LOG && tag != null && message != null) {
			Log.d(tag, message);
		}
	}

	public static String getStringFromFile(File file) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

		StringBuffer stringBuffer = new StringBuffer();
		String line = null;

		while((line =bufferedReader.readLine())!=null){
			stringBuffer.append(line).append("\n");
		}

		bufferedReader.close();

		return stringBuffer.toString();
	}

	@SuppressWarnings("resource")
	public static void copyFile(File src, File dst) throws IOException {
		FileChannel inChannel = new FileInputStream(src).getChannel();
		FileChannel outChannel = new FileOutputStream(dst).getChannel();

		try {
			inChannel.transferTo(0, inChannel.size(), outChannel);

		} finally {
			if (inChannel != null) inChannel.close();
			if (outChannel != null) outChannel.close();
		}
	}

	public static boolean deleteDirectory(File path) {
		if( path.exists() ) {
			File[] files = path.listFiles();
			if (files == null) {
				return true;
			}
			for(int i=0; i<files.length; i++) {
				if(files[i].isDirectory()) {
					deleteDirectory(files[i]);
				}
				else {
					files[i].delete();
				}
			}
		}
		return( path.delete() );
	}

	public static void printStackTrace(Throwable t) {
		t.printStackTrace();
	}

	public static boolean isNotNullOrEmpty(String string) {
		return string != null && ! "".equals(string.trim());
	}

	public static boolean isNullOrEmpty(String s) {
		return !isNotNullOrEmpty(s);
	}

	public static float dip2pixel(Context context, float dipValue) {
		Resources resources = context.getResources();
		float pixelValue = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, resources.getDisplayMetrics());
		return pixelValue;
	}

	public static String convertEntityCharacters(String text) {
		if (text != null && !text.equals("")) {
			return (Html.fromHtml(text).toString());
		} else {
			return text;
		}
	}

	public static final boolean isConnected(Context context) {
		ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = manager.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.getState() == NetworkInfo.State.CONNECTED) {
			return true;
		} else {
			return false;
		}
	}

	public static String byteArrayToHexString(byte[] data) {
		StringBuilder buffer = new StringBuilder(data.length * 2);

		for (int i = 0; i < data.length; i++) {
			int v = data[i] & 0xff;
			if (v < 16) {
				buffer.append('0');
			}
			buffer.append(Integer.toHexString(v));
		}
		return buffer.toString().toUpperCase(Locale.ITALIAN);
	}


	@SuppressLint("SimpleDateFormat")
	public static String convertTime(long time) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(time);
		return convertTime(cal.getTime());
	}

	@SuppressLint("SimpleDateFormat")
	public static String convertTime(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		return dateFormat.format(date);
	}


	public static final byte[] md5(byte[] data) {
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");
			digest.update(data);
			return digest.digest();
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
	}

	public static byte[] hexStringToByteArray(String hexString) {
		byte[] data = new byte[hexString.length() / 2];

		for (int i = 0; i < data.length; i++) {
			data[i] = (byte) (0xFF & Integer.parseInt(hexString.substring(i * 2, i * 2 + 2), 16));
		}

		return data;
	}

	public static String getDeviceID(Context context) {
		return Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
	}

	public static int getNetwork(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo currentNetwork = connectivityManager.getActiveNetworkInfo();
		if (currentNetwork != null) {
			switch (currentNetwork.getType()) {
			case ConnectivityManager.TYPE_MOBILE:
			case ConnectivityManager.TYPE_MOBILE_DUN:
			case ConnectivityManager.TYPE_MOBILE_HIPRI:
			case ConnectivityManager.TYPE_MOBILE_MMS:
			case ConnectivityManager.TYPE_MOBILE_SUPL:
				return NETWORK_3G;
			case ConnectivityManager.TYPE_WIFI:
				return NETWORK_WIFI;
			}
		}

		return 0;
	}

	public static String getAppVersion(Context context) {
		try {
			return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
		} catch (NameNotFoundException e) {
			return "Version Unknown";
		}
	}

	public static String getUriFormattedString( String uriString ) {
		return Uri.encode(uriString);
	}

	public static String getCommaSeparatedNumber(BigDecimal number) {
		try {
			NumberFormat n = NumberFormat.getCurrencyInstance(Locale.US); 
			double doubleValue = number.doubleValue();
			return n.format(doubleValue).substring(1);
		} catch(Exception e) {
			return "";
		}
	}

	public static String getRoundedString(String num) {
		try {
			return "" + Long.parseLong(num);
		} catch(Exception e) {
			return num;
		}
	}
}