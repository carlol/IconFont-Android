package io.carlol.iconfont.fragment.iconset;

import io.carlol.iconfont.R;
import io.carlol.iconfont.fragment.abstracts.FontFragment;

/**
 * 
 * @author c.luchessa
 *
 */
public class FontAwesomeFragment extends FontFragment {
	public static final String TAG = FontAwesomeFragment.class.getSimpleName();

	
	public static FontAwesomeFragment newInstance() {
		FontAwesomeFragment frag = new FontAwesomeFragment();
		return frag;
	}

	@Override
	protected int getIconStringArrayResourceId() {
		return R.array.fa_icons;
	}

	@Override
	protected int getIconLayoutResourceId() {
		return R.layout.view_font_awesome;
	}



}
