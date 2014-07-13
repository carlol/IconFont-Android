package io.carlol.iconfont.fragment;

import io.carlol.iconfont.R;
import io.carlol.iconfont.fragment.abstracts.FontFragment;

/**
 * 
 * @author c.luchessa
 *
 */
public class FontTypiconFragment extends FontFragment {
	public static final String TAG = FontTypiconFragment.class.getSimpleName();


	public static FontTypiconFragment newInstance() {
		FontTypiconFragment frag = new FontTypiconFragment();
		return frag;
	}
	
	@Override
	protected int getIconStringArrayResourceId() {
		return R.array.typeicon_icons;
	}

	@Override
	protected int getIconLayoutResourceId() {
		return R.layout.view_font_typicon;
	}

	
}
