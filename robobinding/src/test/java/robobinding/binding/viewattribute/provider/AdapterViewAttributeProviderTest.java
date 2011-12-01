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
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import robobinding.binding.BindingAttribute;
import robobinding.binding.viewattribute.AdaptedDataSetAttributes;
import robobinding.binding.viewattribute.AdapterViewAttribute;
import robobinding.binding.viewattribute.DropdownLayoutAttribute;
import robobinding.binding.viewattribute.DropdownMappingAttribute;
import robobinding.binding.viewattribute.ItemMappingAttribute;
import robobinding.binding.viewattribute.OnItemClickAttribute;
import robobinding.internal.com_google_common.collect.Maps;
import android.app.Activity;
import android.content.Context;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;

import com.xtremelabs.robolectric.RobolectricTestRunner;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
@RunWith(RobolectricTestRunner.class)
public class AdapterViewAttributeProviderTest
{
	private final Context context = new Activity();
	private final ListView listView = new ListView(context);
	private final Spinner spinner = new Spinner(context);
	private final Attribute source = new Attribute("source", "{sourceProperty}");
	private final Attribute itemLayout = new Attribute("itemLayout", "@layout/itemLayout");
	private final Attribute itemMapping = new Attribute("itemMapping", "[text1.title:{title}]");
	private final Attribute dropdownLayout = new Attribute("dropdownLayout", "@layout/dropdownLayout");
	private final Attribute dropdownMapping = new Attribute("dropdownMapping", "[text1.title:{artist}]");
	private final Attribute onItemClick = new Attribute("onItemClick","commandName");
	
	private AttributesAndViewCombination[] illegalAttributeCombinations = { 
			new AttributesAndViewCombination(listView, source),
			new AttributesAndViewCombination(listView, itemLayout), 
			new AttributesAndViewCombination(spinner, dropdownLayout),
			new AttributesAndViewCombination(spinner, source, itemLayout), 
			new AttributesAndViewCombination(spinner, source, dropdownLayout),
			new AttributesAndViewCombination(spinner, itemLayout, dropdownLayout)};

	private AdapterViewAttributeProvider adapterViewAttributeProvider = new AdapterViewAttributeProvider();
	private Map<String, String> pendingBindingAttributes;

	@Before
	public void setUp()
	{
		adapterViewAttributeProvider = new AdapterViewAttributeProvider();
		pendingBindingAttributes = Maps.newHashMap();
	}

	@Test
	public void givenOnItemClick_ThenCreateAnOnItemClickAttribute()
	{
		givenAttributes(onItemClick);

		BindingAttribute bindingAttribute = adapterViewAttributeProvider.createSupportedBindingAttributes(listView, pendingBindingAttributes, false).get(0);

		assertThat(bindingAttribute.getViewAttribute(), instanceOf(OnItemClickAttribute.class));
	}

	@Test
	public void givenSourceAndItemLayout_ThenCreateACompoundAttribute()
	{
		givenAttributes(source, itemLayout);

		BindingAttribute bindingAttribute = adapterViewAttributeProvider.createSupportedBindingAttributes(listView, pendingBindingAttributes, false).get(0);

		assertThat(bindingAttribute.getViewAttribute(), instanceOf(AdaptedDataSetAttributes.class));
	}

	@Test
	public void givenSourceItemLayoutAndDropdownLayout_ThenCreateACompoundAttribute()
	{
		givenAttributes(source, itemLayout, dropdownLayout);

		BindingAttribute bindingAttribute = adapterViewAttributeProvider.createSupportedBindingAttributes(listView, pendingBindingAttributes, false).get(0);

		assertThat(bindingAttribute.getViewAttribute(), instanceOf(AdaptedDataSetAttributes.class));
		AdaptedDataSetAttributes adaptedDataSetAttributes = (AdaptedDataSetAttributes) bindingAttribute.getViewAttribute();
		assertTrue(adaptedDataSetAttributesContains(DropdownLayoutAttribute.class, adaptedDataSetAttributes));
	}

