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
package robobinding.binding.viewattribute;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;

import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import robobinding.binding.viewattribute.ItemMappingAttribute.ItemMappingParser;
import robobinding.binding.viewattribute.ItemMappingAttribute.ViewMapping;
import robobinding.internal.com_google_common.collect.Lists;
import robobinding.internal.com_google_common.collect.Maps;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
@RunWith(Theories.class)
public class ItemMappingParserTest
{
	private final static int TEXT_1_ID = 10, TEXT_2_ID = 20;
	
	@DataPoints
	public static MappingExpectation[] mappingExpections = {
			attribute("[text1.text:{title}]")
				.shouldMapTo(viewMapping(TEXT_1_ID, "text", "{title}")),
			attribute("[text2.text:{artist}]")
				.shouldMapTo(viewMapping(TEXT_2_ID, "text", "{artist}")),
			attribute("[text1.text:{title}], text2.text:{artist}]")
				.shouldMapTo(viewMapping(TEXT_1_ID, "text", "{title}"), viewMapping(TEXT_2_ID, "text", "{artist}")),
			attribute("[text1.visibility:{titleVisible},text2.enabled:{artistEnabled}]")
				.shouldMapTo(viewMapping(TEXT_1_ID, "visibility", "{titleVisible}"), viewMapping(TEXT_2_ID, "enabled", "{artistEnabled}"))};

	@Theory
	public void shouldParseLegalAttributeValuesCorrectly(MappingExpectation mappingExpectation)
	{
		ItemMappingParser itemMappingParser = new ItemMappingParser();
		List<ViewMapping> viewMappings = itemMappingParser.parse(mappingExpectation.attributeValue);
		
		for (ViewMapping viewMapping : mappingExpectation.viewMappings)
			assertTrue(viewMappings.contains(viewMapping));
	}
	
	private static Attribute attribute(String attributeValue)
	{
		return new Attribute(attributeValue);
	}
	
	private static ViewMappingData viewMapping(int id, String attributeName, String attributeValue)
	{
		return new ViewMappingData(id, attributeName, attributeValue);
	}
	
	private static class MappingExpectation
	{
		private final String attributeValue;
		private final List<ViewMapping> viewMappings;
		
		public MappingExpectation(String attributeValue, ViewMappingData[] viewMappings)
		{
			this.attributeValue = attributeValue;
			this.viewMappings = Lists.newArrayList();
			
			for (ViewMappingData viewMappingData : viewMappings)
			{
				ViewMapping viewMapping = null;
				
				for (ViewMapping existingViewMapping : this.viewMappings)
				{
					if (existingViewMapping.viewId == viewMappingData.id)
					{
						viewMapping = existingViewMapping;
						break;
					}
				}
				
				if (viewMapping == null)
				{
					Map<String, String> attributes = Maps.newHashMap();
					attributes.put(viewMappingData.attributeName, viewMappingData.attributeValue);
					viewMapping = new ViewMapping(viewMappingData.id, attributes);
					this.viewMappings.add(viewMapping);
				}
				else
				{
					Map<String, String> attributes = viewMapping.bindingAttributes;
					attributes.put(viewMappingData.attributeName, viewMappingData.attributeValue);
				}
			}
		}
	}
	
	private static class Attribute
	{
		private final String attributeValue;

		public Attribute(String attributeValue)
		{
			this.attributeValue = attributeValue;
		}

		public MappingExpectation shouldMapTo(ViewMappingData... viewMappings)
		{
			return new MappingExpectation(attributeValue, viewMappings);
		}
	}
	
	private static class ViewMappingData
	{
		private final int id;
		private final String attributeName;
		private final String attributeValue;

		public ViewMappingData(int id, String attributeName, String attributeValue)
		{
			this.id = id;
			this.attributeName = attributeName;
			this.attributeValue = attributeValue;
		}
		
	}
}
