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

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;

import android.view.View;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
@SuppressWarnings("unchecked")
public class ViewAttributeInstantiatorTest
{
	private ViewAttributeInstantiator viewAttributeInjector;

	@Before
	public void setUp()
	{
		viewAttributeInjector = new ViewAttributeInstantiator();
	}

	@Test
	public void whenInvokingOnPropertyViewAttribute_thenInjectAllPropertiesIntoPropertyViewAttribute()
	{
		PropertyViewAttribute<View> propertyViewAttribute = mock(PropertyViewAttribute.class);
		View view = mock(View.class);
		String attributeValue = "{some_property}";
		boolean preInitializeViews = RandomValues.trueOrFalse();

		viewAttributeInjector.injectPropertyAttributeValues(propertyViewAttribute, view, attributeValue, preInitializeViews);

		verify(propertyViewAttribute).setView(view);
		verify(propertyViewAttribute).setAttributeValue(attributeValue);
		verify(propertyViewAttribute).setPreInitializeView(preInitializeViews);
	}

	@Test
	public void whenInvokingOnCommandViewAttribute_thenInjectAllPropertiesIntoCommandViewAttribute()
	{
		AbstractCommandViewAttribute<View> commandViewAttribute = mock(AbstractCommandViewAttribute.class);
		View view = mock(View.class);
		String commandName = "save";

		viewAttributeInjector.injectCommandAttributeValues(commandViewAttribute, view, commandName);

		verify(commandViewAttribute).setView(view);
		verify(commandViewAttribute).setCommandName(commandName);
	}

	@Test
	public void whenInvokingOnGroupViewAttribute_thenInjectAllPropertiesIntoCommandViewAttribute()
	{
		AbstractGroupedViewAttribute<View> groupedViewAttribute = mock(AbstractGroupedViewAttribute.class);
		View view = mock(View.class);
		GroupedAttributeDetails groupedAttributeDetails = mock(GroupedAttributeDetails.class);
		boolean preInitializeViews = RandomValues.trueOrFalse();

		viewAttributeInjector.injectGroupedAttributeValues(groupedViewAttribute, view, preInitializeViews, groupedAttributeDetails);

		verify(groupedViewAttribute).setView(view);
		verify(groupedViewAttribute).setPreInitializeViews(preInitializeViews);
		verify(groupedViewAttribute).setGroupedAttributeDetails(groupedAttributeDetails);
	}
}
