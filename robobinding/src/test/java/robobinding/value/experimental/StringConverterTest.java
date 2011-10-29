/**
 * StringConverterTest.java
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

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;

import org.junit.Before;

import robobinding.property.PropertyValueModel;

/**
 * @since 1.0
 * @version $Revision:  $
 * @author Cheng Wei
 *
 */
public class StringConverterTest extends AbstractConverterTest<Long, String>
{
	private DecimalFormat format;
	@Before
	@Override
	public void setUp()
	{
		super.setUp();
		format = new DecimalFormat("#", new DecimalFormatSymbols(Locale.US));
		format.setParseIntegerOnly(true);
	}
	@Override
	protected AbstractConverterTester<Long, String> createTester()
	{
		return new StringConverterTester();
	}
	@Override
	protected List<Long> createEmptySourceValues()
	{
		return creatLongList(null, null, null);
	}
	@Override
	protected List<Long> createSourceValues()
	{
		return creatLongList(10L, 20L, null);
	}
	@Override
	protected List<String> creatExpectedConvertedValues()
	{
		return creatStringList("10", "20", null);
	}
	@Override
	protected List<String> createConvertedValues()
	{
		return creatExpectedConvertedValues();
	}
	@Override
	protected List<Long> createExpectedSourceValues()
	{
		return createSourceValues();
	}
	private List<Long> creatLongList(Long... values)
	{
		return AbstractConverterTester.createLongList(values);
	}
	private List<String> creatStringList(String... values)
	{
		return AbstractConverterTester.createStringList(values);
	}
	private class StringConverterTester extends AbstractConverterTester<Long, String>
	{
		@Override
		protected PropertyValueModel<String> createConverter(PropertyValueModel<Long> source)
		{
			return Converters.createStringConverter(source, format);
		}
		@Override
		protected PropertyValueModel<Long> createSourceValueModel(Long sourceValue)
		{
			return ValueHolders.create(sourceValue);
		}
	}
}
