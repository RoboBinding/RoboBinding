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

/**
 * @since 1.0
 * @version $Revision:  $
 * @author Cheng Wei
 *
 */
public class BooleanNegatorTest extends AbstractConverterTest<Boolean, Boolean>
{
	@Override
	protected AbstractConverterTester<Boolean, Boolean> createTester()
	{
		return new BooleanNegatorConverterTester();
	}
	@Override
	protected List<Boolean> createEmptySourceValues()
	{
		return creatBooleanList(null, null, null);
	}
	@Override
	protected List<Boolean> createSourceValues()
	{
		return creatBooleanList(false, true, null);
	}
	@Override
	protected List<Boolean> createConvertedValues()
	{
		return creatBooleanList(true, false, null);
	}
	@Override
	protected List<Boolean> creatExpectedConvertedValues()
	{
		return createConvertedValues();
	}
	@Override
	protected List<Boolean> createExpectedSourceValues()
	{
		return createSourceValues();
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
