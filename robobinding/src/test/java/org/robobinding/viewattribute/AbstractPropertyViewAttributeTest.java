package org.robobinding.viewattribute;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.robobinding.viewattribute.BindingAttributeValues.DEFAULT_PROPERTY_NAME;
import static org.robobinding.viewattribute.MockPropertyViewAttributeConfigBuilder.aPropertyViewAttributeConfig;

import java.lang.reflect.ParameterizedType;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.robobinding.BindingContext;
import org.robobinding.property.ValueModel;
import org.robobinding.property.ValueModelUtils;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.xtremelabs.robolectric.RobolectricTestRunner;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
@RunWith(RobolectricTestRunner.class)
@SuppressWarnings({ "unchecked", "rawtypes" })
public abstract class AbstractPropertyViewAttributeTest<ViewType extends View,
	PropertyViewAttributeType extends PropertyViewAttribute<? super ViewType>> {
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


	initializeAttributeWithDefaultConfig();
    }

    protected PropertyViewAttributeType createAttribute() {
	ParameterizedType superclass = (ParameterizedType) getClass().getGenericSuperclass();
	try {
	    return ParameterizedTypeUtils.createTypeArgument(superclass, 1);
	} catch (Exception e) {
	    throw new RuntimeException("Error instantiating attribute: " + e.getMessage());
	}
    }

    private void initializeAttributeWithDefaultConfig() {
	viewAttribute().initialize(aPropertyViewAttributeConfig(view, BindingAttributeValues.ONE_WAY_BINDING_DEFAULT_PROPERTY_NAME));
    }

    protected <PropertyType> ValueModel<PropertyType> twoWayBindToProperty(Class<PropertyType> propertyClass) {
	viewAttribute().initialize(aPropertyViewAttributeConfig(view, BindingAttributeValues.TWO_WAY_BINDING_DEFAULT_PROPERTY_NAME));
	BindingContext bindingContext = mock(BindingContext.class);
	when(bindingContext.getPropertyType(DEFAULT_PROPERTY_NAME)).thenReturn((Class) propertyClass);
	when(bindingContext.getPropertyValueModel(DEFAULT_PROPERTY_NAME)).thenReturn((ValueModel) ValueModelUtils.create());
	attribute.bindTo(bindingContext);
	return bindingContext.getPropertyValueModel(DEFAULT_PROPERTY_NAME);
    }

    protected <PropertyType> ValueModel<PropertyType> twoWayBindToProperty(Class<PropertyType> propertyClass, PropertyType initialPropertyValue) {
	viewAttribute().initialize(aPropertyViewAttributeConfig(view, BindingAttributeValues.TWO_WAY_BINDING_DEFAULT_PROPERTY_NAME));
	BindingContext bindingContext = mock(BindingContext.class);
	when(bindingContext.getPropertyType(DEFAULT_PROPERTY_NAME)).thenReturn((Class) propertyClass);
	when(bindingContext.getPropertyValueModel(DEFAULT_PROPERTY_NAME)).thenReturn((ValueModel) ValueModelUtils.create(initialPropertyValue));
	attribute.bindTo(bindingContext);
	return bindingContext.getPropertyValueModel(DEFAULT_PROPERTY_NAME);
    }

    private <PropertyType> AbstractPropertyViewAttribute<ViewType, PropertyType> viewAttribute() {
	return (AbstractPropertyViewAttribute<ViewType, PropertyType>) attribute;
    }

}
