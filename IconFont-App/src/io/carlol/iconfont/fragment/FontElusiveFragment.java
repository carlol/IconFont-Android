package io.carlol.iconfont.fragment;

import io.carlol.iconfont.R;
import io.carlol.iconfont.fragment.abstracts.FontFragment;

/**
 * 
 * @author c.luchessa
 *
 */
public class FontElusiveFragment extends FontFragment {
	public static final String TAG = FontElusiveFragment.class.getSimpleName();


	public static FontElusiveFragment newInstance() {
		FontElusiveFragment frag = new FontElusiveFragment();
		return frag;
	}
	
	@Override
	protected int getIconStringArrayResourceId() {
		return R.array.elusive_icons;
	}

	@Override
	protected int getIconLayoutResourceId() {
		return R.layout.view_font_elusive;
	}

	
}
