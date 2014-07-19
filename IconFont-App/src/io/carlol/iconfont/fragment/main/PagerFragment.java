package io.carlol.iconfont.fragment.main;

import io.carlol.iconfont.IconFontActivity;
import io.carlol.iconfont.R;
import io.carlol.iconfont.base.BaseSimpleFragment;
import io.carlol.iconfont.constants.C;
import io.carlol.iconfont.fragment.iconset.FontAwesomeFragment;
import io.carlol.iconfont.fragment.iconset.FontElusiveFragment;
import io.carlol.iconfont.fragment.iconset.FontFontelicoFragment;
import io.carlol.iconfont.fragment.iconset.FontStrokeFragment;
import io.carlol.iconfont.fragment.iconset.FontTypiconFragment;
import io.carlol.iconfont.lib.ui.IconViewElusive;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

public class PagerFragment extends BaseSimpleFragment {
	public static final String TAG = PagerFragment.class.getSimpleName();

	private static final int NUM_PAGES = 5;

	private TextView mTitleTextView;
	private ViewPager mViewPager;
	private View mInfoContainerView;
	private TextView mLicenseTextView;
	private TextView mAuthorTextView;
	private View mLinkView;
	private IconViewElusive mIconTitleLeft;
	private IconViewElusive mIconTitleRight;


	public static PagerFragment newInstance() {
		PagerFragment frag = new PagerFragment();
		return frag;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mContentView = inflater.inflate(R.layout.fragment_pager, null);

		mTitleTextView = (TextView) mContentView.findViewById(R.id.page_title);
		mInfoContainerView = mContentView.findViewById(R.id.info_container);
		mLicenseTextView = (TextView) mContentView.findViewById(R.id.license);
		mAuthorTextView = (TextView) mContentView.findViewById(R.id.author);
		mLinkView = mContentView.findViewById(R.id.link);
		mViewPager = (ViewPager) mContentView.findViewById(R.id.pager);
		
		mIconTitleLeft = (IconViewElusive) mContentView.findViewById(R.id.icon_title_left);
		mIconTitleRight = (IconViewElusive) mContentView.findViewById(R.id.icon_title_right);

		return mContentView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		OnPageChangeListener onPageChangeListener = new OnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				int titleResId = 0;
				int licenseResId = 0;
				int authorResId = 0;
				String licenseRedirectUrl = "";
				String siteRedirectUrl = "";

				switch (position) {
				case 0:
					titleResId = R.string.icon_page_title_font_awesome;
					licenseResId = R.string.icon_page_license_font_awesome;
					authorResId = R.string.icon_page_author_font_awesome;
					siteRedirectUrl = C.URL_FONT_AWESOME;
					break;

				case 1:
					titleResId = R.string.icon_page_title_stroke;
					licenseResId = R.string.icon_page_license_stroke;
					authorResId = R.string.icon_page_author_stroke;
					licenseRedirectUrl = C.URL_STROKE_LICENSE;
					siteRedirectUrl = C.URL_STROKE;
					break;

				case 2:
					titleResId = R.string.icon_page_title_typicon;
					licenseResId = R.string.icon_page_license_typicon;
					authorResId = R.string.icon_page_author_typicon;
					siteRedirectUrl = C.URL_TYPICONS;
					break;

				case 3:
					titleResId = R.string.icon_page_title_fontelico;
					licenseResId = R.string.icon_page_license_fontelico;
					authorResId = R.string.icon_page_author_fontelico;
					siteRedirectUrl = C.URL_FONTELICO;
					break;

				case 4:
					titleResId = R.string.icon_page_title_elusive;
					licenseResId = R.string.icon_page_license_elusive;
					authorResId = R.string.icon_page_author_elusive;
					siteRedirectUrl = C.URL_ELUSIVE;
					break;

				default:
					break;
				}
				
				// update UI
				
				mTitleTextView.setText(titleResId);
				mLicenseTextView.setText(licenseResId);
				mAuthorTextView.setText(authorResId);
				
				if ( licenseRedirectUrl.equals("") ) {
					mLicenseTextView.setTextColor(getResources().getColor(R.color.dark_grey));
					mLicenseTextView.setOnClickListener(null);
				} else {
					final String _licenseRedirectUrl = licenseRedirectUrl;
					mLicenseTextView.setTextColor(getResources().getColor(R.color.holo_blue_light));
					mLicenseTextView.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							mBridge.redirectTo(_licenseRedirectUrl);
						}
					});
				}
				
				final String _linkRedirectUrl = siteRedirectUrl;
				mLinkView.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						mBridge.redirectTo(_linkRedirectUrl);
					}
				});
				
				// update main menu selection
				mBridge.get(IconFontActivity.class).selectMainMenuItem(position);
			}

			@Override
			public void onPageScrolled(int position, float arg1, int arg2) { }

			@Override
			public void onPageScrollStateChanged(int arg0) { }
		};
		
		mViewPager.setOnPageChangeListener(onPageChangeListener);
		onPageChangeListener.onPageSelected(0); // init: first page		
		mTitleTextView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if ( mInfoContainerView.getVisibility() == View.VISIBLE ) {
					mInfoContainerView.setVisibility(View.GONE);
					mIconTitleLeft.setIcon(R.id.el_icon_download);
					mIconTitleRight.setIcon(R.id.el_icon_download);
				} else {
					mInfoContainerView.setVisibility(View.VISIBLE);
					mIconTitleLeft.setIcon(R.id.el_icon_upload);
					mIconTitleRight.setIcon(R.id.el_icon_upload);
				}
			}
		});

		mViewPager.setAdapter(new IconPagerAdapter(mChildFragmentManager));
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

			case 3:
				frag = FontFontelicoFragment.newInstance();
				break;

			case 4:
				frag = FontElusiveFragment.newInstance();
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
