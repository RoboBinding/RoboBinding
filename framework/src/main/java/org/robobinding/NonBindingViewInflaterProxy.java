package org.robobinding;

import android.view.View;
import android.view.ViewGroup;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class NonBindingViewInflaterProxy implements NonBindingViewInflater {
	private NonBindingViewInflater delegate = NULL;

	@Override
	public View inflateWithoutRoot(int layoutId) {
		return delegate.inflateWithoutRoot(layoutId);
	}

	@Override
	public View inflate(int layoutId, ViewGroup root, boolean attachToRoot) {
		return delegate.inflate(layoutId, root, attachToRoot);
	}
	
	public void setInflater(NonBindingViewInflater inflater) {
		this.delegate = inflater;
	}
	
	private static final NonBindingViewInflater NULL = new NonBindingViewInflater() {
		public View inflate(int layoutId, ViewGroup root, boolean attachToRoot) {
			throw new UnsupportedOperationException();
		}
		public View inflateWithoutRoot(int layoutId) {
			throw new UnsupportedOperationException();
		}
	};
}
