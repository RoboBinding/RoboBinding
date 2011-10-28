/**
 * LongConverterTest.java
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

import java.util.List;

import robobinding.value.experimental.Converters;
import robobinding.value.experimental.ValueHolders;

/**
 * @since 1.0
 * @version $Revision:  $
 * @author Cheng Wei
 *
 */
public class LongConverterTest extends AbstractConverterTest<Long, Long>
{
	private static final double MULTIPLIER = 10.0;
	
	@Override
	protected AbstractConverterTester<Long, Long> createTester()
	{
		return new LongConverterTester();
	}
	@Override
	protected List<Long> createEmptySourceValues()
	{
		return createLongList(null, null, null);
	}
	@Override
	protected List<Long> createSourceValues()
	{
		return createLongList(1L, 2L, null);
	}
	@Override
	protected List<Long> creatExpectedConvertedValues()
	{
		return createLongList(10L, 20L, null);
	}
	@Override
	protected List<Long> createConvertedValues()
	{
		return creatExpectedConvertedValues();
	}
	@Override
	protected List<Long> createExpectedSourceValues()
	{
		return createSourceValues();
	}
	private List<Long> createLongList(Long... values)
	{
		return AbstractConverterTester.createLongList(values);
	}
	private class LongConverterTester extends AbstractConverterTester<Long, Long>
	{
		@Override
		protected ValueModel<Long> createConverter(ValueModel<Long> source)
		{
			return Converters.createLongConverter(source, MULTIPLIER);
		}
		@Override
		protected ValueModel<Long> createSourceValueModel(Long sourceValue)
		{
			return ValueHolders.create(sourceValue);
		}
	}
}
