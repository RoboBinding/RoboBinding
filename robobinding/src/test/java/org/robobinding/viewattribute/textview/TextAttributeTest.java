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
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.robobinding.internal.com_google_common.collect.Maps;
import org.robobinding.property.ValueModel;
import org.robobinding.viewattribute.AbstractPropertyViewAttributeTest;
import org.robobinding.viewattribute.PropertyViewAttribute;
import org.robobinding.viewattribute.textview.TextAttribute.CharSequenceTextAttribute;
import org.robobinding.viewattribute.textview.TextAttribute.StringTextAttribute;

import android.widget.TextView;

import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.shadows.ShadowTextView;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class TextAttributeTest extends AbstractPropertyViewAttributeTest<TextView, TextAttribute>
{
	private TextAttribute textAttribute;
	private Map<Class<?>, Class<? extends PropertyViewAttribute<TextView>>> propertyTypeToViewAttributeMappings;
	
	@Before
	public void setUp()
	{
		textAttribute = new TextAttribute();
		
		propertyTypeToViewAttributeMappings = Maps.newHashMap();
		propertyTypeToViewAttributeMappings.put(String.class, StringTextAttribute.class);
		propertyTypeToViewAttributeMappings.put(CharSequence.class, CharSequenceTextAttribute.class);
	}
	
	@Test
	public void givenPropertyType_whenCreatePropertyViewAttribute_thenReturnExpectedInstance()
	{
		for(Class<?> propertyType : propertyTypeToViewAttributeMappings.keySet())
		{
			PropertyViewAttribute<TextView> propertyViewAttribute = textAttribute.createPropertyViewAttribute(propertyType);
			
			assertThat(propertyViewAttribute, instanceOf(propertyTypeToViewAttributeMappings.get(propertyType)));
		}
	}
	
	@Test
	public void givenALateValueCommitAttribute_whenUpdatingView_thenDoNotImmediatelyCommitToValueModel()
	{
		attribute.setValueCommitMode(ValueCommitMode.ON_FOCUS_LOST);
		String newText = RandomStringUtils.random(5);
		ValueModel<String> valueModel = twoWayBindToProperty(String.class);
		
		view.setText(newText);

		assertThat(valueModel.getValue(), not(equalTo(newText)));
	}
	
	@Test
	public void givenALateValueCommitAttribute_whenViewLosesFocus_thenCommitToValueModel()
	{
		attribute.setValueCommitMode(ValueCommitMode.ON_FOCUS_LOST);
		String newText = RandomStringUtils.random(5);
		ValueModel<String> valueModel = twoWayBindToProperty(String.class);
		
		view.setText(newText);

		ShadowTextView shadowTextView = Robolectric.shadowOf(view);
		shadowTextView.setViewFocus(false);
		
		assertThat(valueModel.getValue(), equalTo(newText));
	}
}
