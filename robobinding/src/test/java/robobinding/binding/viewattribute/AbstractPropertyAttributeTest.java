/**
 * Copyright 2011 Cheng Wei, Robert Taylor
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
package robobinding.binding.viewattribute;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Random;

import robobinding.beans.PresentationModelAdapter;
import robobinding.value.ValueModel;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public abstract class AbstractPropertyAttributeTest<T>
{
	protected static final String PROPERTY_NAME = "property_name";
	
	protected ValueModel<T> valueModel;
	protected PresentationModelAdapter presentationModelAdapter;
	
	protected void createAttributeWithEither1WayOr2WayBinding()
	{
		if (new Random().nextInt(2) == 0)
			createAttributeWith1WayBinding();
		else
			createAttributeWith2WayBinding();
	}
	
	protected void createAttributeWith1WayBinding()
	{
		this.valueModel = initialValueModelInstance();
		mockPresentationModelFor1WayBinding();
		createAndBindAttribute(BindingType.ONE_WAY);
	}

	protected void createAttributeWith2WayBinding()
	{
		this.valueModel = initialValueModelInstance();
		mockPresentationModelFor2WayBinding();
		createAndBindAttribute(BindingType.TWO_WAY);
	}
	
	protected void updateValueModel(T value)
	{
		valueModel.setValue(value);
	}
	
	private void createAndBindAttribute(BindingType bindingType)
	{
		String bindingAttributeValue = getBindingAttributeValue(bindingType);
		AbstractPropertyViewAttribute<T> propertyViewAttribute = newAttributeInstance(bindingAttributeValue );
		propertyViewAttribute.setPresentationModelAdapter(presentationModelAdapter);
		
		if (bindingType == BindingType.ONE_WAY)
			propertyViewAttribute.performOneWayBinding();
		else
			propertyViewAttribute.performTwoWayBinding();
	}

	private String getBindingAttributeValue(BindingType bindingType)
	{
		String bindingAttributeValuePrefix = bindingType == BindingType.ONE_WAY ? "{" : "${";
		String bindingAttributeValue = bindingAttributeValuePrefix + PROPERTY_NAME + "}";
		return bindingAttributeValue;
	}
	
	private void mockPresentationModelFor1WayBinding()
	{
		presentationModelAdapter = mock(PresentationModelAdapter.class);
		when(presentationModelAdapter.<T>getReadOnlyPropertyValueModel(PROPERTY_NAME)).thenReturn(valueModel);
	}
	
	private void mockPresentationModelFor2WayBinding()
	{
		presentationModelAdapter = mock(PresentationModelAdapter.class);
		when(presentationModelAdapter.<T>getPropertyValueModel(PROPERTY_NAME)).thenReturn(valueModel);
	}
	
	protected abstract AbstractPropertyViewAttribute<T> newAttributeInstance(String bindingAttributeValue);

	protected abstract ValueModel<T> initialValueModelInstance();
}
