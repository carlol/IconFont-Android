package it.open.androidlab.fragment.base;


import it.open.androidlab.activity.base.Bridge;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.ViewGroup;

/**
 * 
 * @author c.luchessa
 *
 */
public abstract class BaseChildFragment extends Fragment {
	
	protected Handler mHandler;
	protected Bridge mBridge;
	protected BaseParentFragment mParentFragment;
	protected ViewGroup mContentView;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		mHandler = new Handler();
		mBridge = (Bridge) getActivity();
		mParentFragment = (BaseParentFragment) getParentFragment();
	}
	
	/**
	 * @return true to do not propagate onBackPressed event, otherwise false.
	 */
	public boolean onBackPressed() {  
		return false;
	}
	
}
