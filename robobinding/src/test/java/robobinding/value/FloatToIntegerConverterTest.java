/**
 * FloatToIntegerConverterTest.java
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

/**
 * @since 1.0
 * @version $Revision:  $
 * @author Cheng Wei
 *
 */
public class FloatToIntegerConverterTest extends AbstractConverterTest<Float, Integer>
{
	private static final float MULTIPLIER = 100.0f;
	@Override
	protected AbstractConverterTester<Float, Integer> createTester()
	{
		return new FloatToIntegerConverterTester();
	}
	@Override
	protected List<Float> createEmptySourceValues()
	{
		return creatFloatList(null, null, null);
	}
	@Override
	protected List<Float> createSourceValues()
	{
		return creatFloatList(1.0f, 2.0f, null);
	}
	@Override
	protected List<Integer> creatExpectedConvertedValues()
	{
		return creatIntegerList(100, 200, null);
	}
	@Override
	protected List<Integer> createConvertedValues()
	{
		return creatExpectedConvertedValues();
	}
	@Override
	protected List<Float> createExpectedSourceValues()
	{
		return createSourceValues();
	}
	private List<Float> creatFloatList(Float... values)
	{
		return AbstractConverterTester.createFloatList(values);
	}
	private List<Integer> creatIntegerList(Integer... values)
	{
		return AbstractConverterTester.createIntegerList(values);
	}
	private class FloatToIntegerConverterTester extends AbstractConverterTester<Float, Integer>
	{
		@Override
		protected ValueModel<Integer> createConverter(ValueModel<Float> source)
		{
			return Converters.createFloatToIntegerConverter(source, MULTIPLIER);
		}
		@Override
		protected ValueModel<Float> createSourceValueModel(Float sourceValue)
		{
			return ValueHolders.create(sourceValue);
		}
	}
}
