package io.carlol.iconfont.fragment;

import io.carlol.iconfont.R;
import io.carlol.iconfont.base.BaseSimpleFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class PagerFragment extends BaseSimpleFragment {
	public static final String TAG = PagerFragment.class.getSimpleName();

	private static final int NUM_PAGES = 3;

	private TextView mTitleView;
	private ViewPager mViewPager;


	public static PagerFragment newInstance() {
		PagerFragment frag = new PagerFragment();
		return frag;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mContentView = inflater.inflate(R.layout.fragment_pager, null);

		mTitleView = (TextView) mContentView.findViewById(R.id.page_title);
		mViewPager = (ViewPager) mContentView.findViewById(R.id.pager);

		return mContentView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		mTitleView.setText(R.string.icon_page_title_font_awesome); // first page title
		
		mViewPager.setAdapter(new IconPagerAdapter(mChildFragmentManager));

		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				int titleResId = 0;

				switch (position) {
				case 0:
					titleResId = R.string.icon_page_title_font_awesome;
					break;

				case 1:
					titleResId = R.string.icon_page_title_stroke;
					break;

				case 2:
					titleResId = R.string.icon_page_title_typicon;
					break;

				default:
					break;
				}
				mTitleView.setText(titleResId);
			}

			@Override
			public void onPageScrolled(int position, float arg1, int arg2) { }

			@Override
			public void onPageScrollStateChanged(int arg0) { }
		});
	}


	/**
	 * A simple pager adapter that represents different icon pages
	 */
	private class IconPagerAdapter extends FragmentStatePagerAdapter {
		public IconPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {

			Fragment frag = null;

			switch (position) {
			case 0:
				frag = FontAwesomeFragment.newInstance();
				break;

			case 1:
				frag = FontStrokeFragment.newInstance();
				break;
				
			case 2:
				frag = FontTypiconFragment.newInstance();
				break;

			default:
				// impossible
				break;
			}

			return frag;
		}

		@Override
		public int getCount() {
			return NUM_PAGES;
		}
	}
}
