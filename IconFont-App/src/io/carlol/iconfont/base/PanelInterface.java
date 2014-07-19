package io.carlol.iconfont.base;


/**
 * W
 * @author c.luchessa
 *
 */
public interface PanelInterface extends ActivityInterface {
	
	public void switchMenu();
	public boolean isMenuOpen();
	
	public void setMenuSwipeEnabled(boolean enable);
	
	public void openMenu();
	public void openMenu(OnOpenMenuListener onOpenMenudListener);
	public void setOpenMenuListener(OnOpenMenuListener onOpenMenudListener, boolean expiration);
	
	public void closeMenu();
	public void closeMenu(OnCloseMenuListener onCloseMenuListener);
	public void setCloseMenuListener(OnCloseMenuListener onCloseMenudListener, boolean expiration);
	
	/**
	 * 
	 */
	public interface OnOpenMenuListener {

		public void onPreOpenMenu();

		public void onPostOpenMenu();
	}

	/**
	 * 
	 */
	public interface OnCloseMenuListener {

		public void onPreCloseMenu();

		public void onPostCloseMenu();
	}
}
