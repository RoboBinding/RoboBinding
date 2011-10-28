/**
 * AbstractConverterTester.java
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

import robobinding.value.ValueModel;

import com.google.common.collect.Lists;

/**
 * @since 1.0
 * @version $Revision:  $
 * @author Cheng Wei
 *
 */
public abstract class AbstractConverterTester<S, D>
{
	private List<ValueModel<S>> sources;
	private List<ValueModel<D>> converters;
	public void givenSourceValues(List<S> sourceValues)
	{
		sources = Lists.newArrayList();
		for(S sourceValue : sourceValues)
		{
			sources.add(createSourceValueModel(sourceValue));
		}
	}
	protected abstract ValueModel<S> createSourceValueModel(S sourceValue);
	public void whenSettingSourceValues(List<S> values)
	{
		setValues(sources, values);
	}
	public void whenSettingConvertedValues(List<D> values)
	{
		setValues(converters, values);
	}
	private static <T> void setValues(List<ValueModel<T>> valueModels, List<T> values)
	{
		Assert.assertEquals(valueModels.size(), values.size());
		for(int i=0; i<valueModels.size(); i++)
		{
			T value = values.get(i);
			ValueModel<T> valueModel = valueModels.get(i);
			valueModel.setValue(value);
		}
	}
	public void whenApplyingConverters()
	{
		applyConverters();
	}
	public void applyConverters()
	{
		converters = Lists.newArrayList();
		for(ValueModel<S> source : sources)
		{
			converters.add(createConverter(source));
		}
	}
	protected abstract ValueModel<D> createConverter(ValueModel<S> source);
	public void assertExpectedConvertedValues(List<D> convertedValues)
	{
		assertValues(converters, convertedValues);
	}

	public void assertExpectedSourceValues(List<S> sourceValues)
	{
		assertValues(sources, sourceValues);
	}
	private static <T> void assertValues(List<ValueModel<T>> valueModels, List<T> expectedValues)
	{
		Assert.assertEquals(valueModels.size(), expectedValues.size());
		for(int i=0; i<valueModels.size(); i++)
		{
			ValueModel<T> valueModel = valueModels.get(i);
			T expectedValue = expectedValues.get(i);
			Assert.assertEquals(expectedValue, valueModel.getValue());
		}
	}
	public static List<Boolean> creatBooleanList(Boolean... values)
	{
		return Lists.newArrayList(values);
	}
	public static List<Double> creatDoubleList(Double... values)
	{
		return Lists.newArrayList(values);
	}
	public static List<Integer> createIntegerList(Integer... values)
	{
		return Lists.newArrayList(values);
	}
	public static List<Float> createFloatList(Float... values)
	{
		return Lists.newArrayList(values);
	}
	public static List<Long> createLongList(Long... values)
	{
		return Lists.newArrayList(values);
	}
	public static List<String> createStringList(String... values)
	{
		return Lists.newArrayList(values);
	}
}
