package io.carlol.iconfont.base;



import java.lang.reflect.Field;

import android.app.Activity;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * 
 * @author c.luchessa
 *
 */
public abstract class TransactionFragment extends Fragment {

	protected FragmentManager mChildFragmentManager;
	protected Handler mHandler = new Handler();

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mChildFragmentManager = getChildFragmentManager();
	}
	
	@Override
	public void onDetach() {
	    super.onDetach();

	    try {
	        Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
	        childFragmentManager.setAccessible(true);
	        childFragmentManager.set(this, null);

	    } catch (NoSuchFieldException e) {
	        throw new RuntimeException(e);
	    } catch (IllegalAccessException e) {
	        throw new RuntimeException(e);
	    }
	}

	/**
	 * Replace transaction with no tag and no backstack
	 */
	protected void performReplace(Fragment frag, int container) {
		performTransaction(frag, null, container, true, false);
	}

	/**
	 * Add transaction with no tag and no backstack
	 */
	protected void performAdd(Fragment frag, int container) {
		performTransaction(frag, null, container, false, false);
	}

	/**
	 * Replace transaction with tag and no backstack
	 */
	protected void performReplace(Fragment frag, String tag, int container) {
		performTransaction(frag, tag, container, true, false);
	}

	/**
	 * Add transaction with tag and no backstack
	 */
	protected void performAdd(Fragment frag, String tag, int container) {
		performTransaction(frag, tag, container, false, false);
	}

	/**
	 * Replace transaction with tag and backstack
	 */
	protected void performReplaceBackStack(Fragment frag, String tag, int container) {
		performTransaction(frag, tag, container, true, true);
	}

	/**
	 * Replace transaction with no tag and backstack
	 */
	protected void performReplaceBackStack(Fragment frag, int container) {
		performTransaction(frag, null, container, true, true);
	}

	/**
	 * Add transaction with  tag and  backstack
	 */
	protected void performAddBackStack(Fragment frag, String tag, int container) {
		performTransaction(frag, tag, container, false, true);
	}

	/**
	 * Add transaction with no tag and backstack
	 */
	protected void performAddBackStack(Fragment frag, int container) {
		performTransaction(frag, null, container, false, true);
	}

	/**
	 * Perform the transaction.
	 *
	 * @param frag the fragment to be added/replaced
	 * @param tag  the tag identifying the fragment in current transaction
	 * @param container the container associated with current transaction
	 * @param replace true if replace, false if add
	 * @param addToBackStack
	 */
	private void performTransaction(Fragment frag, String tag, int container, boolean replace, boolean addToBackStack) {
		FragmentTransaction t = mChildFragmentManager.beginTransaction();
		if (tag != null) {
			t = replace 
					? t.replace(container, frag, tag) 
							: t.add(container, frag, tag);
		} else {
			t = replace 
					? t.replace(container, frag) 
							: t.add(container, frag);
		}
		if (addToBackStack) {
			t.addToBackStack(null);
		}
		t.commit();
	}

	public boolean onBackPressed() {
		return false;
	}

}