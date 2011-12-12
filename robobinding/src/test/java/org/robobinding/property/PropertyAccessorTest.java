/**
 * PropertyAccessorTest.java
 * Oct 29, 2011 Copyright Cheng Wei and Robert Taylor
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
package org.robobinding.property;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.robobinding.property.PropertyAccessor;


/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class PropertyAccessorTest
{
	private Bean bean;
	@Before
	public void setUp()
	{
		bean = new Bean();
	}
	@Test
	public void givenReadOnlyProperty_whenCheckReadable_thenPassCheck()
	{
		PropertyAccessor<Boolean> readOnlyPropertyAccessor = createReadOnlyPropertyAccessor();
		
		readOnlyPropertyAccessor.checkReadable();
	}
	@Test(expected=RuntimeException.class)
	public void givenReadOnlyProperty_whenCheckWritable_thenThrowException()
	{
		PropertyAccessor<Boolean> readOnlyPropertyAccessor = createReadOnlyPropertyAccessor();
		
		readOnlyPropertyAccessor.checkWritable();
	}
	@Test
	public void givenWriteOnlyProperty_whenCheckWritable_thenPassCheck()
	{
		PropertyAccessor<Boolean> writeOnlyPropertyAccessor = createWriteOnlyPropertyAccessor();
		
		writeOnlyPropertyAccessor.checkWritable();
	}
	@Test(expected=RuntimeException.class)
	public void givenWriteOnlyProperty_whenCheckReadable_thenThrowException()
	{
		PropertyAccessor<Boolean> writeOnlyPropertyAccessor = createWriteOnlyPropertyAccessor();
		
		writeOnlyPropertyAccessor.checkReadable();
	}
	@Test
	public void givenReadOnlyProperty_whenGetValue_thenReturnExpectedValue()
	{
		PropertyAccessor<Boolean> readOnlyPropertyAccessor = createReadOnlyPropertyAccessor();
		
		Boolean actualValue = readOnlyPropertyAccessor.getValue(bean);
		
		Assert.assertEquals(bean.getReadOnlyProperty(), actualValue);
	}
	@Test(expected=RuntimeException.class)
	public void givenReadOnlyProperty_whenSetValue_thenThrowException()
	{
		PropertyAccessor<Boolean> readOnlyPropertyAccessor = createReadOnlyPropertyAccessor();
		
		readOnlyPropertyAccessor.setValue(bean, false);
	}
	@Test
	public void givenWriteOnlyProperty_whenSetValue_thenValueSet()
	{
		PropertyAccessor<Boolean> writeOnlyPropertyAccessor = createWriteOnlyPropertyAccessor();
		
		writeOnlyPropertyAccessor.setValue(bean, false);
		
		Assert.assertEquals(false, bean.writeOnlyPropertyValue);
	}
	@Test(expected=RuntimeException.class)
	public void givenWriteOnlyProperty_whenGetValue_thenThrowException()
	{
		PropertyAccessor<Boolean> writeOnlyPropertyAccessor = createWriteOnlyPropertyAccessor();
		
		writeOnlyPropertyAccessor.getValue(bean);
	}
	@Test
	public void givenAnnotatedProperty_whenHasAnnotation_thenReturnTrue()
	{
		PropertyAccessor<Boolean> annotatedPropertyAccessor = createAnnotatedPropertyAccessor();
		
		boolean result = annotatedPropertyAccessor.hasAnnotation(PropertyAnnotation.class);
		
		Assert.assertTrue(result);
	}
	@Test
	public void givenNotAnnotatedProperty_whenHasAnnotation_thenReturnFalse()
	{
		PropertyAccessor<Boolean> notAnnotatedPropertyAccessor = createNotAnnotatedPropertyAccessor();
		
		boolean result = notAnnotatedPropertyAccessor.hasAnnotation(PropertyAnnotation.class);
		
		Assert.assertFalse(result);
	}
	@Test
	public void givenAnnotatedProperty_whenGetAnnotation_thenReturnAnnotationInstance()
	{
		PropertyAccessor<Boolean> annotatedPropertyAccessor = createAnnotatedPropertyAccessor();
		
		PropertyAnnotation annotationUnderTest = annotatedPropertyAccessor.getAnnotation(PropertyAnnotation.class);
		
		Assert.assertNotNull(annotationUnderTest);
	}
	@Test(expected=RuntimeException.class)
	public void givenNotAnnotatedProperty_whenGetAnnotation_thenThrowException()
	{
		PropertyAccessor<Boolean> notAnnotatedPropertyAccessor = createNotAnnotatedPropertyAccessor();
		
		notAnnotatedPropertyAccessor.getAnnotation(PropertyAnnotation.class);
	}
	private PropertyAccessor<Boolean> createReadOnlyPropertyAccessor()
	{
		return createPropertyAccessor(Bean.READ_ONLY_PROPERTY);
	}
	private PropertyAccessor<Boolean> createWriteOnlyPropertyAccessor()
	{
		return createPropertyAccessor(Bean.WRITE_ONLY_PROPERTY);
	}
	private PropertyAccessor<Boolean> createAnnotatedPropertyAccessor()
	{
		return createPropertyAccessor(Bean.ANNOTATED_PROPERTY);
	}
	private PropertyAccessor<Boolean> createNotAnnotatedPropertyAccessor()
	{
		return createPropertyAccessor(Bean.NOT_ANNOTATED_PROPERTY);
	}
	private PropertyAccessor<Boolean> createPropertyAccessor(String propertyName)
	{
		return PropertyAccessorUtils.createPropertyAccessor(bean.getClass(), propertyName);
	}
}
