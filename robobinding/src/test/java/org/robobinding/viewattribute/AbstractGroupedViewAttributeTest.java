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

import static org.junit.Assert.assertTrue;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.robobinding.viewattribute.AbstractCommandViewAttribute;
import org.robobinding.viewattribute.AbstractGroupedViewAttribute;
import org.robobinding.viewattribute.AbstractPropertyViewAttribute;
import org.robobinding.viewattribute.impl.GroupedAttributeDetailsImpl;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.xtremelabs.robolectric.RobolectricTestRunner;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @param <S>
 */
@RunWith(RobolectricTestRunner.class)
public abstract class AbstractGroupedViewAttributeTest<T extends AbstractGroupedViewAttribute<?>>
{
	protected T attributeUnderTest;
	protected GroupedAttributeDetailsImpl mockGroupedAttributeDetails;

	@Before
	@SuppressWarnings("unchecked")
	public void initialize()
	{
		AbstractGroupedViewAttribute<View> attributeUnderTest = instantiateAttributeUnderTest();
		instantiateView(attributeUnderTest);
		this.attributeUnderTest = (T) attributeUnderTest;
		mockGroupedAttributeDetails = new GroupedAttributeDetailsImpl(null);
	}

	private AbstractGroupedViewAttribute<View> instantiateAttributeUnderTest()
	{
		try
		{
			ParameterizedType attributeType = (ParameterizedType) getClass().getGenericSuperclass();
			return ParameterizedTypeUtils.createTypeArgument(attributeType, 0);
		} catch (Exception e)
		{
			throw new RuntimeException("Error instantiating grouped attribute class: " + e.getMessage());
		}
	}

	private void instantiateView(AbstractGroupedViewAttribute<View> attributeUnderTest)
	{
		try
		{
			View view;
			Activity context = new Activity();

			if (overrideViewClass() == null)
			{
				ParameterizedType viewType = (ParameterizedType) attributeUnderTest.getClass().getGenericSuperclass();
				view = ParameterizedTypeUtils.createTypeArgument(viewType, 0, Context.class, context);
			} else
			{
				view = overrideViewClass().getConstructor(Context.class).newInstance(context);
			}

			attributeUnderTest.setView(view);
		} catch (Exception e)
		{
			throw new RuntimeException("Error instantiating view: " + e.getMessage());
		}
	}

	protected Class<? extends View> overrideViewClass()
	{
		return null;
	}

	protected void performInitialization()
	{
		attributeUnderTest.setGroupedAttributeDetails(mockGroupedAttributeDetails);
		attributeUnderTest.postInitialization();
	}

	protected Attribute attribute(String attribute)
	{
		if (attribute.indexOf('=') == -1)
			throw new IllegalArgumentException("Expected attribute in the form '[attributeName]=[attributeValue]'");

		String[] components = attribute.split("=");
		return new Attribute(components[0], components[1]);
	}

	protected void givenAttributes(Attribute... attributes)
	{
		for (Attribute attribute : attributes)
		{
			givenAttribute(attribute);
		}
	}

	protected void givenAttribute(Attribute attribute)
	{
		mockGroupedAttributeDetails.addPresentAttribute(attribute.name, attribute.value);
	}

	protected void assertThatAttributesWereCreated(Class<?>... attributeClasses)
	{
		for (Class<?> attributeClass : attributeClasses)
			assertThatAttributeWasCreated(attributeClass);
	}

	protected void assertThatAttributeWasCreated(Class<?> attributeClass)
	{
		List<?> childAttributes = getGeneratedChildAttributes(attributeUnderTest);
		boolean instanceFound = false;

		for (Object childAttribute : childAttributes)
		{
			if (childAttribute.getClass().isAssignableFrom(attributeClass))
			{
				instanceFound = true;

				if (childAttribute instanceof AbstractPropertyViewAttribute)
				{
					AbstractPropertyViewAttribute<?, ?> propertyViewAttribute = (AbstractPropertyViewAttribute<?, ?>) childAttribute;
					assertTrue(propertyViewAttribute.getValidationError(), propertyViewAttribute.validate());
				} else if (childAttribute instanceof AbstractCommandViewAttribute)
				{
					AbstractCommandViewAttribute<?> commandViewAttribute = (AbstractCommandViewAttribute<?>) childAttribute;
					assertTrue(commandViewAttribute.getValidationError(), commandViewAttribute.validate());
				}

				break;
			}
		}

		assertTrue(instanceFound);
	}

	protected abstract List<?> getGeneratedChildAttributes(T attributeUnderTest);

	public static class Attribute
	{
		private final String name;
		private final String value;

		private Attribute(String name, String value)
		{
			this.name = name;
			this.value = value;
		}

		@Override
		public String toString()
		{
			return name;
		}
	}
}