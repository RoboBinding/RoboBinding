package org.robobinding.widget.view;

import static org.mockito.Mockito.mock;

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
@Config(manifest=Config.NONE)
public class VisibilityAttributeTest extends
		AbstractMultiTypePropertyViewAttributeTest<VisibilityAttribute<View>> {
	@Override
	protected void setTypeMappingExpectations() {
		forPropertyType(int.class).expectAttribute(
				IntegerVisibilityAttribute.class);
		forPropertyType(Integer.class).expectAttribute(
				IntegerVisibilityAttribute.class);
		forPropertyType(boolean.class).expectAttribute(
				BooleanVisibilityAttribute.class);
		forPropertyType(Boolean.class).expectAttribute(
				BooleanVisibilityAttribute.class);
	}

	@Override
	protected VisibilityAttribute<View> createAttribute() {
		@SuppressWarnings("unchecked")
		VisibilityFactory<View> visibilityFactory = mock(VisibilityFactory.class);
		return new VisibilityAttribute<View>(visibilityFactory);
	}

}
