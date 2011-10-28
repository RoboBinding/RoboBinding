/**
 * BooleanToStringConverterTest.java
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

import com.google.common.collect.Lists;

/**
 * @since 1.0
 * @version $Revision:  $
 * @author Cheng Wei
 *
 */
public class BooleanToStringConverterTest extends AbstractConverterTest<Boolean, String>
{
	private static final String TRUE_TEXT = "true";
	private static final String FALSE_TEXT = "false";
	private static final String NULL_TEXT = "unknown";
	@Override
	protected AbstractConverterTester<Boolean, String> createTester()
	{
		return new BooleanToStringConverterTester();
	}
	@Override
	protected List<Boolean> createEmptySourceValues()
	{
		return creatBooleanList(null, null, null);
	}
	@Override
	protected List<Boolean> createSourceValues()
	{
		return creatBooleanList(true, false, null);
	}
	@Override
	protected List<String> creatExpectedConvertedValues()
	{
		return createStringList(TRUE_TEXT, FALSE_TEXT, NULL_TEXT);
	}
	@Override
	protected List<String> createConvertedValues()
	{
		return creatExpectedConvertedValues();
	}
	@Override
	protected List<Boolean> createExpectedSourceValues()
	{
		return createSourceValues();
	}
	private List<String> createStringList(String... strs)
	{
		return Lists.newArrayList(strs);
	}
	private List<Boolean> creatBooleanList(Boolean... values)
	{
		return AbstractConverterTester.creatBooleanList(values);
	}
	private static class BooleanToStringConverterTester extends AbstractConverterTester<Boolean, String>
	{
		@Override
		protected ValueModel<String> createConverter(ValueModel<Boolean> source)
		{
			return Converters.createBooleanToStringConverter(source, TRUE_TEXT, FALSE_TEXT, NULL_TEXT);
		}
		@Override
		protected ValueModel<Boolean> createSourceValueModel(Boolean sourceValue)
		{
			return ValueHolders.create(sourceValue);
		}
	}
}
