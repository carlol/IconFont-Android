package it.open.androidlab.ui.base;


import it.open.androidlab.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * 
 * @author c.luchessa
 *
 */
public class ButtonCustomFont extends Button implements CustomizableFont {

	public ButtonCustomFont(Context context, AttributeSet attrs) {
		super(context, attrs);
		setFont(attrs);
	}

	public ButtonCustomFont(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		setFont(attrs);
	}

	public ButtonCustomFont(Context context, String typeface) {
		super(context);
		setTypeface(typeface);
	}
	
	public ButtonCustomFont(Context context) {
		super(context);
	}

	private void setFont(AttributeSet attrs) {
		if ( USE_CUSTOM_FONT ) {
			TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.TextViewCustomFont);
			String typeface = a.getString(R.styleable.TextViewCustomFont_ttf_name);
			setTypeface(typeface);
			a.recycle();
		}
	}

	public void setTypeface(String typeface) {
		setTypeface(Typefaces.get(this.getContext(), typeface));
	}
	
	public void setTypeface(String typeface, int style) {
		setTypeface(Typefaces.get(this.getContext(), typeface), style);
	}

}

