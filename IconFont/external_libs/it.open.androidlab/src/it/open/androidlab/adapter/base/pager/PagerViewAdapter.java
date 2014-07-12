package it.open.androidlab.adapter.base.pager;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;


/**
 * 
 * @author c.luchessa
 *
 */
public abstract class PagerViewAdapter extends PagerAdapter {

	private Context mContext;
	private int mItemResourceId;
	
	public PagerViewAdapter(Context context, int itemResourceId) {
		super();
		mContext = context;
		mItemResourceId = itemResourceId;
	}
	
	@Override
	public Object instantiateItem(View collection, int position) {
		LayoutInflater inflater = LayoutInflater.from(mContext);
		LinearLayout container = (LinearLayout) inflater.inflate(mItemResourceId, null);
		((ViewPager)collection).addView(container);
		return container;
	}

	@Override
	public int getItemPosition(Object object) {
		return POSITION_NONE;
	}

	@Override
	public void destroyItem(View collection, int position, Object view) {
		((ViewPager) collection).removeView((View) view);
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == object;
	}

	@Override
	public Parcelable saveState() {
		return null;
	}

	@Override
	public void restoreState(Parcelable arg0, ClassLoader arg1) { }

	@Override
	public void startUpdate(View arg0) { }

	@Override
	public void finishUpdate(View arg0) { }
	
}