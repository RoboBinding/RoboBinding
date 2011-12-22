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
package org.robobinding.viewattribute.textview;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.robobinding.presentationmodel.PresentationModelAdapter;
import org.robobinding.viewattribute.PropertyBindingDetails;
import org.robobinding.viewattribute.textview.TextAttribute;
import org.robobinding.viewattribute.textview.ValueCommitMode;
import org.robobinding.viewattribute.textview.TextAttribute.CharSequenceTextAttribute;
import org.robobinding.viewattribute.textview.TextAttribute.StringTextAttribute;

import android.widget.TextView;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class TextAttributeTest
{
	private TextView textView;
	private TextAttribute textAttribute;
	private PresentationModelAdapter presentationModelAdapter;

	@Before
	public void setUp()
	{
		 textAttribute = new TextAttribute(textView, PropertyBindingDetails.createFrom("{property_name}", true), ValueCommitMode.ON_FOCUS_LOST);
		 presentationModelAdapter = mock(PresentationModelAdapter.class);
	}
	
	@Test
	public void whenBindingWithACharSequenceProperty_ThenInitializeCharSequenceTextAttribute()
	{
		when(presentationModelAdapter.getPropertyType("property_name")).thenReturn((Class)CharSequence.class);
		
		assertThat(textAttribute.lookupPropertyViewAttribute(presentationModelAdapter), instanceOf(CharSequenceTextAttribute.class));
	}
	
	@Test
	public void whenBindingWithAStringProperty_ThenInitializeStringTextAttribute()
	{
		when(presentationModelAdapter.getPropertyType("property_name")).thenReturn((Class)String.class);
		
		assertThat(textAttribute.lookupPropertyViewAttribute(presentationModelAdapter), instanceOf(StringTextAttribute.class));
	}
	
}
