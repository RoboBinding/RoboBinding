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
package org.robobinding.viewattribute;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;

import java.lang.reflect.ParameterizedType;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import android.view.View;

import com.google.common.collect.Maps;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public abstract class AbstractMultiTypePropertyViewAttributeTest<T extends AbstractMultiTypePropertyViewAttribute<?>>
{
	private Map<Class<?>, Class<? extends PropertyViewAttribute<? extends View>>> propertyTypeToViewAttributeMappings;
	
	@Before
	public void populateMappings()
	{
		propertyTypeToViewAttributeMappings = Maps.newHashMap();
		setTypeMappingExpectations();
	}
	
	protected abstract void setTypeMappingExpectations();
	
	@Test
	public void givenPropertyType_whenCreatePropertyViewAttribute_thenReturnExpectedInstance()
	{
		AbstractMultiTypePropertyViewAttribute<?> attribute = createAttribute();
		
		for(Class<?> propertyType : propertyTypeToViewAttributeMappings.keySet())
		{
			AbstractPropertyViewAttribute<?,?> propertyViewAttribute = attribute.createPropertyViewAttribute(propertyType);
			
			assertThat(propertyViewAttribute, instanceOf(propertyTypeToViewAttributeMappings.get(propertyType)));
		}
	}

	protected AbstractMultiTypePropertyViewAttribute<?> createAttribute()
	{
		ParameterizedType attributeType = (ParameterizedType)getClass().getGenericSuperclass();
		AbstractMultiTypePropertyViewAttribute<?> attribute = ParameterizedTypeUtils.createTypeArgument(attributeType, 0);
		return attribute;
	}
	
	protected TypeMappingBuilder forPropertyType(Class<?> propertyType)
	{
		return new TypeMappingBuilder(propertyType);
	}
	
	protected class TypeMappingBuilder
	{
		private final Class<?> propertyType;

		public TypeMappingBuilder(Class<?> propertyType)
		{
			this.propertyType = propertyType;
		}

		public void expectAttribute(Class<? extends PropertyViewAttribute<? extends View>> attributeClass)
		{
			propertyTypeToViewAttributeMappings.put(propertyType, attributeClass);
		}
	}
}
