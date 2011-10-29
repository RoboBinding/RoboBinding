/**
 * LongToIntegerConverterTest.java
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
package robobinding.value.experimental;

import java.util.List;

import robobinding.property.PropertyValueModel;

/**
 * @since 1.0
 * @version $Revision:  $
 * @author Cheng Wei
 *
 */
public class LongToIntegerConverterTest extends AbstractConverterTest<Long, Integer>
{
	private static final int MULTIPLIER = 10;
	
	@Override
	protected AbstractConverterTester<Long, Integer> createTester()
	{
		return new LongToIntegerConverterTester();
	}
	@Override
	protected List<Long> createEmptySourceValues()
	{
		return creatLongList(null, null, null);
	}
	@Override
	protected List<Long> createSourceValues()
	{
		return creatLongList(1L, 2L, null);
	}
	@Override
	protected List<Integer> creatExpectedConvertedValues()
	{
		return creatIntegerList(10, 20, null);
	}
	@Override
	protected List<Integer> createConvertedValues()
	{
		return creatExpectedConvertedValues();
	}
	@Override
	protected List<Long> createExpectedSourceValues()
	{
		return createSourceValues();
	}
	private List<Integer> creatIntegerList(Integer... values)
	{
		return AbstractConverterTester.createIntegerList(values);
	}
	private List<Long> creatLongList(Long... values)
	{
		return AbstractConverterTester.createLongList(values);
	}
	private class LongToIntegerConverterTester extends AbstractConverterTester<Long, Integer>
	{
		@Override
		protected PropertyValueModel<Integer> createConverter(PropertyValueModel<Long> source)
		{
			return Converters.createLongToIntegerConverter(source, MULTIPLIER);
		}
		@Override
		protected PropertyValueModel<Long> createSourceValueModel(Long sourceValue)
		{
			return ValueHolders.create(sourceValue);
		}
	}
}
