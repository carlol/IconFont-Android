IconFont-Android
================

Android modular implementation of font-based icons.

**@see** sample app: [Play Store][1]

###[XML]

    <io.carlol.iconfont.ui.IconViewFontAwesome
        xmlns:custom="http://schemas.android.com/apk/res-auto"
        android:id="@+id/icon_font"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:textColor="#FF333333"
        android:textSize="26dp"
        custom:fa_icon="fa_music" />

-

     <io.carlol.iconfont.ui.IconViewStroke
        xmlns:custom="http://schemas.android.com/apk/res-auto"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:textColor="#FF333333"
        android:textSize="26dp"
        custom:stroke_icon="pe_7s_like" />
        
###[Programmatically]


		IconViewFontAwesome iconAF = new IconViewFontAwesome(this, null);
		iconAF.setIcon(R.id.fa_music);
		iconAF.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 26);
		iconAF.setTextColor(Color.parseColor("#FF333333"));

-
	
		IconViewStroke iconSF = new IconViewStroke(this, null);
		iconSF.setIcon(R.id.pe_7s_like);
		iconSF.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 26);
		iconSF.setTextColor(Color.parseColor("#FF333333"));



=> TODO: incomplete doc


  [1]: https://play.google.com/store/apps/details?id=io.carlol.iconfont