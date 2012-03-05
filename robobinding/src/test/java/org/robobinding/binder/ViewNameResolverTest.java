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

import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import org.robobinding.binding.ViewNameResolver;


/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
@RunWith(Theories.class)
public class ViewNameResolverTest
{
	@DataPoints
	public static ViewNameMapping[] viewNameMappings = { 
		layoutTagName("View").shouldResolveTo("android.view.View"),
		layoutTagName("ViewGroup").shouldResolveTo("android.view.ViewGroup"),
		layoutTagName("EditText").shouldResolveTo("android.widget.EditText"),
		layoutTagName("ListView").shouldResolveTo("android.widget.ListView"),
		layoutTagName("robobinding.widget.CustomWidget").shouldResolveTo("robobinding.widget.CustomWidget")
	};

	@Theory
	public void shouldResolveViewNamesCorrectly(ViewNameMapping viewNameMapping)
	{
		ViewNameResolver viewNameResolver = new ViewNameResolver();
		String viewName = viewNameResolver.getViewNameFromLayoutTag(viewNameMapping.tagName);
		assertThat(viewName, equalTo(viewNameMapping.expectedMapping));
	}
	
	
	private static class ViewNameMapping
	{
		private final String tagName;
		private final String expectedMapping;

		public ViewNameMapping(String tagName, String expectedMapping)
		{
			this.tagName = tagName;
			this.expectedMapping = expectedMapping;
		}
	}

	private static LayoutTagName layoutTagName(String tagName)
	{
		return new LayoutTagName(tagName);
	}
	
	private static class LayoutTagName
	{
		private final String tagName;

		public LayoutTagName(String tagName)
		{
			this.tagName = tagName;
		}

		public ViewNameMapping shouldResolveTo(String mapping)
		{
			return new ViewNameMapping(tagName, mapping);
		}

	}
	
}
