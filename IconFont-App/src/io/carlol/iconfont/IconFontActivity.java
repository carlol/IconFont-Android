package io.carlol.iconfont;

import io.carlol.iconfont.base.BaseActivity;
import io.carlol.iconfont.base.BaseFragment;
import io.carlol.iconfont.constants.C;
import io.carlol.iconfont.fragment.FontAwesomeFragment;
import io.carlol.iconfont.fragment.FontStrokeFragment;
import io.carlol.iconfont.fragment.PagerFragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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
		
		titleTextView.setPadding(
				getResources().getDimensionPixelSize(R.dimen.actionbar_margin_left)
				, 0 
				, 0 
				, 0 
				);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		
		if (id == R.id.action_github) {
			
			Intent i = new Intent(Intent.ACTION_VIEW);
			i.setData(Uri.parse(C.URL_GITHUB));
			startActivity(i);
			
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

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