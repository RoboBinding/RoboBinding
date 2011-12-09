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

import java.util.List;

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
public class TextViewAttributeProviderTest extends AbstractCompoundBindingAttributeTest<TextView>
{
	private static final String ONE_WAY_BINDING_ATTRIBUTE = "{attributeValue}";
	private static final String TWO_WAY_BINDING_ATTRIBUTE = "${attributeValue}";
	
	private final Attribute textWithOneWayBinding = new Attribute("text", ONE_WAY_BINDING_ATTRIBUTE);
	private final Attribute textWithTwoWayBinding = new Attribute("text", TWO_WAY_BINDING_ATTRIBUTE);
	private final Attribute valueCommitOnChange = new Attribute("valueCommitMode", "onChange");
	private final Attribute unrecognizedAttribute = new Attribute("something_else", ONE_WAY_BINDING_ATTRIBUTE);
	
	private TextView textView;
	private TextViewAttributeProvider textViewAttributeProvider;
	
	@Before
	public void setUp()
	{
		textView = new TextView(null);
		textViewAttributeProvider = new TextViewAttributeProvider();
	}
	
	@Test
	public void givenATextAttribute_WhenCreatingATextAttributeInstance_ThenDefaultValueCommitModeShouldBeOnChange()
	{
		givenAttributes(textWithTwoWayBinding);
		
		BindingAttribute bindingAttribute = getResolvedBindingAttribute();
		
		TextAttribute textAttribute = (TextAttribute)bindingAttribute.getViewAttribute();
		assertThat(textAttribute.getValueCommitMode(), equalTo(ValueCommitMode.ON_CHANGE));
	}
	
	@Test
	public void givenBothTextAndValueCommitModeAttributes_WhenCreatingATextAttributeInstance_ThenValueCommitModeShouldBeCorrect()
	{
		givenAttributes(textWithTwoWayBinding, valueCommitOnChange);
		
		BindingAttribute bindingAttribute = getResolvedBindingAttribute();
		
		TextAttribute textAttribute = (TextAttribute)bindingAttribute.getViewAttribute();
		assertThat(textAttribute.getValueCommitMode(), equalTo(ValueCommitMode.ON_CHANGE));
	}
	
	@Test (expected=RuntimeException.class)
	public void givenATextAttributeWithOnly1WayBinding_WhenAValueCommitModeAttributeIsPresent_ThenThrowRuntimeException()
	{
		givenAttributes(textWithOneWayBinding, valueCommitOnChange);
		
		resolveSupportedBindingAttributes();
	}
	
	@Test (expected=RuntimeException.class)
	public void givenNoTextAttribute_WhenAValueCommitModeAttributeIsPresent_ThenThrowRuntimeException()
	{
		givenAttributes(valueCommitOnChange);
		
		resolveSupportedBindingAttributes();
	}
	
	@Test
	public void givenAnyOtherAttributeName_ThenReturnNothing()
	{
		givenAttributes(unrecognizedAttribute);
		
		List<BindingAttribute> bindingAttributes = getResolvedBindingAttributes();
		
		assertThat(bindingAttributes.size(), equalTo(0));
	}

	@Override
	protected TextView getView()
	{
		return textView;
	}

	@Override
	protected BindingAttributeProvider<TextView> getAttributeProvider()
	{
		return textViewAttributeProvider;
	}
}
