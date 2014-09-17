package org.robobinding.widget.view;

import org.robobinding.util.PrimitiveTypeUtils;
import org.robobinding.viewattribute.property.MultiTypePropertyViewAttribute;
import org.robobinding.viewattribute.property.PropertyViewAttribute;

import android.view.View;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
public final class VisibilityAttribute<T extends View> implements MultiTypePropertyViewAttribute<T> {
	private final VisibilityFactory<T> visibilityFactory;

	public VisibilityAttribute(VisibilityFactory<T> visibilityFactory) {
		this.visibilityFactory = visibilityFactory;
	}

	@Override
	public PropertyViewAttribute<T, ?> create(final T view, Class<?> propertyType) {
		AbstractVisibility visibility = visibilityFactory.create(view);

		if (PrimitiveTypeUtils.integerIsAssignableFrom(propertyType)) {
			return new IntegerVisibilityAttribute<T>(visibility);
		} else if (PrimitiveTypeUtils.booleanIsAssignableFrom(propertyType)) {
			return new BooleanVisibilityAttribute<T>(visibility);
		}

		return null;
	}

	static class BooleanVisibilityAttribute<T extends View> implements PropertyViewAttribute<T, Boolean> {
		private AbstractVisibility visibility;

		public BooleanVisibilityAttribute(AbstractVisibility visibility) {
			this.visibility = visibility;
		}

		@Override
		public void updateView(T view, Boolean newValue) {
			if (newValue) {
				visibility.makeVisible();
			} else {
				visibility.makeGone();
			}
		}
	}

	static class IntegerVisibilityAttribute<T extends View> implements PropertyViewAttribute<T, Integer> {
		private AbstractVisibility visibility;

		public IntegerVisibilityAttribute(AbstractVisibility visibility) {
			this.visibility = visibility;
		}

		@Override
		public void updateView(T view, Integer newValue) {
			visibility.setVisibility(newValue);
		}
	}

}
