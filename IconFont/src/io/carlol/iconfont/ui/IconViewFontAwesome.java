package io.carlol.iconfont.ui;

import io.carlol.iconfont.R;
import io.carlol.iconfont.ui.abstracts.AbstractIconView;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;


/**
 * 
 * @author c.luchessa
 *
 */
public class IconViewFontAwesome extends AbstractIconView {

	private static Typeface mFont;

	public IconViewFontAwesome(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public Typeface getTypeface(Context context) {
        if (mFont == null) {
            mFont = Typeface.createFromAsset(context.getAssets(), "fontawesome-webfont.ttf");
        }
        return mFont;
    }
	
	@Override
	protected int getFirstIconResId() {
		return R.id.fa_glass;
	}

	@Override
	protected int getStringArrayResId() {
		return R.array.fa_icons;
	}

	@Override
	protected int[] getStyleableResIds() {
		return R.styleable.IconViewFontAwesome;
	}

	@Override
	protected int getIconAttributeResId() {
		return R.styleable.IconViewFontAwesome_fa_icon;
	}

	@Override
	protected String getFontResName() {
		return "fontawesome-webfont.ttf";
	}

}
