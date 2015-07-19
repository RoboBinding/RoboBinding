package org.robobinding.widget.menuitem;

import org.robobinding.util.PrimitiveTypeUtils;
import org.robobinding.viewattribute.property.OneWayMultiTypePropertyViewAttribute;
import org.robobinding.viewattribute.property.OneWayPropertyViewAttribute;

import android.view.MenuItem;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class TitleAttribute implements OneWayMultiTypePropertyViewAttribute<MenuItem> {
	@Override
	public OneWayPropertyViewAttribute<MenuItem, ?> create(MenuItem view, Class<?> propertyType) {
		if (PrimitiveTypeUtils.integerIsAssignableFrom(propertyType)) {
			return new ResourceIdTitleAttrbite();
		} else if (String.class.isAssignableFrom(propertyType)) {
			return new StringTitleAttribute();
		}

		return null;
	}

	public static class ResourceIdTitleAttrbite implements OneWayPropertyViewAttribute<MenuItem, Integer> {
		@Override
		public void updateView(MenuItem view, Integer newValue) {
			view.setTitle(newValue);
		}
	}

	public static class StringTitleAttribute implements OneWayPropertyViewAttribute<MenuItem, String> {
		@Override
		public void updateView(MenuItem view, String newValue) {
			view.setTitle(newValue);
		}
	}
}
