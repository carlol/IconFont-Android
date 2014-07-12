package it.open.androidlab;


import android.app.Application;
import android.content.Context;

/**
 * 
 * @author c.luchessa
 *
 */
public abstract class BaseApplication extends Application {

	protected static Application mSelf;


	public static Context context() {
		return mSelf;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		mSelf = this;
	}

}
