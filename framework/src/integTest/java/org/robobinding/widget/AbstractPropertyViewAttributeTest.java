package org.robobinding.widget;

import java.lang.reflect.ParameterizedType;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.robobinding.viewattribute.ParameterizedTypeUtils;
import org.robobinding.viewattribute.property.PropertyViewAttribute;

import android.app.Activity;
import android.content.Context;

import com.xtremelabs.robolectric.RobolectricTestRunner;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
@RunWith(RobolectricTestRunner.class)
public abstract class AbstractPropertyViewAttributeTest<ViewType,
	PropertyViewAttributeType extends PropertyViewAttribute<? super ViewType, ?>> {
    protected ViewType view;
    protected PropertyViewAttributeType attribute;

    @Before
    public void initializeViewAndAttribute() {
	ParameterizedType superclass = (ParameterizedType) getClass().getGenericSuperclass();

	try {
	    view = ParameterizedTypeUtils.createTypeArgument(superclass, 0, Context.class, new Activity());
	} catch (Exception e) {
	    throw new RuntimeException("Error instantiating view: " + e.getMessage());
	}

	attribute = createAttribute();
    }

    protected PropertyViewAttributeType createAttribute() {
	ParameterizedType superclass = (ParameterizedType) getClass().getGenericSuperclass();
	try {
	    return ParameterizedTypeUtils.createTypeArgument(superclass, 1);
	} catch (Exception e) {
	    throw new RuntimeException("Error instantiating attribute: " + e.getMessage());
	}
    }
}
