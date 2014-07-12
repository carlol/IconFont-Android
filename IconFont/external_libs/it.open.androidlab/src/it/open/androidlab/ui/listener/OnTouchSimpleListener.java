package it.open.androidlab.ui.listener;


import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

/**
 * 
 * @author c.luchessa
 *
 */
public abstract class OnTouchSimpleListener implements OnTouchListener {

	public boolean onTouch(View v, MotionEvent event) {

		switch (event.getAction() & MotionEvent.ACTION_MASK) {
		
		case MotionEvent.ACTION_DOWN: 
			return this.onTouchDown(v);

		case MotionEvent.ACTION_MOVE:
			return this.onTouchMove(v);
			
		case MotionEvent.ACTION_OUTSIDE:   
			return this.onTouchOutside(v);
			
		case MotionEvent.ACTION_CANCEL:
			return this.onTouchCancel(v);

		case MotionEvent.ACTION_UP:   
			return this.onTouchUp(v);
		}

		return false; 
	}

	/**
	 * finger touches the screen 
	 * @param v
	 * @return
	 */
	public abstract boolean onTouchDown(View v);

	/**
	 * finger moves on the screen 
	 * @param v
	 * @return
	 */
	public abstract boolean onTouchMove(View v);

	/**
	 * finger leaves the screen 
	 * @param v
	 * @return
	 */
	public abstract boolean onTouchUp(View v);

	/**
	 * 
	 * @param v
	 * @return
	 */
	public abstract boolean onTouchCancel(View v);
	
	/**
	 * 
	 * @param v
	 * @return
	 */
	public abstract boolean onTouchOutside(View v);

}
