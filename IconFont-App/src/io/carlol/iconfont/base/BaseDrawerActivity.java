package io.carlol.iconfont.base;

import io.carlol.iconfont.R;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.view.View;

/**
 * 
 * @author c.luchessa
 *
 */
public abstract class BaseDrawerActivity extends BaseActivity implements PanelInterface {
	
	private static final int MAIN_DRAWER_LAYOUT 		= R.layout.activity_main;
	private static final int DRAWER_LAYOUT_RESOURCE_ID 	= R.id.drawer_layout;
	private static final int DRAWER_CONTENT_RESOURCE_ID	= R.id.drawer_content;

	private DrawerLayout mDrawerLayout;
	private View mDrawerContent;

	private boolean onCloseMenuListenerExpiration;
	private boolean onOpenMenuListenerExpiration;

	private OnOpenMenuListener onOpenMenuListener;
	private OnCloseMenuListener onCloseMenuListener;
	
	DrawerListener mDrawerListener = new DrawerListener() {
		@Override
		public void onDrawerStateChanged(int arg0) {
			// nothing to do
		}
		
		@Override
		public void onDrawerSlide(View arg0, float arg1) {
			// nothing to do
		}
		
		@Override
		public void onDrawerOpened(View arg0) {
			if ( onOpenMenuListener != null ) onOpenMenuListener.onPostOpenMenu();
			if ( onOpenMenuListenerExpiration ) mDrawerLayout.setDrawerListener(null); // disable
		}
		
		@Override
		public void onDrawerClosed(View arg0) {
			if ( onCloseMenuListener != null ) onCloseMenuListener.onPostCloseMenu();
			if ( onCloseMenuListenerExpiration ) mDrawerLayout.setDrawerListener(null); // disable			
		}
	};
	
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(MAIN_DRAWER_LAYOUT);
		
		mDrawerLayout = (DrawerLayout) this.findViewById(DRAWER_LAYOUT_RESOURCE_ID);
		mDrawerContent = this.findViewById(DRAWER_CONTENT_RESOURCE_ID);
	}
	
	
	@Override // @PanelInterface
	public void switchMenu() {
		if ( this.isMenuOpen() ) {
			this.closeMenu();
		} else {
			this.openMenu();
		}
	}

	@Override // @PanelInterface
	public void openMenu() {
		this.openMenu(null);
	}
	
	@Override // @PanelInterface
	public void openMenu(final OnOpenMenuListener onOpenMenudListener) {
		if ( this.isMenuOpen() ) return;	// EXIT: if menu is not open: do nothing
		if ( onOpenMenudListener != null ) this.setOpenMenuListener(onOpenMenudListener, true);
		mDrawerLayout.openDrawer(mDrawerContent);
	}
	
	@Override // @PanelInterface
	public void setMenuSwipeEnabled(boolean enable) {
		mDrawerLayout.setDrawerLockMode(enable ? DrawerLayout.LOCK_MODE_UNLOCKED : DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
	}
	

	@Override // @PanelInterface
	public void setOpenMenuListener(final OnOpenMenuListener onOpenMenuListener, final boolean expiration) {
		this.onOpenMenuListenerExpiration = expiration;
		this.onOpenMenuListener = onOpenMenuListener; 
		mDrawerLayout.setDrawerListener(mDrawerListener);
	}

	@Override // @PanelInterface
	public void closeMenu() {
		this.closeMenu(null);
	}

	@Override // @PanelInterface
	public void closeMenu(final OnCloseMenuListener onCloseMenudListener) {
		if ( ! this.isMenuOpen() ) return;	// EXIT: if menu is not open: do nothing
		if ( onCloseMenudListener != null ) this.setCloseMenuListener(onCloseMenudListener, true);
		mDrawerLayout.closeDrawer(mDrawerContent);
	}
	
	@Override // @PanelInterface
	public void setCloseMenuListener(final OnCloseMenuListener onCloseMenuListener, final boolean expiration) {
		this.onCloseMenuListenerExpiration = expiration;
		this.onCloseMenuListener = onCloseMenuListener; 
		mDrawerLayout.setDrawerListener(mDrawerListener);
	}

	@Override // @PanelInterface
	public boolean isMenuOpen() {
		return mDrawerLayout.isDrawerOpen(mDrawerContent);
	}

}