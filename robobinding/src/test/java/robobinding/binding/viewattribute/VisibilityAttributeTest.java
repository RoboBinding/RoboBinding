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
package robobinding.binding.viewattribute;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import robobinding.binding.viewattribute.VisibilityAttribute.BooleanVisibilityAttribute;
import robobinding.binding.viewattribute.VisibilityAttribute.IntegerVisibilityAttribute;
import robobinding.presentationmodel.PresentationModelAdapter;
import android.view.View;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class VisibilityAttributeTest
{
	private View view;
	private VisibilityAttribute visibilityAttribute;
	private PresentationModelAdapter presentationModelAdapter;
	
	@Before
	public void setUp()
	{
		 visibilityAttribute = new VisibilityAttribute(view, "{property_name}");
		 presentationModelAdapter = mock(PresentationModelAdapter.class);
	}
	
	//TODO Refactor these tests
	
	@Test
	public void whenBindingWithAnIntegerProperty_ThenInitializeIntegerVisibilityAttribute()
	{
		when(presentationModelAdapter.getPropertyType("property_name")).thenReturn((Class) Integer.class);
		
		assertThat(visibilityAttribute.lookupPropertyViewAttribute(presentationModelAdapter), instanceOf(IntegerVisibilityAttribute.class));
		
		when(presentationModelAdapter.getPropertyType("property_name")).thenReturn((Class) int.class);
		
		assertThat(visibilityAttribute.lookupPropertyViewAttribute(presentationModelAdapter), instanceOf(IntegerVisibilityAttribute.class));
	}
	
	@Test
	public void whenBindingWithABooleanProperty_ThenInitializeBooleanVisibilityAttribute()
	{
		when(presentationModelAdapter.getPropertyType("property_name")).thenReturn((Class) Boolean.class);
		
		assertThat(visibilityAttribute.lookupPropertyViewAttribute(presentationModelAdapter), instanceOf(BooleanVisibilityAttribute.class));
		
		when(presentationModelAdapter.getPropertyType("property_name")).thenReturn((Class) boolean.class);
		
		assertThat(visibilityAttribute.lookupPropertyViewAttribute(presentationModelAdapter), instanceOf(BooleanVisibilityAttribute.class));
	}
	
}
