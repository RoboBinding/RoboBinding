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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import robobinding.binding.BindingAttribute;
import robobinding.binding.viewattribute.TextAttribute;
import robobinding.binding.viewattribute.ValueCommitMode;
import android.widget.TextView;

import com.xtremelabs.robolectric.RobolectricTestRunner;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
@RunWith(RobolectricTestRunner.class)
public class TextViewAttributeProviderTest
{
	private static final String ONE_WAY_BINDING_ATTRIBUTE = "{attributeValue}";
	private static final String TWO_WAY_BINDING_ATTRIBUTE = "${attributeValue}";
	
	private TextView textView;
	private TextViewAttributeProvider textViewAttributeProvider;
	private Map<String, String> pendingBindingAttributes;
	
	
	@Before
	public void setUp()
	{
		textView = new TextView(null);
		textViewAttributeProvider = new TextViewAttributeProvider();
		pendingBindingAttributes = new HashMap<String, String>();
	}
	
	@Test
	public void givenATextAttribute_WhenCreatingATextAttributeInstance_ThenDefaultValueCommitModeShouldBeOnChange()
	{
		pendingBindingAttributes.put("text", TWO_WAY_BINDING_ATTRIBUTE);
		
		BindingAttribute bindingAttribute = textViewAttributeProvider.createSupportedBindingAttributes(textView, pendingBindingAttributes, true).get(0);
		
		TextAttribute textAttribute = (TextAttribute)bindingAttribute.getViewAttribute();
		assertThat(textAttribute.getValueCommitMode(), equalTo(ValueCommitMode.ON_CHANGE));
	}
	
	@Test
	public void givenBothTextAndValueCommitModeAttributes_WhenCreatingATextAttributeInstance_ThenValueCommitModeShouldBeCorrect()
	{
		pendingBindingAttributes.put("text", TWO_WAY_BINDING_ATTRIBUTE);
		pendingBindingAttributes.put("valueCommitMode", "onChange");
		
		BindingAttribute bindingAttribute = textViewAttributeProvider.createSupportedBindingAttributes(textView, pendingBindingAttributes, true).get(0);
		
		TextAttribute textAttribute = (TextAttribute)bindingAttribute.getViewAttribute();
		assertThat(textAttribute.getValueCommitMode(), equalTo(ValueCommitMode.ON_CHANGE));
	}
	
	@Test (expected=RuntimeException.class)
	public void givenATextAttributeWithOnly1WayBinding_WhenAValueCommitModeAttributeIsPresent_ThenThrowRuntimeException()
	{
		pendingBindingAttributes.put("text", ONE_WAY_BINDING_ATTRIBUTE);
		pendingBindingAttributes.put("valueCommitMode", "onChange");
		
		textViewAttributeProvider.createSupportedBindingAttributes(textView, pendingBindingAttributes, true).get(0);
	}
	
	@Test (expected=RuntimeException.class)
	public void givenNoTextAttribute_WhenAValueCommitModeAttributeIsPresent_ThenThrowRuntimeException()
	{
		pendingBindingAttributes.put("valueCommitMode", "onChange");
		
		textViewAttributeProvider.createSupportedBindingAttributes(textView, pendingBindingAttributes, true).get(0);
	}
	
	@Test
	public void givenAnyOtherAttributeName_ThenReturnNothing()
	{
		pendingBindingAttributes.put("something_else", ONE_WAY_BINDING_ATTRIBUTE);
		
		List<BindingAttribute> bindingAttributes = textViewAttributeProvider.createSupportedBindingAttributes(textView, pendingBindingAttributes, true);
		
		assertThat(bindingAttributes.size(), equalTo(0));
	}
}
