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
package robobinding.value;

import java.util.List;

import org.junit.Test;

/**
 * @since 1.0
 * @version $Revision:  $
 * @author Cheng Wei
 *
 */
public class LongToIntegerConverterTest
{
	private static final int MULTIPLIER = 10;
	
	private LongToIntegerConverterTester tester;
	public void setUp()
	{
		tester = new LongToIntegerConverterTester();
	}
	@Test
	public void givenSourceValues_whenApplyingConverters_thenConvertedResults()
	{
		tester.givenSourceValues(creatLongList(1L, 2L, null));
		
		tester.whenApplyingConverters();
		
		tester.assertConvertedResults(creatIntegerList(10, 20, null));
	}
	@Test
	public void givenEmptySourceValuesAndConverters_whenSettingSourceValues_thenConvertedResults()
	{
		tester.givenSourceValues(creatLongList(null, null, null));
		tester.applyConverters();
		
		tester.whenSettingSourceValues(creatLongList(1L, 2L, null));
		
		tester.assertConvertedResults(creatIntegerList(10, 20, null));
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
		protected ValueModel<Integer> createConverter(ValueModel<Long> source)
		{
			return Converters.createLongToIntegerConverter(source, MULTIPLIER);
		}
		@Override
		protected ValueModel<Long> createSourceValueModel(Long sourceValue)
		{
			return ValueHolders.create(sourceValue);
		}
	}
}
