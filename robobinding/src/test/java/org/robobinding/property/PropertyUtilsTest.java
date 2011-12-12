/**
 * PropertyUtilsTest.java
 * Nov 13, 2011 Copyright Cheng Wei and Robert Taylor
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

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.robobinding.internal.com_google_common.collect.Sets;
import org.robobinding.property.PropertyUtils;


/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class PropertyUtilsTest
{
	@Test
	public void whenGetPropertyNames_thenReturnExpectedPropertyNames()
	{
		List<String> propertyNames = PropertyUtils.getPropertyNames(Bean.class);
		
		Assert.assertEquals(Sets.newHashSet(Bean.PROPERTY1, Bean.PROPERTY2), Sets.newHashSet(propertyNames));
	}
	public static class Bean
	{
		public static final String PROPERTY1 = "property1";
		public static final String PROPERTY2 = "property2";
		public Bean()
		{
		}
		public String getProperty1()
		{
			return "property1";
		}
		public int getProperty2()
		{
			return 0;
		}
		public double nonProperty()
		{
			return 0.0;
		}
		public boolean getNonPropertyWithParameter(String p1)
		{
			return true;
		}
	}
}
