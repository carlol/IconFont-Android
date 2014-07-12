package io.carlol.iconfont;

import io.carlol.iconfont.fragment.FontAwesomeFragment;
import io.carlol.iconfont.fragment.FontStrokeFragment;
import io.carlol.iconfont.fragment.PagerFragment;
import it.open.androidlab.activity.base.BaseActivity;
import it.open.androidlab.fragment.base.BaseFragment;
import it.open.androidlab.util.Utils;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

/**
 * 
 * @author c.luchessa
 *
 */
public class IconFontActivity extends BaseActivity {
	public static final String TAG = IconFontActivity.class.getSimpleName();
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if ( this.isFirstOpening() ) {
			
			this.switchToSection(PagerFragment.TAG);
			
		}
		
		int titleId = getResources().getIdentifier("action_bar_title", "id", "android");
		TextView titleTextView = (TextView) this.getActionBarView().findViewById(titleId);
		
//		titleTextView.setTextSize(R.dimen.actionbar_title_text_size);
		
		titleTextView.setPadding(
				getResources().getDimensionPixelSize(R.dimen.actionbar_margin_left)
				, 0 //titleTextView.getPaddingTop()
				, 0 //titleTextView.getPaddingRight()
				, 0 //titleTextView.getPaddingBottom()
				);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
//
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		// Handle action bar item clicks here. The action bar will
//		// automatically handle clicks on the Home/Up button, so long
//		// as you specify a parent activity in AndroidManifest.xml.
//		int id = item.getItemId();
//		if (id == R.id.action_settings) {
//			return true;
//		}
//		return super.onOptionsItemSelected(item);
//	}


	@Override
	public void switchToSection(String sectionId, Object... args) {
		BaseFragment frag = null;
		boolean addToBackstack = false;

		if ( FontAwesomeFragment.TAG.equals(sectionId) ) {
			frag = FontAwesomeFragment.newInstance();
			
		} else if ( FontStrokeFragment.TAG.equals(sectionId) ) {
			frag = FontStrokeFragment.newInstance();
			
		} if ( PagerFragment.TAG.equals(sectionId) ) {
			frag = PagerFragment.newInstance();
			
		} 

		if (frag == null) {
			Utils.debug(TAG, "can't switchToSection if sectionId doesn't match!");
			return; // EXIT
		}

		this.performReplace(frag, sectionId, addToBackstack);
	}
	
	public View getActionBarView() {
	    View v = getWindow().getDecorView();
	    int resId = getResources().getIdentifier("action_bar_container", "id", "android");
	    return v.findViewById(resId);
	}


}
