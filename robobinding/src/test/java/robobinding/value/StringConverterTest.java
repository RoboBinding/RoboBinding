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
package robobinding.value;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;

import org.junit.Test;

/**
 * @since 1.0
 * @version $Revision:  $
 * @author Cheng Wei
 *
 */
public class StringConverterTest
{
	private DecimalFormat format;
	
	private StringConverterTester tester;
	public void setUp()
	{
		tester = new StringConverterTester();
		
		format = new DecimalFormat("#", new DecimalFormatSymbols(Locale.US));
		format.setParseIntegerOnly(true);
	}
	@Test
	public void givenSourceValues_whenApplyingConverters_thenConvertedResults()
	{
		tester.givenSourceValues(creatLongList(10L, 20L, null));
		
		tester.whenApplyingConverters();
		
		tester.assertConvertedResults(creatStringList("10", "20", null));
	}
	@Test
	public void givenEmptySourceValuesAndConverters_whenSettingSourceValues_thenConvertedResults()
	{
		tester.givenSourceValues(creatLongList(null, null, null));
		tester.applyConverters();
		
		tester.whenSettingSourceValues(creatLongList(10L, 20L, null));
		
		tester.assertConvertedResults(creatStringList("10", "20", null));
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
		protected ValueModel<String> createConverter(ValueModel<Long> source)
		{
			return Converters.createStringConverter(source, format);
		}
		@Override
		protected ValueModel<Long> createSourceValueModel(Long sourceValue)
		{
			return ValueHolders.create(sourceValue);
		}
	}
}
