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
package robobinding.binding.viewattribute.provider;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import robobinding.binding.BindingAttribute;
import robobinding.binding.ViewAttribute;
import android.view.View;

import com.google.common.collect.Lists;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public abstract class AbstractAttributeProviderTest<T extends View>
{
	private static final String ATTRIBUTE_VALUE = "{attributeValue}";
	private AbstractBindingAttributeProvider<T> bindingAttributeProvider;
	private T view;
	
	@Before
	public void setUp()
	{
		bindingAttributeProvider = getBindingAttributeProvider();
		view = getView();
	}

	@Test
	public void givenEachAttributeName_ThenReturnABindingAttributeContainingExpectedViewAttribute()
	{
		List<AttributeClassMapping> attributeClassMappings = Lists.newArrayList();
		populateAttributeClassMappings(attributeClassMappings);
		
		for (AttributeClassMapping attributeClassMapping : attributeClassMappings)
		{
			BindingAttribute bindingAttribute = bindingAttributeProvider.getSupportedBindingAttribute(view, attributeClassMapping.attributeName, ATTRIBUTE_VALUE);
			
			assertThat(bindingAttribute.getViewAttribute(), instanceOf(attributeClassMapping.expectedBindingAttributeClass));
		}
	}
	
	@Test
	public void givenAnyOtherAttributeName_ThenReturnNull()
	{
		String attributeName = "something_else";
		
		BindingAttribute bindingAttribute = bindingAttributeProvider.getSupportedBindingAttribute(view, attributeName, ATTRIBUTE_VALUE);
		
		assertNull(bindingAttribute);
	}
	
	protected abstract AbstractBindingAttributeProvider<T> getBindingAttributeProvider();
	protected abstract T getView();
	protected abstract void populateAttributeClassMappings(List<AttributeClassMapping> attributeClassMappings);
	
	public static class AttributeClassMapping
	{
		private String attributeName;
		private Class<? extends ViewAttribute> expectedBindingAttributeClass;
		protected AttributeClassMapping(String attributeName, Class<? extends ViewAttribute> expectedBindingAttributeClass)
		{
			this.attributeName = attributeName;
			this.expectedBindingAttributeClass = expectedBindingAttributeClass;
		}
	}
}
