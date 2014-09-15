package org.robobinding;

import android.view.LayoutInflater;
import android.view.LayoutInflater.Factory;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class ViewCreationListenerInstaller {
    private final LayoutInflater layoutInflater;
    
    public ViewCreationListenerInstaller(LayoutInflater layoutInflater) {
    	this.layoutInflater = layoutInflater;
    }
    
    public void install(ViewCreationListener listener) {
    	Factory factory = new ViewFactory(layoutInflater, new ViewNameResolver(), listener);
		layoutInflater.setFactory(factory);
    }
}
