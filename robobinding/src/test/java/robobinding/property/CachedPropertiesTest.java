/**
 * CachedPropertiesTest.java
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
package robobinding.property;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class CachedPropertiesTest
{
	private CachedProperties cachedProperties;
	@Before
	public void setUp()
	{
		cachedProperties = new CachedProperties(new Bean());
	}
	@Test
	public void givenGetReadOnlyPropertyOnce_whenGetReadOnlyPropertyAgain_thenReturnSameInstance()
	{
		AbstractProperty<Boolean> property = cachedProperties.getReadOnlyProperty(Bean.READ_ONLY_PROPERTY);
		
		AbstractProperty<Boolean> cachedProperty = cachedProperties.getReadOnlyProperty(Bean.READ_ONLY_PROPERTY);
		
		Assert.assertTrue(property == cachedProperty);
	}
	@Test
	public void givenAccessReadWritePropertyUsingGetReadOnlyPropertyAtFirst_whenAccessUsingGetReadWritePropertyLater_thenReturnSameInstance()
	{
		AbstractProperty<Boolean> propertyUsingGetReadOnlyProperty = cachedProperties.getReadOnlyProperty(Bean.READ_WRITE_PROPERTY);
		
		AbstractProperty<Boolean> propertyUsingGetReadWriteProperty = cachedProperties.getReadWriteProperty(Bean.READ_WRITE_PROPERTY);
		
		Assert.assertTrue(propertyUsingGetReadOnlyProperty == propertyUsingGetReadWriteProperty);
	}
}
