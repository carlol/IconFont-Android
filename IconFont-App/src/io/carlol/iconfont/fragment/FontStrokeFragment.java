package io.carlol.iconfont.fragment;

import io.carlol.iconfont.R;
import io.carlol.iconfont.fragment.abstracts.FontFragment;

/**
 * 
 * @author c.luchessa
 *
 */
public class FontStrokeFragment extends FontFragment {
	public static final String TAG = FontStrokeFragment.class.getSimpleName();


	public static FontStrokeFragment newInstance() {
		FontStrokeFragment frag = new FontStrokeFragment();
		return frag;
	}
	
	@Override
	protected int getIconStringArrayResourceId() {
		return R.array.stroke_icons;
	}

	@Override
	protected int getIconLayoutResourceId() {
		return R.layout.view_font_stroke;
	}

	
}
