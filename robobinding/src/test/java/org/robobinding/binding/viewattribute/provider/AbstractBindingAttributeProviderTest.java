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
package org.robobinding.binding.viewattribute.provider;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robobinding.binder.BindingAttribute;
import org.robobinding.binder.BindingAttributeResolver;
import org.robobinding.internal.com_google_common.collect.Lists;
import org.robobinding.internal.com_google_common.collect.Maps;
import org.robobinding.viewattribute.BindingAttributeProvider;
import org.robobinding.viewattribute.ViewAttribute;

import android.view.View;

import com.xtremelabs.robolectric.RobolectricTestRunner;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
@RunWith(RobolectricTestRunner.class)
public abstract class AbstractBindingAttributeProviderTest<T extends View>
{
	private static final String ATTRIBUTE_VALUE = "{attributeValue}";
	private BindingAttributeProvider<T> bindingAttributeProvider;
	private T view;
	
	@Before
	public void setUp()
	{
		bindingAttributeProvider = getBindingAttributeProvider();
		view = createNewViewInstance();
	}

	@Test
	public void givenEachAttributeName_ThenReturnABindingAttributeContainingExpectedViewAttribute()
	{
		AttributeClassMappings attributeClassMappings = new AttributeClassMappings();
		populateAttributeClassMappings(attributeClassMappings);
		
		for (AttributeClassMapping attributeClassMapping : attributeClassMappings.mappingsList)
		{
			BindingAttribute bindingAttribute = getResolvedBindingAttribute(attributeClassMapping.attributeName);
			
			assertNotNull("Attribute support not implemented for " + attributeClassMapping.attributeName, bindingAttribute);
			assertThat(bindingAttribute.getViewAttribute(), instanceOf(attributeClassMapping.expectedBindingAttributeClass));
		}
	}

	@Test
	public void givenAnyOtherAttributeName_ThenReturnNull()
	{
		String attributeName = "something_else";
		
		List<BindingAttribute> bindingAttributes = getResolvedBindingAttributes(attributeName);
		
		assertTrue(bindingAttributes.size() == 0);
	}

	private List<BindingAttribute> getResolvedBindingAttributes(String attributeName)
	{
		BindingAttributeResolver bindingAttributeResolver = initialiseBindingAttributeResolverFromAttributeName(attributeName);
		bindingAttributeProvider.resolveSupportedBindingAttributes(view, bindingAttributeResolver, true);
		return bindingAttributeResolver.getResolvedBindingAttributes();
	}
	
	private BindingAttribute getResolvedBindingAttribute(String attributeName)
	{
		return getResolvedBindingAttributes(attributeName).get(0);
	}
	
	private BindingAttributeResolver initialiseBindingAttributeResolverFromAttributeName(String attributeName)
	{
		Map<String, String> pendingBindingAttributes = Maps.newHashMap();
		pendingBindingAttributes.put(attributeName, ATTRIBUTE_VALUE);
		BindingAttributeResolver bindingAttributeResolver = new BindingAttributeResolver(pendingBindingAttributes);
		return bindingAttributeResolver;
	}
	
	protected abstract BindingAttributeProvider<T> getBindingAttributeProvider();
	protected abstract T createNewViewInstance();
	protected abstract void populateAttributeClassMappings(AttributeClassMappings attributeClassMappings);
	
	static class AttributeClassMappings
	{
		private List<AttributeClassMapping> mappingsList = Lists.newArrayList();
		
		void add(String attributeName, Class<? extends ViewAttribute> expectedBindingAttributeClass)
		{
			mappingsList.add(new AttributeClassMapping(attributeName, expectedBindingAttributeClass));
		}
	}
	
	private static class AttributeClassMapping
	{
		private String attributeName;
		private Class<? extends ViewAttribute> expectedBindingAttributeClass;
		private AttributeClassMapping(String attributeName, Class<? extends ViewAttribute> expectedBindingAttributeClass)
		{
			this.attributeName = attributeName;
			this.expectedBindingAttributeClass = expectedBindingAttributeClass;
		}
	}
}
