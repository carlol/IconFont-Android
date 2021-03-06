package io.carlol.iconfont.base;


import io.carlol.iconfont.R;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

/**
 * 
 * @author c.luchessa
 *
 */
public abstract class BaseActivity extends ActionBarActivity implements Bridge {

	protected static final int MAIN_LAYOUT = R.layout.lab_main;

	protected static final int SECTION_CONTAINER = R.id.fragment_container;

	protected Handler mHandler;

	private static boolean activityVisible;
	
	protected BaseFragment mCurrentSection;

	private boolean isFirstOpening;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mHandler = new Handler();

		setContentView(MAIN_LAYOUT);

		isFirstOpening = savedInstanceState == null;

		if (savedInstanceState == null) {
			this.cleanPreviousExecution(); // clear singletons and other data
			this.initCurrentExecution(); // register singleton e other data

		} else { // is resumed
			this.restoreStatus(savedInstanceState);
		}

		// TODO: add views var initialization
	}


	protected void cleanPreviousExecution() {
		// TODO: clear singletons and other data
	}


	protected void initCurrentExecution() {
		// TODO: register singleton e other data
	}


	protected void restoreStatus(Bundle savedInstanceState) {
		mCurrentSection = (BaseFragment) getSupportFragmentManager().findFragmentById(SECTION_CONTAINER);
		// TODO: reinizializzare variabili contenenti frammenti
	}


	@Override
	protected void onPause() {
		super.onPause();
		activityPaused();
	}


	@Override
	protected void onResume() {
		super.onResume();
		activityResumed();
	}


	@Override
	public void onBackPressed() {
		if (!( mCurrentSection != null && mCurrentSection.onBackPressed()) ) {
			super.onBackPressed();
		}
	}


	@Override // @Bridge
	public boolean isActivityVisible() {
		return activityVisible;
	}  


	private static void activityResumed() {
		activityVisible = true;
	}


	private static void activityPaused() {
		activityVisible = false;
	}

	@Override // @Bridge
	public void performReplace(BaseFragment frag, String tag) {
		this.performReplace(frag, tag, true);
	}

	@Override // @Bridge
	public void performReplace(BaseFragment frag, String tag, boolean addToBackstack ) {
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction().replace(SECTION_CONTAINER, frag, tag);
		if (addToBackstack) {
			transaction.addToBackStack(tag);
		}
		transaction.commitAllowingStateLoss();
		mCurrentSection = frag;
	}

	@Override // @Bridge
	public void mkToast( String text ) {
		Context context = getApplicationContext();
		int duration = Toast.LENGTH_SHORT;
		Toast toast = Toast.makeText(context, text, duration);
		toast.show();
	}

	@Override // @Bridge
	public void hideProgressDialog() {
		//		DialogFragment dialogFragment = (DialogFragment)getSupportFragmentManager().findFragmentByTag(PROGRESS_TAG); 
		//		if (dialogFragment != null) { dialogFragment.dismissAllowingStateLoss(); }
	}

	@Override // @Bridge
	public void showProgressDialog() {
		//		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		//		mProgressDialog = BaseProgressDialogFragment.newInstance();
		//		mProgressDialog.show(ft, PROGRESS_TAG);
	}

	@Override // @Bridge
	public void lockScreenOrientation() {
		int currentOrientation = getResources().getConfiguration().orientation;
		if (currentOrientation == Configuration.ORIENTATION_PORTRAIT) {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		} else {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		}
		//		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
	}

	@Override // @Bridge
	public void unlockScreenOrientation() {
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
	}

	@Override
	public <T extends ActivityInterface> T get(Class<T> clazz) {
		return clazz.cast(this);
	}

	@Override
	public void closeKeyboard() {
		if ( this.getCurrentFocus() == null ) return; // EXIT

		InputMethodManager inputManager = (InputMethodManager) 
				this.getSystemService(Context.INPUT_METHOD_SERVICE); 

		if ( inputManager != null ) {
			inputManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(),
					InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}

	@Override
	public void redirectTo(String url) {
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setData(Uri.parse(url));
		startActivity(i);
	}

	@Override
	public boolean isFirstOpening() {
		return isFirstOpening;
	}

}