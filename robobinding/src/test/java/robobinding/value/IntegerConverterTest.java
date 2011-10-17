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

/**
 * @since 1.0
 * @version $Revision:  $
 * @author Cheng Wei
 *
 */
public class IntegerConverterTest extends AbstractConverterTest<Integer, Integer>
{
	private static final double MULTIPLIER = 10;
	@Override
	protected AbstractConverterTester<Integer, Integer> createTester()
	{
		return new IntegerConverterTester();
	}
	@Override
	protected List<Integer> createEmptySourceValues()
	{
		return creatIntegerList(null, null, null);
	}
	@Override
	protected List<Integer> createSourceValues()
	{
		return creatIntegerList(1, 2, null);
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
	protected List<Integer> createExpectedSourceValues()
	{
		return createSourceValues();
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
