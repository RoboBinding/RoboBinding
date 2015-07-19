package org.robobinding.widget.view;

import org.robobinding.viewattribute.property.OneWayMultiTypePropertyViewAttributeFactory;

import android.view.View;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class VisibilityAttributeFactory<T extends View> implements OneWayMultiTypePropertyViewAttributeFactory<T> {
	private VisibilityFactory<T> visibilityFactory;

	public VisibilityAttributeFactory(VisibilityFactory<T> visibilityFactory) {
		this.visibilityFactory = visibilityFactory;
	}

	@Override
	public VisibilityAttribute<T> create() {
		return new VisibilityAttribute<T>(visibilityFactory);
	}
}
