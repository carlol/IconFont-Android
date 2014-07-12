package it.open.androidlab.fragment.base;


import it.open.androidlab.R;
import it.open.androidlab.util.GraphicUtils;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

/**
 * 
 * @author c.luchessa
 *
 */
public abstract class BaseParentFragment extends BaseFragment {

	protected final static int MAIN_LAYOUT = R.layout.lab_fragment_section;
	protected final static int CHILD_CONTAINER = R.id.child_fragment_container;

	private Menu mMenu;
	
	protected BaseChildFragment mContentFrag;

	private Bitmap mFakeBitmapView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		mContentView = inflater.inflate(MAIN_LAYOUT, container, false);
		return mContentView;
	}

	@Override
	public void onPrepareOptionsMenu(Menu menu) {
		super.onPrepareOptionsMenu(menu);

		mMenu = menu;
		
		if ( mContentFrag != null ) {
			mContentFrag.onPrepareOptionsMenu(menu);
		}
		
	}
	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		if ( savedInstanceState == null ) {
			initFrags();
			attachFrags();

		} else {		
			mContentFrag = (BaseChildFragment) mChildFragmentManager.findFragmentById(CHILD_CONTAINER);
			if ( mMenu != null ) { 
				// this is a safety check (testing on nexus5, it's always executed in onPrepareOperationsMenu())
				mContentFrag.onPrepareOptionsMenu(mMenu);
			}
		}
	}


	// NB: it's executed before onResume
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		mContentFrag.onActivityResult(requestCode, resultCode, data);
	}


	@Override
	public void onPause() {
		if ( getResources().getBoolean(R.bool.fragment_transition_animation_enabled) && mContentView != null ) {
			mFakeBitmapView = GraphicUtils.loadBitmapFromView(mContentView);
		}
		super.onPause();
	}


	@Override
	public void onDestroyView() {
		if ( getResources().getBoolean(R.bool.fragment_transition_animation_enabled) ) {
			View view = getView();
			if ( view != null && mFakeBitmapView != null ) {
				((ViewGroup)view).removeAllViews();
				BitmapDrawable bitmap = new BitmapDrawable(getActivity().getResources(), mFakeBitmapView);
				GraphicUtils.setBackground(view, bitmap);
				mFakeBitmapView = null;
			}
		}
		super.onDestroyView();
	}


	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		// TODO: save tmp state
	}


	protected void restoreStatus(Bundle icicle) {
		// TODO: restore tmp state
	}


	/**
	 * @return true to do not propagate onBackPressed event, otherwise false.
	 */
	@Override
	public boolean onBackPressed() {

		// before check on backstack, useful if 1st frag in attached with addToBackStack=false
		if ( mContentFrag != null && mContentFrag.onBackPressed() ) {
			return true; // EXIT: custom onBack action
		}

		if (mChildFragmentManager.getBackStackEntryCount() <= 0) {
			return false; // EXIT: no children in back stack
		}

		mChildFragmentManager.popBackStackImmediate(); // no delay

		// update current fragment
		mContentFrag = (BaseChildFragment) mChildFragmentManager.findFragmentById(CHILD_CONTAINER);

		if ( mContentFrag == null ) {
			return false; // EXIT: empty BaseFragment
		}

		return true;
	}

	/*
	 * Index of initializations
	 */
	protected void initFrags() {
		// TODO could be other frags
		initContent();
	}


	/**
	 * Set the Fragment representing the default content. Throws an 
	 * IllegalArgumentException if a null fragment is provided.
	 */
	private void initContent() {
		mContentFrag = getContentFrag();
	}


	/*
	 * Get default content (MUST be overridden).
	 */
	protected BaseChildFragment getContentFrag() {
		return null;
	}


	/*
	 * Index of attachments
	 */
	protected void attachFrags() {
		attachFrag(mContentFrag, CHILD_CONTAINER, true);
		// TODO could be other frags
	}


	/*
	 * Attach a fragment to its container (by resourceId)
	 */
	protected void attachFrag(Fragment frag, int container, boolean show) {
		if (frag != null && show) {
			performReplace(frag, null, container);
		}
	}


	/*
	 * Change content of nested fragment
	 */
	public void switchTo(boolean addToBackStack, BaseChildFragment newFragment, String tag) {
		if ( addToBackStack ) {
			performReplaceBackStack(newFragment, tag, CHILD_CONTAINER);
		} else {
			performReplace(newFragment, tag, CHILD_CONTAINER);
		}
		mContentFrag = newFragment;
	}

}