	@Test
	public void givenAnItemMappingAttribute_ThenCompoundAttributeShouldContainAnItemMappingAttribute()
	{
		givenAttributes(source, itemLayout, itemMapping);
		
		BindingAttribute bindingAttribute = adapterViewAttributeProvider.createSupportedBindingAttributes(listView, pendingBindingAttributes, false).get(0);

		AdaptedDataSetAttributes adaptedDataSetAttributes = (AdaptedDataSetAttributes) bindingAttribute.getViewAttribute();
		assertTrue(adaptedDataSetAttributesContains(ItemMappingAttribute.class, adaptedDataSetAttributes));
	}
	
	@Test
	public void givenAnDropdownMappingAttribute_ThenCompoundAttributeShouldContainADropdownMappingAttribute()
	{
		givenAttributes(source, itemLayout, dropdownLayout, dropdownMapping);
		
		BindingAttribute bindingAttribute = adapterViewAttributeProvider.createSupportedBindingAttributes(spinner, pendingBindingAttributes, false).get(0);

		AdaptedDataSetAttributes adaptedDataSetAttributes = (AdaptedDataSetAttributes) bindingAttribute.getViewAttribute();
		assertTrue(adaptedDataSetAttributesContains(DropdownMappingAttribute.class, adaptedDataSetAttributes));
	}
	
	@Test
	public void givenDataSetAttributes_ThenBindingAttributeShouldContainAllRelevantAttributeNames()
	{
		givenAttributes(source, itemLayout, itemMapping, dropdownLayout, dropdownMapping);
		
		BindingAttribute bindingAttribute = adapterViewAttributeProvider.createSupportedBindingAttributes(spinner, pendingBindingAttributes, false).get(0);

		assertTrue(bindingAttribute.getAttributeNames().contains("source"));
		assertTrue(bindingAttribute.getAttributeNames().contains("itemLayout"));
		assertTrue(bindingAttribute.getAttributeNames().contains("itemMapping"));
		assertTrue(bindingAttribute.getAttributeNames().contains("dropdownLayout"));
		assertTrue(bindingAttribute.getAttributeNames().contains("dropdownMapping"));
	}
	
	private boolean adaptedDataSetAttributesContains(Class<? extends AdapterViewAttribute> clazz, AdaptedDataSetAttributes adaptedDataSetAttributes)
	{
		for (AdapterViewAttribute adapterViewAttribute : adaptedDataSetAttributes.getAdapterViewAttributes())
		{
			if (clazz.isAssignableFrom(adapterViewAttribute.getClass()))
				return true;
		}

		return false;
	}

	private void givenAttributes(Attribute... attributes)
	{
		for (Attribute attribute : attributes)
			pendingBindingAttributes.put(attribute.name, attribute.value);
	}
	
	@Test
	public void givenAnIllegalAttributeCombination_ThenReject()
	{
		for (AttributesAndViewCombination illegalAttributeCombination : illegalAttributeCombinations)
		{
			boolean runtimeExceptionThrown = false;
			setUp();
			
			for (Attribute attribute : illegalAttributeCombination.attributes)
			{
				pendingBindingAttributes.put(attribute.name, attribute.value);
			}

			try
			{
				adapterViewAttributeProvider.createSupportedBindingAttributes(illegalAttributeCombination.view, pendingBindingAttributes, false);
			} catch (RuntimeException e)
			{
				runtimeExceptionThrown = true;
			}

			assertTrue(illegalAttributeCombination + " did not throw RuntimeException as expected", runtimeExceptionThrown);
		}
	}

	private static class Attribute
	{
		private String name;
		private String value;

		public Attribute(String name, String value)
		{
			this.name = name;
			this.value = value;
		}
		
		public String toString()
		{
			return name + ":" + value;
		}
	}

	private static class AttributesAndViewCombination
	{
		private AdapterView<?> view;
		private Attribute[] attributes;

		public AttributesAndViewCombination(AdapterView<?> view, Attribute... attributes)
		{
			this.view = view;
			this.attributes = attributes;
		}
		
		public String toString()
		{
			String str = view.getClass().getName() + " with attributes: ";
		
			for (Attribute attribute : attributes)
				str += attribute.toString() + " ";
			
			return str;
		}
	}
}
