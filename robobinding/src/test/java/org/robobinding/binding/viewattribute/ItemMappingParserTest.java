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
package org.robobinding.binding.viewattribute;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import org.robobinding.binding.viewattribute.ItemMappingAttribute.ItemMappingParser;
import org.robobinding.binding.viewattribute.ItemMappingAttribute.ViewMappings;

import android.content.Context;
import android.content.res.Resources;

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
				.shouldMapTo(viewMapping("text1", TEXT_1_ID, "text", "{title}")),
			attribute("[text2.text:{artist}]")
				.shouldMapTo(viewMapping("text2", TEXT_2_ID, "text", "{artist}")),
			attribute("[text1.text:{title},text2.text:{artist}]")
				.shouldMapTo(viewMapping("text1", TEXT_1_ID, "text", "{title}"), viewMapping("text2", TEXT_2_ID, "text", "{artist}")),
			attribute("[text1.visibility:{titleVisible},text2.enabled:{artistEnabled}]")
				.shouldMapTo(viewMapping("text1", TEXT_1_ID, "visibility", "{titleVisible}"), viewMapping("text2", TEXT_2_ID, "enabled", "{artistEnabled}"))};

	@DataPoints
	public static String[] illegalAttributeValues = {
		"[text1.text: {title}", "text:{title}", "[text1.text:title]", "[text1.text:{title}, text2.text:{artist}]", "[text1.text:{title}],text2.text:{artist}]",
		"[text1.text:{title},text2..text:{artist}]"
	};
	
	private Context mockContext;
	private Resources mockResources;
	
	@Before
	public void setUp()
	{
		mockResources = mock(Resources.class);
		mockContext = mock(Context.class);
		when(mockContext.getResources()).thenReturn(mockResources);
	}
	
	@Theory
	public void shouldParseLegalAttributeValuesCorrectly(MappingExpectation mappingExpectation)
	{
		for (ViewMappingData viewMappingData : mappingExpectation.viewMappingData)
			when(mockResources.getIdentifier(viewMappingData.viewName, "id", "android")).thenReturn(viewMappingData.viewId);
		
		ItemMappingParser itemMappingParser = new ItemMappingParser();
		ViewMappings viewMappings = itemMappingParser.parse(mappingExpectation.attributeValue, mockContext);
		
		assertThat(viewMappings, equalTo(mappingExpectation.expectedViewMappings));
	}
	
	@Theory
	@Test (expected=RuntimeException.class)
	public void whenParsingAnIllegalAttributeValue_ThenReject(String illegalAttributeValue)
	{
		ItemMappingParser itemMappingParser = new ItemMappingParser();
		itemMappingParser.parse(illegalAttributeValue, mockContext);
	}
	
	@Test (expected=RuntimeException.class)
	public void givenALegalAttributeValue_WhenViewCantBeFound_ThenThrowException()
	{
		when(mockResources.getIdentifier("text1", "id", "android")).thenReturn(0);
		
		ItemMappingParser itemMappingParser = new ItemMappingParser();
		itemMappingParser.parse("[text1.text:{title}]", mockContext);
	}
	
	private static Attribute attribute(String attributeValue)
	{
		return new Attribute(attributeValue);
	}
	
	private static ViewMappingData viewMapping(String viewName, int viewId, String attributeName, String attributeValue)
	{
		return new ViewMappingData(viewName, viewId, attributeName, attributeValue);
	}
	
	private static class MappingExpectation
	{
		private final String attributeValue;
		private final ViewMappings expectedViewMappings;
		private final ViewMappingData[] viewMappingData;
		
		public MappingExpectation(String attributeValue, ViewMappingData[] viewMappings)
		{
			this.attributeValue = attributeValue;
			viewMappingData = viewMappings;
			this.expectedViewMappings = new ViewMappings();
			
			for (ViewMappingData viewMappingData : viewMappings)
				expectedViewMappings.add(viewMappingData.viewId, viewMappingData.attributeName, viewMappingData.attributeValue);
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
		private final String viewName;
		private final int viewId;
		private final String attributeName;
		private final String attributeValue;

		public ViewMappingData(String viewName, int viewId, String attributeName, String attributeValue)
		{
			this.viewName = viewName;
			this.viewId = viewId;
			this.attributeName = attributeName;
			this.attributeValue = attributeValue;
		}
		
	}
}
