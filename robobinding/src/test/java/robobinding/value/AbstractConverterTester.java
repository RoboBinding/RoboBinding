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
package robobinding.value;

import java.util.List;

import org.junit.Assert;

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
	public void whenSettingSourceValues(List<S> values)
	{
		Assert.assertEquals(sources.size(), values.size());
		for(int i=0; i<sources.size(); i++)
		{
			S value = values.get(i);
			ValueModel<S> source = sources.get(i);
			source.setValue(value);
		}
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
	public void assertConvertedResults(List<D> expectedConvertedResults)
	{
		Assert.assertEquals(converters.size(), expectedConvertedResults.size());
		for(int i=0; i<converters.size(); i++)
		{
			ValueModel<D> converter = converters.get(i);
			D convertedResult = converter.getValue();
			Assert.assertEquals(expectedConvertedResults.get(i), convertedResult);
		}
	}

	public void whenApplyingConverters()
	{
		applyConverters();
	}
	public void givenSourceValues(List<S> sourceValues)
	{
		sources = Lists.newArrayList();
		for(S sourceValue : sourceValues)
		{
			sources.add(createSourceValueModel(sourceValue));
		}
	}
	protected abstract ValueModel<S> createSourceValueModel(S sourceValue);
	
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
