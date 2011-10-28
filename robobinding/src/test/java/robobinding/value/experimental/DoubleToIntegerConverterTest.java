/**
 * DoubleToIntegerConverterTest.java
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

import robobinding.value.ValueModel;

/**
 * @since 1.0
 * @version $Revision:  $
 * @author Cheng Wei
 *
 */
public class DoubleToIntegerConverterTest extends AbstractConverterTest<Double, Integer>
{
	private static final double MULTIPLIER = 0.1;
	
	@Override
	protected AbstractConverterTester<Double, Integer> createTester()
	{
		return new DoubleToIntegerConverterTester();
	}
	@Override
	protected List<Double> createEmptySourceValues()
	{
		return creatDoubleList(null, null, null);
	}
	@Override
	protected List<Double> createSourceValues()
	{
		return creatDoubleList(100.0, 200.0, null);
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
	protected List<Double> createExpectedSourceValues()
	{
		return createSourceValues();
	}
	private List<Integer> creatIntegerList(Integer... values)
	{
		return AbstractConverterTester.createIntegerList(values);
	}
	private List<Double> creatDoubleList(Double... values)
	{
		return AbstractConverterTester.creatDoubleList(values);
	}
	private class DoubleToIntegerConverterTester extends AbstractConverterTester<Double, Integer>
	{
		@Override
		protected ValueModel<Integer> createConverter(ValueModel<Double> source)
		{
			return Converters.createDoubleToIntegerConverter(source, MULTIPLIER);
		}
		@Override
		protected ValueModel<Double> createSourceValueModel(Double sourceValue)
		{
			return ValueHolders.create(sourceValue);
		}
	}
}
