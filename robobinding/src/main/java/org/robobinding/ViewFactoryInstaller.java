package org.robobinding;

import org.robobinding.ViewFactory.ViewCreationListener;

import android.view.LayoutInflater;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class ViewFactoryInstaller {
    private final ViewCreationListener listener;
    
    public ViewFactoryInstaller(ViewCreationListener listener) {
	this.listener = listener;
    }
    
    public void install(LayoutInflater layoutInflater) {
	new ViewFactory(layoutInflater, new ViewNameResolver(), listener);
    }
}
