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

import static org.junit.Assert.assertNotNull;

import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.Map;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.robobinding.BindingContext;
import org.robobinding.MockBindingContext;
import org.robobinding.attribute.PendingGroupAttributes;
import org.robobinding.viewattribute.AbstractGroupedViewAttribute.ChildAttributeBindings;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.google.common.collect.Maps;
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
	private Map<String, String> presentAttributeMappings;
	private Collection<ViewAttribute> childAttributes;

	@Before
	@SuppressWarnings("unchecked")
	public void initialize()
	{
		AbstractGroupedViewAttribute<View> attributeUnderTest = instantiateAttributeUnderTest();
		instantiateView(attributeUnderTest);
		this.attributeUnderTest = (T) attributeUnderTest;
		presentAttributeMappings = Maps.newHashMap();
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
		PendingGroupAttributes groupedAttributeDecriptor = new PendingGroupAttributes(presentAttributeMappings);
		attributeUnderTest.resolvePendingGroupAttributes(groupedAttributeDecriptor);
		setupChildAttributes();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void setupChildAttributes()
	{
		AttributeGroupBindingException bindingErrors = new AttributeGroupBindingException();
		
		BindingContext bindingContext = MockBindingContext.create();
		attributeUnderTest.preBind(bindingContext);
		
		ChildAttributeBindings binding = attributeUnderTest.new ChildAttributeBindings(bindingContext, bindingErrors);
		attributeUnderTest.setupChildAttributeBindings(binding);
		
		bindingErrors.assertNoErrors();
		
		childAttributes = binding.childAttributeMap.values();
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
		presentAttributeMappings.put(attribute.name, attribute.value);
	}

	protected void assertThatAttributesWereCreated(Class<?>... attributeClasses)
	{
		for (Class<?> attributeClass : attributeClasses)
			assertThatAttributeWasCreated(attributeClass);
	}

	protected void assertThatAttributeWasCreated(Class<?> attributeClass)
	{
		Object childAttribute = findChildAttributeOfType(attributeClass);
		assertNotNull("Child attribute of type '"+attributeClass.getName()+" not found", childAttribute);
		if(childAttribute instanceof ViewAttribute)
		{
			assertViewAttributeProperlyInitialized((ViewAttribute)childAttribute);
		}
	}
	
	private Object findChildAttributeOfType(Class<?> attributeClass)
	{
		for (Object childAttribute : childAttributes)
		{
			if (attributeClass.isInstance(childAttribute))
			{
				return childAttribute;
			}
		}
		
		return null;
	}

	private void assertViewAttributeProperlyInitialized(ViewAttribute childAttribute)
	{
		ViewAttributeValidation validation = new ViewAttributeValidation();
		if (childAttribute instanceof AbstractPropertyViewAttribute)
		{
			AbstractPropertyViewAttribute<?, ?> propertyViewAttribute = (AbstractPropertyViewAttribute<?, ?>) childAttribute;
			propertyViewAttribute.validate(validation);
		} else if (childAttribute instanceof AbstractCommandViewAttribute)
		{
			AbstractCommandViewAttribute<?> commandViewAttribute = (AbstractCommandViewAttribute<?>) childAttribute;
			commandViewAttribute.validate(validation);
		}
		validation.assertNoErrors();
	}

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