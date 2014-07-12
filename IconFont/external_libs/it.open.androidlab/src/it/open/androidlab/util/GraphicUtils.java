package it.open.androidlab.util;


import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.Display;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;

/**
 * 
 * @author c.luchessa
 *
 */
public final class GraphicUtils {

	public static final int SDK = android.os.Build.VERSION.SDK_INT; 


	private GraphicUtils() { /* singleton */ }


	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	public static void disableOverScrollMode( View v ) {
		if ( SDK >= Build.VERSION_CODES.GINGERBREAD ) {
			v.setOverScrollMode( View.OVER_SCROLL_NEVER );
		}
	}


	@SuppressLint("NewApi")
	@SuppressWarnings("deprecation")
	public static void setBackground( View v, Drawable drawable ) {
		if(SDK < android.os.Build.VERSION_CODES.JELLY_BEAN) {
			v.setBackgroundDrawable(drawable);
		} else {
			v.setBackground(drawable);
		}
	}


	@SuppressLint("NewApi")
	@SuppressWarnings("deprecation")
	public static void removeOnGlobalLayoutListener(View v, ViewTreeObserver.OnGlobalLayoutListener listener){
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
			v.getViewTreeObserver().removeGlobalOnLayoutListener(listener);
		} else {
			v.getViewTreeObserver().removeOnGlobalLayoutListener(listener);
		}
	}


	public static int getAbsoluteBottomCoordinates(View v) {
		int[] coords = {0,0};
		v.getLocationOnScreen(coords);

		return coords[1] + v.getHeight();		
	}


	public static int getAbsoluteTopCoordinates(View v) {
		int[] coords = {0,0};
		v.getLocationOnScreen(coords);

		return coords[1];		
	}


	@SuppressLint("NewApi")
	@SuppressWarnings("deprecation")
	public static int getWindowWidth( WindowManager windowManager ) {
		int sdk = android.os.Build.VERSION.SDK_INT;
		Display display = windowManager.getDefaultDisplay();
		int width = 0;
		if (sdk < 13) {
			width = display.getWidth();
		} else {
			Point size = new Point();
			display.getSize(size);
			width = size.x;
		}
		return width;
	}


	public static Bitmap loadBitmapFromView(View v) {
		int width = v.getWidth();
		int height = v.getHeight();
		Bitmap b = null;
		if ( width > 0 && height > 0 ) {
			b = Bitmap.createBitmap( width, height, Bitmap.Config.ARGB_8888 );
			Canvas c = new Canvas(b);
			v.layout(0, 0, v.getWidth(), v.getHeight());
			v.draw(c);
		}
		return b;
	}
	
	
	@SuppressWarnings("deprecation")
	public static Point getScreenCoo( Activity activity ) {
		Point size = new Point();
		Display display = activity.getWindowManager().getDefaultDisplay();
		
		if ( SDK >= android.os.Build.VERSION_CODES.HONEYCOMB_MR2 ) {
			display.getSize(size);
		} else {
			size = new Point(display.getWidth(), display.getHeight());
		}
		return size;
	}
	
	
	public static Point getScreenCenter( Activity activity ) {
		Point center = getScreenCoo(activity);
		center.set(center.x/2, center.y/2);
		return center;
	}

}
