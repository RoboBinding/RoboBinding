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

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import robobinding.binding.BindingAttribute;
import robobinding.binding.viewattribute.EnabledAttribute;
import robobinding.binding.viewattribute.OnClickAttribute;
import robobinding.binding.viewattribute.VisibilityAttribute;
import android.view.View;

import com.xtremelabs.robolectric.RobolectricTestRunner;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
@RunWith(RobolectricTestRunner.class)
public class ViewAttributeProviderTest
{
	private static final String ATTRIBUTE_VALUE = "{attributeValue}";
	private ViewAttributeProvider viewAttributeProvider;
	private View view;

	@Before
	public void setUp()
	{
		viewAttributeProvider = new ViewAttributeProvider();
		view = new View(null);
	}
	
	@Test
	public void givenTheAttributeNameVisibility_ThenReturnABindingAttributeWithVisibilityAttribute()
	{
		String attributeName = "visibility";
		
		BindingAttribute bindingAttribute = viewAttributeProvider.getSupportedBindingAttribute(view, attributeName, ATTRIBUTE_VALUE);
		
		assertThat(bindingAttribute.getViewAttribute(), instanceOf(VisibilityAttribute.class));
	}
	
	@Test
	public void givenTheAttributeNameEnabled_ThenReturnABindingAttributeWithEnabledAttribute()
	{
		String attributeName = "enabled";
		
		BindingAttribute bindingAttribute = viewAttributeProvider.getSupportedBindingAttribute(view, attributeName, ATTRIBUTE_VALUE);
		
		assertThat(bindingAttribute.getViewAttribute(), instanceOf(EnabledAttribute.class));
	}
	
	@Test
	public void givenTheAttributeNameOnClick_ThenReturnABindingAttributeWithOnClickAttribute()
	{
		String attributeName = "onClick";
		
		BindingAttribute bindingAttribute = viewAttributeProvider.getSupportedBindingAttribute(view, attributeName, ATTRIBUTE_VALUE);
		
		assertThat(bindingAttribute.getViewAttribute(), instanceOf(OnClickAttribute.class));
	}
	
	@Test
	public void givenAnyOtherAttributeName_ThenReturnNull()
	{
		String attributeName = "something_else";
		
		BindingAttribute bindingAttribute = viewAttributeProvider.getSupportedBindingAttribute(view, attributeName, ATTRIBUTE_VALUE);
		
		assertNull(bindingAttribute);
	}
}
