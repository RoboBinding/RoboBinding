package org.robobinding;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class NonBindingViewInflater {
    private final LayoutInflater layoutInflater;

    public NonBindingViewInflater(LayoutInflater layoutInflater) {
	this.layoutInflater = layoutInflater;
    }

    public View inflate(int layoutId) {
	return inflate(layoutId, null);
    }
    
    public View inflate(int layoutId, ViewGroup attachToRoot) {
	boolean shouldAttachToRoot = attachToRoot != null;
	if (shouldAttachToRoot) {
	    return layoutInflater.inflate(layoutId, attachToRoot, true);
	} else {
	    return layoutInflater.inflate(layoutId, null);
	}
    }
}
