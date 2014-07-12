package it.open.androidlab.dialog;


import it.open.androidlab.R;
import android.os.Bundle;
import android.view.View;

/**
 * 
 * @author c.luchessa
 *
 */
public class BaseProgressDialogFragment extends BaseStyledDialogFragment {

	public static BaseProgressDialogFragment newInstance() {
		return new BaseProgressDialogFragment();
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
		setCancelable(false); // prevent back button to cancel the dialogfrag
	}
	
	@Override
	protected int getContentResourceId() {
		return R.layout.lab_dialog_progress;
	}

	@Override
	protected void onDialogInflated(View v) {
		// nothing to do
	}

	@Override
	public void onDestroyView() {
	    if (getDialog() != null && getRetainInstance())
	        getDialog().setDismissMessage(null);
	    super.onDestroyView();
	}
	
}
