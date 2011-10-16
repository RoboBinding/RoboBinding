/**
 * BooleanNegatorTest.java
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

import org.junit.Before;
import org.junit.Test;

/**
 * @since 1.0
 * @version $Revision:  $
 * @author Cheng Wei
 *
 */
public class BooleanNegatorTest
{
	private BooleanNegatorConverterTester tester;
	@Before
	public void setUp()
	{
		tester = new BooleanNegatorConverterTester();
	}
	@Test
	public void givenSourceValues_whenApplyingConverters_thenConvertedResults()
	{
		tester.givenSourceValues(creatBooleanList(false, true, null));
		
		tester.whenApplyingConverters();
		
		tester.assertConvertedResults(creatBooleanList(true, false, null));
	}
	@Test
	public void givenEmptySourceValuesAndConverters_whenSettingSourceValues_thenConvertedResults()
	{
		tester.givenSourceValues(creatBooleanList(null, null, null));
		tester.applyConverters();
		
		tester.whenSettingSourceValues(creatBooleanList(false, true, null));
		
		tester.assertConvertedResults(creatBooleanList(true, false, null));
	}

	private List<Boolean> creatBooleanList(Boolean... values)
	{
		return AbstractConverterTester.creatBooleanList(values);
	}

	private static class BooleanNegatorConverterTester extends AbstractConverterTester<Boolean, Boolean>
	{
		@Override
		protected ValueModel<Boolean> createConverter(ValueModel<Boolean> source)
		{
			return Converters.createBooleanNegator(source);
		}
		@Override
		protected ValueModel<Boolean> createSourceValueModel(Boolean sourceValue)
		{
			return ValueHolders.create(sourceValue);
		}
	}
}
