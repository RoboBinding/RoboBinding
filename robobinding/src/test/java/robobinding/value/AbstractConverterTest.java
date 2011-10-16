/**
 * AbstractConverterTest.java
 * Oct 16, 2011 Copyright Cheng Wei and Robert Taylor
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
package robobinding.value;

import org.junit.Assert;
import org.junit.Test;

/**
 * @since 1.0
 * @version $Revision:  $
 * @author Cheng Wei
 *
 */
public class AbstractConverterTest
{
	@Test
	public void testInvertable()
	{
		long value = 1L;
		ValueModel<Long> source = ValueHolders.createLong(value);
		@SuppressWarnings("unchecked")
		AbstractConverter<Long, Long> converter = (AbstractConverter<Long, Long>)Converters.createLongConverter(source, 100);
		try
		{
			converter.setValue(converter.convertFromSource(value));
		}catch (RuntimeException e)
		{
			Assert.fail("Unexpected exception is thrown: " + e);
		}
	}

}
