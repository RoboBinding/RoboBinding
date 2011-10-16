/**
 * FloatConverterTest.java
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
public class FloatConverterTest
{
	private static final float MULTIPLIER = 0.1f;
	
	private FloatConverterTester tester;
	public void setUp()
	{
		tester = new FloatConverterTester();
	}
	@Test
	public void givenSourceValues_whenApplyingConverters_thenConvertedResults()
	{
		tester.givenSourceValues(creatFloatList(1.0f, 2.0f, null));
		
		tester.whenApplyingConverters();
		
		tester.assertConvertedResults(creatFloatList(10.0f, 20.0f, null));
	}
	@Test
	public void givenEmptySourceValuesAndConverters_whenSettingSourceValues_thenConvertedResults()
	{
		tester.givenSourceValues(creatFloatList(null, null, null));
		tester.applyConverters();
		
		tester.whenSettingSourceValues(creatFloatList(1.0f, 2.0f, null));
		
		tester.assertConvertedResults(creatFloatList(10.0f, 20.0f, null));
	}
	private List<Float> creatFloatList(Float... values)
	{
		return AbstractConverterTester.createFloatList(values);
	}
	private class FloatConverterTester extends AbstractConverterTester<Float, Float>
	{
		@Override
		protected ValueModel<Float> createConverter(ValueModel<Float> source)
		{
			return Converters.createFloatConverter(source, MULTIPLIER);
		}
		@Override
		protected ValueModel<Float> createSourceValueModel(Float sourceValue)
		{
			return ValueHolders.create(sourceValue);
		}
	}
}
