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
package org.robobinding.viewattribute.compoundbutton;

import java.util.List;

import org.junit.Test;
import org.robobinding.viewattribute.seekbar.AbstractGroupedViewAttributeTest;

import android.view.View;
import android.widget.CheckBox;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class OnCheckedChangeAttributesTest extends AbstractGroupedViewAttributeTest<OnCheckedChangeAttributes>
{
	private final Attribute checked = attribute("checked={checked}");
	private final Attribute onCheckedChange = attribute("onCheckedChange=checkedChange");
	
	@Test
	public void whenAttributeCheckedIsPresent_thenCheckedAttributeCreated()
	{
		givenAttribute(checked);
		
		performInitialization();
		
		assertThatAttributeWasCreated(CheckedAttribute.class);
	}

	@Test
	public void whenAttributeOnCheckedChangeIsPresent_thenOnCheckedChangeAttributeCreated()
	{
		givenAttribute(onCheckedChange);
		
		performInitialization();
		
		assertThatAttributeWasCreated(OnCheckedChangeAttribute.class);
	}
	
	@Test
	public void whenAttributesOnCheckedChangeAndCheckedArePresent_thenBothAttributesCreated()
	{
		givenAttributes(checked, onCheckedChange);
		
		performInitialization();
		
		assertThatAttributesWereCreated(CheckedAttribute.class, OnCheckedChangeAttribute.class);
	}

	@Override
	protected List<?> getGeneratedChildAttributes(OnCheckedChangeAttributes attributeUnderTest)
	{
		return attributeUnderTest.viewAttributes;
	}

	@Override
	protected Class<? extends View> overrideViewClass()
	{
		return CheckBox.class;
	}
}
