package org.robobinding;

import android.os.Build;
import android.view.LayoutInflater;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 * 
 */
public class ViewCreationListenerInstaller {
	private final ViewCreationListener listener;

	public ViewCreationListenerInstaller(ViewCreationListener listener) {
		this.listener = listener;
	}

	public LayoutInflater installWith(final LayoutInflater layoutInflater) {
		ViewNameResolver viewNameResolver = new ViewNameResolver();
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			return LayoutInflaterHoneyComb.create(layoutInflater, listener, viewNameResolver);
		} else {
			return LayoutInflaterPreHoneyComb.create(layoutInflater, listener, viewNameResolver);
		}
	}
}
