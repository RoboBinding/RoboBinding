package org.robobinding;

import org.robobinding.BindingContext.SubViewBinderFactory;

import android.view.View;
import android.view.ViewGroup;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class SubBindingContext {
	private final SubViewBinderFactory factory;
	private final Object presentationModel;

	SubBindingContext(SubViewBinderFactory factory, Object presentationModel) {
		this.factory = factory;
		this.presentationModel = presentationModel;
	}

	public View inflateAndBindWithoutAttachingToRoot(int layoutId, ViewGroup root) {
		SubViewBinder subViewBinder = factory.createSubViewBinder();
		return subViewBinder.inflateAndBindWithoutAttachingToRoot(layoutId, presentationModel, root);
	}
}
