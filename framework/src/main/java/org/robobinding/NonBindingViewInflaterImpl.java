package org.robobinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class NonBindingViewInflaterImpl implements NonBindingViewInflater {
	private final LayoutInflater layoutInflater;

	public NonBindingViewInflaterImpl(LayoutInflater layoutInflater) {
		this.layoutInflater = layoutInflater;
	}

	@Override
	public View inflateWithoutRoot(int layoutId) {
		return layoutInflater.inflate(layoutId, null);
	}

	@Override
	public View inflate(int layoutId, ViewGroup root, boolean attachToRoot) {
		return layoutInflater.inflate(layoutId, root, attachToRoot);
	}
}
