/**
 * DoubleConverterTest.java
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
public class DoubleConverterTest
{
	private static final double MULTIPLIER_100 = 100;
	
	private DoubleConverterTester tester;
	public void setUp()
	{
		tester = new DoubleConverterTester();
	}
	@Test
	public void givenSourceValues_whenApplyingConverters_thenConvertedResults()
	{
		tester.givenSourceValues(creatDoubleList(1.0, 2.0, null));
		
		tester.whenApplyingConverters();
		
		tester.assertConvertedResults(creatDoubleList(100.0, 200.0, null));
	}
	@Test
	public void givenEmptySourceValuesAndConverters_whenSettingSourceValues_thenConvertedResults()
	{
		tester.givenSourceValues(creatDoubleList(null, null, null));
		tester.applyConverters();
		
		tester.whenSettingSourceValues(creatDoubleList(1.0, 2.0, null));
		
		tester.assertConvertedResults(creatDoubleList(100.0, 200.0, null));
	}
	private List<Double> creatDoubleList(Double... values)
	{
		return AbstractConverterTester.creatDoubleList(values);
	}
	private class DoubleConverterTester extends AbstractConverterTester<Double, Double>
	{
		@Override
		protected ValueModel<Double> createConverter(ValueModel<Double> source)
		{
			return Converters.createDoubleConverter(source, MULTIPLIER_100);
		}
		@Override
		protected ValueModel<Double> createSourceValueModel(Double sourceValue)
		{
			return ValueHolders.create(sourceValue);
		}
	}
}
