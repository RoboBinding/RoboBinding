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
package org.robobinding.viewattribute.textview;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.robobinding.internal.com_google_common.collect.Lists;
import org.robobinding.viewattribute.RandomValues;
import org.robobinding.viewattribute.seekbar.AbstractGroupedViewAttributeTest;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class TextAttributeGroupTest extends AbstractGroupedViewAttributeTest<TextAttributeGroup>
{
	private final Attribute oneWayBindingText = attribute("text={name}");
	private final Attribute twoWayBindingText = attribute("text=${name}");
	private final Attribute valueCommitMode = attribute("valueCommitMode=onChange");

	@Test
	public void givenATextAttribute_thenCreateInstance()
	{
		givenAttribute(RandomValues.either(oneWayBindingText, twoWayBindingText));

		performInitialization();

		assertThatAttributeWasCreated(TextAttribute.class);
	}

	@Test
	public void givenATextAttribute_thenValueCommitModeShouldDefaultToOnChange()
	{
		givenAttribute(RandomValues.either(oneWayBindingText, twoWayBindingText));

		performInitialization();

		assertThat(attributeUnderTest.valueCommitMode, equalTo(ValueCommitMode.ON_CHANGE));
	}
	
	@Test
	public void givenOneWayBindingTextAndValueCommitModeAttributes_thenCreateTextAttribute()
	{
		givenAttributes(oneWayBindingText, valueCommitMode);

		performInitialization();

		assertThatAttributeWasCreated(TextAttribute.class);
	}

	@Test
	public void givenValueCommitModeAttribute_thenSetValueCommitModeAccordingly()
	{
		String valueCommitModeValue = RandomValues.either("onChange", "onFocusLost");
		Attribute valueCommitMode = attribute("valueCommitMode=" + valueCommitModeValue);
		givenAttributes(oneWayBindingText, valueCommitMode);

		performInitialization();

		assertTrue(attributeUnderTest.valueCommitMode == ValueCommitMode.from(valueCommitModeValue));
	}

	@Test(expected = RuntimeException.class)
	public void givenValueCommitModeAttributeOnly_thenReject()
	{
		givenAttribute(valueCommitMode);

		performInitialization();
	}

	@Test(expected = RuntimeException.class)
	public void givenTwoWayBindingTextAndValueCommitModeAttributes_thenReject()
	{
		givenAttributes(twoWayBindingText, valueCommitMode);

		performInitialization();
	}

	@Override
	protected List<?> getGeneratedChildAttributes(TextAttributeGroup attributeUnderTest)
	{
		return Lists.newArrayList(attributeUnderTest.textAttribute);
	}

}
