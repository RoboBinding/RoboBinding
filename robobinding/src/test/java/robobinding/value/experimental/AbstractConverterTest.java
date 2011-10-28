/**
 * AbstractConverterTest.java
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

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @since 1.0
 * @version $Revision:  $
 * @author Cheng Wei
 *
 */
public abstract class AbstractConverterTest<S, D>
{
	private AbstractConverterTester<S, D> tester;
	@Before
	public void setUp()
	{
		tester = createTester();
	}
	protected abstract AbstractConverterTester<S, D> createTester();
	@Test
	public void givenSourceValues_whenApplyingConverters_thenExpectedConvertedValues()
	{
		tester.givenSourceValues(createSourceValues());
		
		tester.whenApplyingConverters();
		
		tester.assertExpectedConvertedValues(creatExpectedConvertedValues());
	}
	@Test
	public void givenEmptySourceValuesAndConverters_whenSettingSourceValues_thenExpectedConvertedValues()
	{
		tester.givenSourceValues(createEmptySourceValues());
		tester.applyConverters();
		
		tester.whenSettingSourceValues(createSourceValues());
		
		tester.assertExpectedConvertedValues(creatExpectedConvertedValues());
	}
	protected abstract List<S> createEmptySourceValues();
	protected abstract List<S> createSourceValues();
	protected abstract List<D> creatExpectedConvertedValues();
	@Test
	public void givenEmptySourceValuesAndConverters_whenSettingConvertedValues_thenExpectedSourceValues()
	{
		tester.givenSourceValues(createEmptySourceValues());
		tester.applyConverters();
		
		tester.whenSettingConvertedValues(createConvertedValues());
		
		tester.assertExpectedSourceValues(createExpectedSourceValues());
	}
	protected abstract List<S> createExpectedSourceValues();
	protected abstract List<D> createConvertedValues();
}
