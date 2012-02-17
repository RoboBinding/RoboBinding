/**
 * Copyright 2011 Cheng Wei, Robert Taylor
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
package org.robobinding.binder;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robobinding.binder.BindingAttributeProcessor.ViewAttributes;

import android.util.AttributeSet;
import android.view.View;

import com.google.common.collect.Maps;
import com.xtremelabs.robolectric.RobolectricTestRunner;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
@RunWith(RobolectricTestRunner.class)
public class BindingAttributesProcessorTest
{
	private static final View VIEW = new View(null);
	private AttributeSetParser attributeSetParser;
	
	@Before
	public void setUp()
	{
		attributeSetParser = mock(AttributeSetParser.class);
	}
	
	@Test
	public void givenOneBindingAttribute_whenLoading_thenReturnOneBindingAttributeInstance()
	{
		when(attributeSetParser.parse(any(AttributeSet.class))).thenReturn(singleBindingAttribute());
	
		BindingAttributeProcessor bindingAttributesProcessor = newBindingAttributesProcessor();
		ViewAttributes viewAttributes = bindingAttributesProcessor.read(VIEW, mock(AttributeSet.class));
		
		assertThat(viewAttributes.viewAttributes.size(), equalTo(1));
	}

	@Test
	public void givenXBindingAttributes_whenLoading_thenReturnXBindingAttributeInstances()
	{
		Map<String, String> multipleBindingAttributes = multipleBindingAttributes();
		int attributesCount = multipleBindingAttributes.size();
		when(attributeSetParser.parse(any(AttributeSet.class))).thenReturn(multipleBindingAttributes);
	
		BindingAttributeProcessor bindingAttributesProcessor = newBindingAttributesProcessor();
		ViewAttributes viewAttributes = bindingAttributesProcessor.read(VIEW, mock(AttributeSet.class));
		
		assertThat(viewAttributes.viewAttributes.size(), equalTo(attributesCount));
	}

	@Test (expected=RuntimeException.class)
	public void givenAnyUnsupportedBindingAttributes_whenLoading_thenThrowRuntimeException()
	{
		when(attributeSetParser.parse(any(AttributeSet.class))).thenReturn(unsupportedBindingAttributes());
		
		BindingAttributeProcessor bindingAttributesProcessor = newBindingAttributesProcessor();
		bindingAttributesProcessor.read(VIEW, mock(AttributeSet.class));
	}

	private Map<String, String> singleBindingAttribute()
	{
		Map<String, String> singleBindingAttribute = Maps.newHashMap();
		singleBindingAttribute.put("enabled", "{propertyName}");
		return singleBindingAttribute;
	}
	
	private Map<String, String> multipleBindingAttributes()
	{
		Map<String, String> multipleBindingAttributes = singleBindingAttribute();
		multipleBindingAttributes.put("visibility", "{propertyName}");
		multipleBindingAttributes.put("onClick", "{propertyName}");
		return multipleBindingAttributes;
	}
	
	private Map<String, String> unsupportedBindingAttributes()
	{
		Map<String, String> unsupportedBindingAttributes = singleBindingAttribute();
		unsupportedBindingAttributes.put("enabled", "{propertyName}");
		unsupportedBindingAttributes.put("unsupported", "{propertyName}");
		return unsupportedBindingAttributes;
	}

	private BindingAttributeProcessor newBindingAttributesProcessor()
	{
		return new BindingAttributeProcessor(attributeSetParser, false);
	}
	
}
