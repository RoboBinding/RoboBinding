/**
 * Copyright 2012 Cheng Wei, Robert Taylor
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions
 * and limitations under the License.
 */
package org.robobinding.viewattribute;

import java.lang.reflect.ParameterizedType;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.robobinding.property.ValueModel;

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
public abstract class AbstractPropertyViewAttributeTest<ViewType extends View, PropertyViewAttributeType extends PropertyViewAttribute<? super ViewType>>
{
	protected ViewType view;
	protected PropertyViewAttributeType attribute;
	protected Context context;
	
	@Before
	public void initializeViewAndAttribute()
	{
		context = new Activity();
		ParameterizedType superclass = (ParameterizedType)getClass().getGenericSuperclass();
		
		try
		{
			view = ParameterizedTypeUtils.createTypeArgument(superclass, 0, Context.class, new Activity());
		} 
		catch (Exception e)
		{
			throw new RuntimeException("Error instantiating view: " + e.getMessage());
		}
		
		try
		{
			attribute = ParameterizedTypeUtils.createTypeArgument(superclass, 1);
			attribute.setView(view);
		}
		catch (Exception e)
		{
			throw new RuntimeException("Error instantiating attribute: " + e.getMessage());
		}
	}
	
	protected <PropertyType> ValueModel<PropertyType> twoWayBindToProperty(Class<PropertyType> propertyClass)
	{
		attribute.setAttributeValue(MockPresentationModelForProperty.TWO_WAY_BINDING_PROPERTY_NAME);
		MockPresentationModelForProperty<PropertyType> mockPresentationModelForProperty = MockPresentationModelForProperty.bindToProperty(attribute, propertyClass);
		return mockPresentationModelForProperty.getPropertyValueModel();
	}
	
	protected <PropertyType> ValueModel<PropertyType> twoWayBindToProperty(Class<PropertyType> propertyClass, PropertyType initialValue)
	{
		ValueModel<PropertyType> valueModel = twoWayBindToProperty(propertyClass);
		valueModel.setValue(initialValue);
		return valueModel;
	}
}
