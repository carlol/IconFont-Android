package io.carlol.iconfont.base;


import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

/**
 * 
 * @author c.luchessa
 *
 */
public abstract class BaseFragment extends TransactionFragment {

	protected Bridge mBridge;
	protected Handler mHandler;
	
	protected View mContentView;

	protected boolean isFirstOpening;
	
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		mBridge = (Bridge) activity;
		mHandler = new Handler();
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if ( savedInstanceState != null ) {
			restoreStatus(savedInstanceState);
		}

	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		isFirstOpening = savedInstanceState == null && mBridge.isFirstOpening();
	}
	
	protected void restoreStatus(Bundle icicle) {
		// TODO: restore tmp state
	}
	
	/**
	 * @return true to do not propagate onBackPressed event, otherwise false.
	 */
	public boolean onBackPressed() {
		return false;
	}
	
}
