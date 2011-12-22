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
package org.robobinding.customwidget;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.robobinding.internal.com_google_common.collect.Lists;
import org.robobinding.internal.org_apache_commons_lang3.Validate;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class GroupedAttribute
{
	private String groupAttributeName;
	private List<String> compulsoryAttributes;
	private List<String> optionalAttributes;
	public GroupedAttribute(Builder builder)
	{
		validate(builder);
		
		this.groupAttributeName = builder.groupAttributeName;
		this.compulsoryAttributes = builder.compulsoryAttributes;
		this.optionalAttributes = builder.optionalAttributes;
	}
	public void validate(Builder builder)
	{
		Validate.notBlank(builder.groupAttributeName, "groupAttributeName is required");
		Validate.isTrue(!(builder.compulsoryAttributes.isEmpty() && builder.optionalAttributes.isEmpty()), "compulsory and optional attributes cannot be both empty");
		Validate.isTrue((builder.compulsoryAttributes.size()+builder.optionalAttributes.size())>1, "The number of compulsory and optional attributes should be more than 1"); 
	}
	public String getGroupAttributeName()
	{
		return groupAttributeName;
	}
	public Collection<String> getCompulsoryAttributes()
	{
		return Collections.unmodifiableCollection(compulsoryAttributes);
	}
	public Collection<String> getOptionalAttributes()
	{
		return Collections.unmodifiableCollection(optionalAttributes);
	}
	public static class Builder
	{
		private String groupAttributeName;
		private List<String> compulsoryAttributes;
		private List<String> optionalAttributes;
		public Builder()
		{
			compulsoryAttributes = Lists.newArrayList();
			optionalAttributes = Lists.newArrayList();
		}
		public Builder setGroupAttributeName(String groupAttributeName)
		{
			this.groupAttributeName = groupAttributeName;
			return this;
		}
		public Builder setCompulsoryAttributes(String... compulsoryAttributes)
		{
			this.compulsoryAttributes = Lists.newArrayList(compulsoryAttributes);
			return this;
		}
		public Builder setOptionalAttributes(String... optionalAttributes)
		{
			this.optionalAttributes = Lists.newArrayList(optionalAttributes);
			return this;
		}
		public GroupedAttribute create()
		{
			return new GroupedAttribute(this);
		}
	}
}
