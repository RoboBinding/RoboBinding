package org.robobinding.widget;

import java.lang.reflect.ParameterizedType;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.robobinding.viewattribute.property.PropertyViewAttribute;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import android.content.Context;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
@RunWith(RobolectricTestRunner.class)
public abstract class AbstractPropertyViewAttributeTest<ViewType, PropertyViewAttributeType extends PropertyViewAttribute<? super ViewType, ?>> {
	protected ViewType view;
	protected PropertyViewAttributeType attribute;

	@Before
	public void initializeViewAndAttribute() {
		ParameterizedType superclass = (ParameterizedType) getClass()
				.getGenericSuperclass();

		try {
			view = ParameterizedTypeUtils.createTypeArgument(superclass, 0,
					Context.class, Robolectric.application);
		} catch (Exception e) {
			throw new RuntimeException("Error instantiating view: "
					+ e.getMessage());
		}

		attribute = createAttribute();
	}

	protected PropertyViewAttributeType createAttribute() {
		ParameterizedType superclass = (ParameterizedType) getClass()
				.getGenericSuperclass();
		try {
			return ParameterizedTypeUtils.createTypeArgument(superclass, 1);
		} catch (Exception e) {
			throw new RuntimeException("Error instantiating attribute: "
					+ e.getMessage());
		}
	}
}
