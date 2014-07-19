package io.carlol.iconfont;

import io.carlol.iconfont.base.BaseDrawerActivity;
import io.carlol.iconfont.base.BaseFragment;
import io.carlol.iconfont.constants.C;
import io.carlol.iconfont.fragment.main.PagerFragment;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 
 * @author c.luchessa
 *
 */
public class IconFontActivity extends BaseDrawerActivity {
	public static final String TAG = IconFontActivity.class.getSimpleName();

	private ListView mMenuList;

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

		// init menu
		mMenuList = (ListView) this.findViewById(R.id.drawer_content);
		Resources res = getResources();
		String[] menuLabelResIds = {
			res.getString(R.string.icon_page_title_font_awesome)
			, res.getString(R.string.icon_page_title_stroke)
			, res.getString(R.string.icon_page_title_typicon)
			, res.getString(R.string.icon_page_title_fontelico)
			, res.getString(R.string.icon_page_title_elusive)
		};
		mMenuList.setAdapter(new ArrayAdapter<String>(this, R.layout.view_item_main_menu, menuLabelResIds));
		mMenuList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				CheckedTextView cTV = (CheckedTextView) view;
				cTV.setChecked(true);
			}
		});
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

			redirectTo(C.URL_GITHUB);

			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void switchToSection(String sectionId, Object... args) {
		BaseFragment frag = null;
		boolean addToBackstack = false;

		if ( PagerFragment.TAG.equals(sectionId) ) {
			frag = PagerFragment.newInstance();

		} 

		if (frag == null) {
			return; // EXIT
		}

		this.performReplace(frag, sectionId, addToBackstack);
	}
	
	public void selectMainMenuItem( int position ) {
		mMenuList.setItemChecked(position, true);
	}

	public View getActionBarView() {
		View v = getWindow().getDecorView();
		int resId = getResources().getIdentifier("action_bar_container", "id", "android");
		return v.findViewById(resId);
	}

}
