package org.robobinding;

import android.view.View;
import android.view.ViewGroup;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class SubViewBinder {
	private final NonBindingViewInflater nonBindingViewInflater;
	private final ViewBinder viewBinder;

	public SubViewBinder(NonBindingViewInflater nonBindingViewInflater, ViewBinder viewBinder) {
		this.nonBindingViewInflater = nonBindingViewInflater;
		this.viewBinder = viewBinder;
	}
	
	public View inflateWithoutAttachingToRoot(int layoutId, ViewGroup root) {
		return nonBindingViewInflater.inflate(layoutId, root, false);
	}

	public View inflateAndBindWithoutAttachingToRoot(int layoutId, Object presentationModel, ViewGroup root) {
		return viewBinder.inflateAndBindWithoutAttachingToRoot(layoutId, presentationModel, root);
	}

}