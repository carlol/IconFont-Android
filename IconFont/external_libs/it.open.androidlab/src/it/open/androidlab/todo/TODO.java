package it.open.androidlab.todo;


import it.open.androidlab.constants.BaseFlags;
import it.open.androidlab.util.Utils;
import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * TODO: Inserire altri metodi
 * 
 * @author c.luchessa
 *
 */
public class TODO {
	
	/*
	 * Listener simple toast markers
	 */
	
	public static void mkToastInfo( Context context, String info, String tag) {
		String content = manageTag(tag) + "info -> " + info;
		Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
		Utils.debug(tag, content);
	}
	
	public static void mkToastFailure( Context context, Throwable t, String tag) {
		Toast.makeText(context, manageTag(tag) + "Failure: " + t.getMessage(), Toast.LENGTH_LONG).show();
	}

	public static void mkToastFailure( Context context, int statusCode, String tag ) {
		Toast.makeText(context, manageTag(tag) + "Failure: statusCode -> " + statusCode, Toast.LENGTH_LONG).show();
	}
	
	public static void mkToastFailure( Context context, String message, String tag) {
		Toast.makeText(context, manageTag(tag) + "Failure: server returns -> " + message, Toast.LENGTH_LONG).show();
	}

	
	/*
	 * UI placeholder view 
	 */
	
	public static void addPlaceholderView( Context context, ViewGroup container, String text ) {
		addPlaceholderView(context, container, text, true);
	}
	
	public static void addPlaceholderView( Context context, ViewGroup container, String text, boolean force ) {
		
		if ( ! (BaseFlags.ENABLE_PLACEHOLDER_VIEWS || force) ) return; // EXIT
		
		TextView tv = new TextView(context);
		tv.setText(text);
		tv.setGravity(Gravity.CENTER);
		
		container.addView(tv, 0/*prepend*/);
	}
	
	
	/*
	 * Facility method
	 */
	private static String manageTag( String tag ) {
		if ( tag == null ) {
			tag = "";
		} else if ( ! tag.isEmpty() ) {
			tag += " ";
		}
		return tag;
	}
}
