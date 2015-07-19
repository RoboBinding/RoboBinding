package org.robobinding.widget.view;

import static org.mockito.Mockito.mock;

import org.robobinding.viewattribute.property.OneWayPropertyViewAttribute;
import org.robobinding.widget.AbstractMultiTypePropertyViewAttributeTest;
import org.robobinding.widget.view.VisibilityAttribute.BooleanVisibilityAttribute;
import org.robobinding.widget.view.VisibilityAttribute.IntegerVisibilityAttribute;
import org.robolectric.annotation.Config;

import android.view.View;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
@Config(manifest = Config.NONE)
public class VisibilityAttributeTest extends AbstractMultiTypePropertyViewAttributeTest {
	@Override
	protected void setTypeMappingExpectations() {
		forPropertyType(int.class).expectAttributeType(IntegerVisibilityAttribute.class);
		forPropertyType(Integer.class).expectAttributeType(IntegerVisibilityAttribute.class);
		forPropertyType(boolean.class).expectAttributeType(BooleanVisibilityAttribute.class);
		forPropertyType(Boolean.class).expectAttributeType(BooleanVisibilityAttribute.class);
	}

	@Override
	protected OneWayPropertyViewAttribute<View, ?> createViewAttribute(Class<?> propertyType) {
		@SuppressWarnings("unchecked")
		VisibilityFactory<View> visibilityFactory = mock(VisibilityFactory.class);
		return new VisibilityAttribute<View>(visibilityFactory).create(null, propertyType);
	}

}
