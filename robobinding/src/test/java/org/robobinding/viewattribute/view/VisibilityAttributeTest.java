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

import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.robobinding.internal.com_google_common.collect.Maps;
import org.robobinding.viewattribute.AbstractPropertyViewAttributeTest;
import org.robobinding.viewattribute.PropertyViewAttribute;
import org.robobinding.viewattribute.view.VisibilityAttribute.BooleanVisibilityAttribute;
import org.robobinding.viewattribute.view.VisibilityAttribute.IntegerVisibilityAttribute;

import android.view.View;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class VisibilityAttributeTest extends AbstractPropertyViewAttributeTest<View, VisibilityAttribute>
{
	private VisibilityAttribute visibilityAttribute;
	private Map<Class<?>, Class<? extends PropertyViewAttribute<View>>> propertyTypeToViewAttributeMappings;
	
	@Before
	public void setUp()
	{
		visibilityAttribute = new VisibilityAttribute();
		
		propertyTypeToViewAttributeMappings = Maps.newHashMap();
		propertyTypeToViewAttributeMappings.put(int.class, IntegerVisibilityAttribute.class);
		propertyTypeToViewAttributeMappings.put(Integer.class, IntegerVisibilityAttribute.class);
		propertyTypeToViewAttributeMappings.put(boolean.class, BooleanVisibilityAttribute.class);
		propertyTypeToViewAttributeMappings.put(Boolean.class, BooleanVisibilityAttribute.class);
	}
	
	@Test
	public void givenPropertyType_whenCreatePropertyViewAttribute_thenReturnExpectedInstance()
	{
		for(Class<?> propertyType : propertyTypeToViewAttributeMappings.keySet())
		{
			PropertyViewAttribute<View> propertyViewAttribute = visibilityAttribute.createPropertyViewAttribute(propertyType);
			
			assertThat(propertyViewAttribute, instanceOf(propertyTypeToViewAttributeMappings.get(propertyType)));
		}
	}
	
}
