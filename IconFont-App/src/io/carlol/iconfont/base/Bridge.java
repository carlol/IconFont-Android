package io.carlol.iconfont.base;




/**
 * Contains shared Activity methods
 * 
 * @author c.luchessa
 *
 */
public interface Bridge extends ActivityInterface {
	
	// Common
	public boolean isActivityVisible();
	
	// Navigation management
	public void switchToSection(String sectionId, Object... args);
	
	public void performReplace(BaseFragment frag, String tag);
	public void performReplace(BaseFragment frag, String tag, boolean addToBackstack );

	// Utils
	public void mkToast( String text );
	public void showProgressDialog();
	public void hideProgressDialog();
	public void lockScreenOrientation();
	public void unlockScreenOrientation();
	public void closeKeyboard();
	public boolean isFirstOpening();
	public void redirectTo(String url);
	
	// Custom
	public abstract <T extends ActivityInterface> T get( Class<T> clazz );
	
}
