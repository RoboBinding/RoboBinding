/**
 * Copyright 2013 Cheng Wei, Robert Taylor
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

import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.robobinding.attribute.GroupAttributesBuilder.aGroupAttributes;

import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import org.robobinding.BindingContext;
import org.robobinding.attribute.CommandAttribute;
import org.robobinding.attribute.GroupAttributesBuilder;
import org.robobinding.attribute.ValueModelAttribute;

import android.view.View;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@RunWith(Theories.class)
public class ChildViewAttributesTest
{
	@DataPoints
	public static AKindOfChildViewAttributes[] childViewAttributeSamples = {
		new ChildViewAttributeTester(), new ChildViewAttributeWithAttributeTester(), new PropertyViewAttributeTester()
	};
	
	@Theory()
	@Test(expected = RuntimeException.class)
	public void givenSetupCompleted_whenAddAChildViewAttribute_thenThrowsException(AKindOfChildViewAttributes aKindOfChildViewAttributes)
	{
		ChildViewAttributes<View> childViewAttributes = createChildViewAttributesFrom(aKindOfChildViewAttributes);

		childViewAttributes.markSetupCompleted();
		
		aKindOfChildViewAttributes.addTo(childViewAttributes);
	}
	
	@Test
	public void whenPreInitializeView_thenTheMethodOfChildPropertyViewAttributeIsCalled()
	{
		PropertyViewAttributeTester aPropertyViewAttribute = new PropertyViewAttributeTester();
		ChildViewAttributes<View> childViewAttributes = createChildViewAttributesFrom(aPropertyViewAttribute);
		aPropertyViewAttribute.addTo(childViewAttributes);
		childViewAttributes.markSetupCompleted();
		
		childViewAttributes.preInitializeView(mock(BindingContext.class));
		
		aPropertyViewAttribute.verifyNumCallsToPreInitializeView(1);
	}
	
	@Test
	public void whenBindTo_thenTheMethodInEachChildViewAttributeIsCalled()
	{
		ChildViewAttributes<View> childViewAttributes = createChildViewAttributesFrom(childViewAttributeSamples);
		addToChildViewAttributes(childViewAttributes, childViewAttributeSamples);
		childViewAttributes.markSetupCompleted();
		
		childViewAttributes.bindTo(mock(BindingContext.class));
		
		childViewAttributeSamples[0].verifyNumCallsToBindTo(1);
		childViewAttributeSamples[1].verifyNumCallsToBindTo(1);
		childViewAttributeSamples[2].verifyNumCallsToBindTo(1);
	}
	
	@Test
	public void whenErrorsOccurDuringPreInitializeViewWithFailOnFirstBindingError_thenOnlyTheMethodInTheFirstChildViewAttributeIsCalled()
	{
		PropertyViewAttributeTester propertyViewAttribute1 = new PropertyViewAttributeTester();
		propertyViewAttribute1.throwsExceptionWhenPreInitializeView();
		
		PropertyViewAttributeTester propertyViewAttribute2 = new PropertyViewAttributeTester();
		
		ChildViewAttributes<View> childViewAttributes = createChildViewAttributesFrom(propertyViewAttribute1, propertyViewAttribute2);
		propertyViewAttribute1.addTo(childViewAttributes);
		propertyViewAttribute2.addTo(childViewAttributes);
		childViewAttributes.failOnFirstBindingError();
		childViewAttributes.markSetupCompleted();
		
		try
		{
			childViewAttributes.preInitializeView(mock(BindingContext.class));
			fail("expect an attributeGroupBindingException is thrown");
		}catch(AttributeGroupBindingException e)
		{
			propertyViewAttribute1.verifyNumCallsToPreInitializeView(1);
			propertyViewAttribute2.verifyNumCallsToPreInitializeView(0);
		}
	}
	
	@Test
	public void whenErrorsOccurDuringPreInitializeView_thenAllTheMethodInEachChildViewAttributeIsCalled()
	{
		PropertyViewAttributeTester propertyViewAttribute1 = new PropertyViewAttributeTester();
		propertyViewAttribute1.throwsExceptionWhenPreInitializeView();
		
		PropertyViewAttributeTester propertyViewAttribute2 = new PropertyViewAttributeTester();
		propertyViewAttribute2.throwsExceptionWhenPreInitializeView();
		
		ChildViewAttributes<View> childViewAttributes = createChildViewAttributesFrom(propertyViewAttribute1, propertyViewAttribute2);
		propertyViewAttribute1.addTo(childViewAttributes);
		propertyViewAttribute2.addTo(childViewAttributes);
		childViewAttributes.markSetupCompleted();
		
		try
		{
			childViewAttributes.preInitializeView(mock(BindingContext.class));
			fail("expect an attributeGroupBindingException is thrown");
		}catch(AttributeGroupBindingException e)
		{
			propertyViewAttribute1.verifyNumCallsToPreInitializeView(1);
			propertyViewAttribute1.verifyNumCallsToPreInitializeView(1);
		}
	}
	
	@Test
	public void whenErrorsOccurDuringBindToWithFailOnFirstBindingError_thenOnlyTheMethodInTheFirstChildViewAttributeIsCalled()
	{
		PropertyViewAttributeTester propertyViewAttribute1 = new PropertyViewAttributeTester();
		propertyViewAttribute1.throwsExceptionWhenBindTo();
		
		PropertyViewAttributeTester propertyViewAttribute2 = new PropertyViewAttributeTester();
		
		ChildViewAttributes<View> childViewAttributes = createChildViewAttributesFrom(propertyViewAttribute1, propertyViewAttribute2);
		propertyViewAttribute1.addTo(childViewAttributes);
		propertyViewAttribute2.addTo(childViewAttributes);
		childViewAttributes.failOnFirstBindingError();
		childViewAttributes.markSetupCompleted();
		
		try
		{
			childViewAttributes.bindTo(mock(BindingContext.class));
			fail("expect an attributeGroupBindingException is thrown");
		}catch(AttributeGroupBindingException e)
		{
			propertyViewAttribute1.verifyNumCallsToBindTo(1);
			propertyViewAttribute2.verifyNumCallsToBindTo(0);
		}
	}
	
	@Test
	public void whenErrorsOccurDuringBindTo_thenAllTheMethodInEachChildViewAttributeIsCalled()
	{
		PropertyViewAttributeTester propertyViewAttribute1 = new PropertyViewAttributeTester();
		propertyViewAttribute1.throwsExceptionWhenBindTo();
		
		PropertyViewAttributeTester propertyViewAttribute2 = new PropertyViewAttributeTester();
		propertyViewAttribute2.throwsExceptionWhenBindTo();
		
		ChildViewAttributes<View> childViewAttributes = createChildViewAttributesFrom(propertyViewAttribute1, propertyViewAttribute2);
		propertyViewAttribute1.addTo(childViewAttributes);
		propertyViewAttribute2.addTo(childViewAttributes);
		childViewAttributes.markSetupCompleted();
		
		try
		{
			childViewAttributes.bindTo(mock(BindingContext.class));
			fail("expect an attributeGroupBindingException is thrown");
		}catch(AttributeGroupBindingException e)
		{
			propertyViewAttribute1.verifyNumCallsToBindTo(1);
			propertyViewAttribute1.verifyNumCallsToBindTo(1);
		}
	}
	
	private static int childViewAttributeCounter = 0;
	private static String nextChildViewAttributeName()
	{
		return "child"+childViewAttributeCounter++;
	}
	
	private ChildViewAttributes<View> createChildViewAttributesFrom(AKindOfChildViewAttributes... childViewAttributeCollection)
	{
		GroupAttributesBuilder groupAttributesBuilder = aGroupAttributes();
		for(AKindOfChildViewAttributes aKindOfChildViewAttributes : childViewAttributeCollection)
		{
			aKindOfChildViewAttributes.updateGroupAttributesBuilder(groupAttributesBuilder);
		}
		return new ChildViewAttributes<View>(groupAttributesBuilder.build(), new DummyViewAttributeInitializer());
	}

	private void addToChildViewAttributes(ChildViewAttributes<View> childViewAttributes, AKindOfChildViewAttributes[] childViewAttributeCollection)
	{
		for(AKindOfChildViewAttributes aKindOfChildViewAttributes : childViewAttributeCollection)
		{
			aKindOfChildViewAttributes.addTo(childViewAttributes);
		}
	}

	private static class DummyViewAttributeInitializer extends AbstractViewAttributeInitializer
	{

		protected DummyViewAttributeInitializer()
		{
			super(mock(ViewListenersProvider.class));
		}

		@Override
		protected View getView()
		{
			return null;
		}

		@Override
		public <ViewType extends View, PropertyViewAttributeType extends PropertyViewAttribute<ViewType>> PropertyViewAttributeType initializePropertyViewAttribute(
				PropertyViewAttributeType propertyViewAttribute, ValueModelAttribute attribute)
		{
			return propertyViewAttribute;
		}

		@Override
		public <ViewType extends View, CommandViewAttributeType extends AbstractCommandViewAttribute<ViewType>> CommandViewAttributeType initializeCommandViewAttribute(
				CommandViewAttributeType viewAttribute, CommandAttribute attribute)
		{
			return viewAttribute;
		}
		
	}
	
	private static interface AKindOfChildViewAttributes
	{
		void updateGroupAttributesBuilder(GroupAttributesBuilder groupAttributesBuilder);
		void addTo(ChildViewAttributes<View> childViewAttributes);
		void verifyNumCallsToBindTo(int expectedNumCalls);
	}
	
	private static class ChildViewAttributeTester implements AKindOfChildViewAttributes
	{
		private ChildViewAttribute childViewAttribute;
		
		public ChildViewAttributeTester()
		{
			childViewAttribute = mock(ChildViewAttribute.class);
		}		

		@Override
		public void updateGroupAttributesBuilder(GroupAttributesBuilder groupAttributesBuilder)
		{
		}
		
		@Override
		public void addTo(ChildViewAttributes<View> childViewAttributes)
		{
			childViewAttributes.add(nextChildViewAttributeName(), childViewAttribute);
		}

		@Override
		public void verifyNumCallsToBindTo(int expectedNumCalls)
		{
			verify(childViewAttribute, times(expectedNumCalls)).bindTo(any(BindingContext.class));
		}
	}
	
	private static class ChildViewAttributeWithAttributeTester implements AKindOfChildViewAttributes
	{
		private ChildViewAttributeWithAttribute<ValueModelAttribute> childViewAttribute;
		private ValueModelAttribute attribute;

		@SuppressWarnings("unchecked")
		public ChildViewAttributeWithAttributeTester()
		{
			childViewAttribute = mock(ChildViewAttributeWithAttribute.class);
			attribute = new ValueModelAttribute(nextChildViewAttributeName(), "value");
		}
		
		@Override
		public void updateGroupAttributesBuilder(GroupAttributesBuilder groupAttributesBuilder)
		{
			groupAttributesBuilder.withChildAttributeResolution(attribute);
		}
		
		@Override
		public void addTo(ChildViewAttributes<View> childViewAttributes)
		{
			childViewAttributes.add(attribute.getName(), childViewAttribute);
		}
		
		@Override
		public void verifyNumCallsToBindTo(int expectedNumCalls)
		{
			verify(childViewAttribute, times(expectedNumCalls)).bindTo(any(BindingContext.class));
		}
	}
	
	private static class PropertyViewAttributeTester implements AKindOfChildViewAttributes
	{
		private PropertyViewAttribute<View> childViewAttribute;
		
		@SuppressWarnings("unchecked")
		public PropertyViewAttributeTester()
		{
			childViewAttribute = mock(PropertyViewAttribute.class);
		}

		@Override
		public void updateGroupAttributesBuilder(GroupAttributesBuilder groupAttributesBuilder)
		{
		}

		@Override
		public void addTo(ChildViewAttributes<View> childViewAttributes)
		{
			childViewAttributes.add(nextChildViewAttributeName(), childViewAttribute);
		}

		public void throwsExceptionWhenPreInitializeView()
		{
			doThrow(new RuntimeException("an error")).when(childViewAttribute).preInitializeView(any(BindingContext.class));
		}
		
		public void throwsExceptionWhenBindTo()
		{
			doThrow(new RuntimeException("an error")).when(childViewAttribute).bindTo(any(BindingContext.class));
		}
		
		public void verifyNumCallsToPreInitializeView(int expectedNumCalls)
		{
			verify(childViewAttribute, times(expectedNumCalls)).preInitializeView(any(BindingContext.class));
		}

		@Override
		public void verifyNumCallsToBindTo(int expectedNumCalls)
		{
			verify(childViewAttribute, times(expectedNumCalls)).bindTo(any(BindingContext.class));
		}
	}
}
