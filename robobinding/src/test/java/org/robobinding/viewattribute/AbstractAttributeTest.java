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

import java.util.Random;

import org.junit.Before;
import org.junit.runner.RunWith;

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
public class AbstractAttributeTest<S extends View, T extends PropertyViewAttribute<? super S>> extends ViewAndAttributeReference<S, T>
{
	protected S view;
	protected T attribute;
	protected Context context;
	
	@Before
	public void initializeViewAndAttribute()
	{
		context = new Activity();
		
		try
		{
			view = newViewInstance();
		} 
		catch (Exception e)
		{
			throw new RuntimeException("Error instantiating view: " + e.getMessage());
		}
		
		try
		{
			attribute = newAttributeInstance();
			attribute.setView(view);
		}
		catch (Exception e)
		{
			throw new RuntimeException("Error instantiating attribute: " + e.getMessage());
		}
	}
	
	protected <C> MockPresentationModelForProperty<C> initializeForOneWayBinding(Class<C> propertyClass)
	{
		attribute.setPropertyBindingDetails(PropertyBindingDetails.createFrom(MockPresentationModelForProperty.ONE_WAY_BINDING_PROPERTY_NAME));
		return MockPresentationModelForProperty.bindToProperty(attribute, propertyClass);
	}
	
	protected <C> MockPresentationModelForProperty<C> initializeForTwoWayBinding(Class<C> propertyClass)
	{
		attribute.setPropertyBindingDetails(PropertyBindingDetails.createFrom(MockPresentationModelForProperty.TWO_WAY_BINDING_PROPERTY_NAME));
		return MockPresentationModelForProperty.bindToProperty(attribute, propertyClass);
	}
	
	protected boolean trueOrFalse()
	{
		return new Random().nextBoolean();
	}
	
	protected int anyInteger()
	{
		return new Random().nextInt(100);
	}
	
	protected Class<Integer> primitiveOrBoxedIntegerClass()
	{
		if (new Random().nextInt(2) == 0)
			return int.class;
		
		return Integer.class;
	}
	
	protected Class<Boolean> primitiveOrBoxedBooleanClass()
	{
		if (new Random().nextInt(2) == 0)
			return boolean.class;
		
		return Boolean.class;
	}
}
