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
package org.robobinding.viewattribute.view;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;
import org.robobinding.presentationmodel.PresentationModelAdapter;
import org.robobinding.viewattribute.view.VisibilityAttribute.BooleanVisibilityAttribute;
import org.robobinding.viewattribute.view.VisibilityAttribute.IntegerVisibilityAttribute;

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
		 visibilityAttribute = new VisibilityAttribute(view, "{property_name}", true);
		 presentationModelAdapter = mock(PresentationModelAdapter.class);
	}
	
	@Test
	public void whenBindingWithAnIntegerProperty_ThenInitializeIntegerVisibilityAttribute()
	{
		when(presentationModelAdapter.getPropertyType("property_name")).thenReturn(eitherPrimitiveOrWrappedIntegerClass());
		
		assertThat(visibilityAttribute.lookupPropertyViewAttribute(presentationModelAdapter), instanceOf(IntegerVisibilityAttribute.class));
	}
	
	@Test
	public void whenBindingWithABooleanProperty_ThenInitializeBooleanVisibilityAttribute()
	{
		when(presentationModelAdapter.getPropertyType("property_name")).thenReturn(eitherPrimitiveOrWrappedBooleanClass());
		
		assertThat(visibilityAttribute.lookupPropertyViewAttribute(presentationModelAdapter), instanceOf(BooleanVisibilityAttribute.class));
	}
	
	private Class eitherPrimitiveOrWrappedIntegerClass()
	{
		if (new Random().nextInt(2) == 0)
			return int.class;
		
		return Integer.class;
	}
	
	private Class eitherPrimitiveOrWrappedBooleanClass()
	{
		if (new Random().nextInt(2) == 0)
			return boolean.class;
		
		return Boolean.class;
	}
}
