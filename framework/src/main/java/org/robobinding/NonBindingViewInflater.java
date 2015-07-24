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

	public View inflateWithoutRoot(int layoutId) {
		return layoutInflater.inflate(layoutId, null);
	}

	public View inflate(int layoutId, ViewGroup root, boolean attachToRoot) {
		return layoutInflater.inflate(layoutId, root, attachToRoot);
	}
}