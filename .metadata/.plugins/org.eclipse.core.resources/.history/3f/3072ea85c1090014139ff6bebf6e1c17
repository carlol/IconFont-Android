package it.open.androidlab.dialog;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 
 * @author c.luchessa
 *
 */
public abstract class BaseStyledDialogFragment extends BaseDialogFragment {

	protected Handler mHandler;

	protected BaseStyledDialogFragment() {}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Translucent_NoTitleBar);
		
		mHandler = new Handler();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		if ( getContentResourceId() != 0 ) {
			mContentView = inflater.inflate(getContentResourceId(), container, false);
			onDialogInflated(mContentView);
		}
		return mContentView;
	}

	protected int getContentResourceId() {
		return 0;
	};

	protected void onDialogInflated(View v) {
		
	}

}