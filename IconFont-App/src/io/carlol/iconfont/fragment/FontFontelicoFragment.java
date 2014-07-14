package io.carlol.iconfont.fragment;

import io.carlol.iconfont.R;
import io.carlol.iconfont.fragment.abstracts.FontFragment;

/**
 * 
 * @author c.luchessa
 *
 */
public class FontFontelicoFragment extends FontFragment {
	public static final String TAG = FontFontelicoFragment.class.getSimpleName();


	public static FontFontelicoFragment newInstance() {
		FontFontelicoFragment frag = new FontFontelicoFragment();
		return frag;
	}
	
	@Override
	protected int getIconStringArrayResourceId() {
		return R.array.fontelico_icons;
	}

	@Override
	protected int getIconLayoutResourceId() {
		return R.layout.view_font_fontelico;
	}

	
}
