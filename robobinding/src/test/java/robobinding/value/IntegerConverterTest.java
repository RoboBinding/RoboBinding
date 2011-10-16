/**
 * IntegerConverterTest.java
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
public class IntegerConverterTest
{
	private static final double MULTIPLIER = 10;
	
	private IntegerConverterTester tester;
	public void setUp()
	{
		tester = new IntegerConverterTester();
	}
	@Test
	public void givenSourceValues_whenApplyingConverters_thenConvertedResults()
	{
		tester.givenSourceValues(creatIntegerList(1, 2, null));
		
		tester.whenApplyingConverters();
		
		tester.assertConvertedResults(creatIntegerList(10, 20, null));
	}
	@Test
	public void givenEmptySourceValuesAndConverters_whenSettingSourceValues_thenConvertedResults()
	{
		tester.givenSourceValues(creatIntegerList(null, null, null));
		tester.applyConverters();
		
		tester.whenSettingSourceValues(creatIntegerList(1, 2, null));
		
		tester.assertConvertedResults(creatIntegerList(10, 20, null));
	}
	private List<Integer> creatIntegerList(Integer... values)
	{
		return AbstractConverterTester.createIntegerList(values);
	}
	private class IntegerConverterTester extends AbstractConverterTester<Integer, Integer>
	{
		@Override
		protected ValueModel<Integer> createConverter(ValueModel<Integer> source)
		{
			return Converters.createIntegerConverter(source, MULTIPLIER);
		}
		@Override
		protected ValueModel<Integer> createSourceValueModel(Integer sourceValue)
		{
			return ValueHolders.create(sourceValue);
		}
	}
}
