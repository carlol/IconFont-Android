package it.open.androidlab.dialog;

import it.open.androidlab.activity.base.Bridge;
import android.app.Activity;
import android.support.v4.app.DialogFragment;
import android.view.View;

/**
 * 
 * @author c.luchessa
 *
 */
public class BaseDialogFragment extends DialogFragment {

	protected Bridge mBridge;
	
	protected View mContentView;

	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		
		mBridge = (Bridge) activity;
	}
	
}
