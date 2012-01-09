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
package org.robobinding.viewattribute;

import java.text.MessageFormat;
import java.util.Collection;

import org.robobinding.internal.org_apache_commons_lang3.StringUtils;

import android.view.View;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public abstract class AbstractGroupedViewAttribute<T extends View> implements ViewAttribute
{
	protected T view;
	protected boolean preInitializeViews;
	protected GroupedAttributeDetails groupedAttributeDetails;
	
	public void setView(T view)
	{
		this.view = view;
	}
	public void setPreInitializeViews(boolean preInitializeViews)
	{
		this.preInitializeViews = preInitializeViews;
	}
	public void setGroupedAttributeDetails(GroupedAttributeDetails groupedAttributeDetails)
	{
		this.groupedAttributeDetails = groupedAttributeDetails;
		initializeChildViewAttributes();
	}
	
	protected abstract void initializeChildViewAttributes();
	
	protected void assertAttributesArePresent(String... attributes)
	{
		if(!groupedAttributeDetails.hasAttributes(attributes))
		{
			Collection<String> missingAttributes = groupedAttributeDetails.findAbsentAttributes(attributes);
			throw new RuntimeException(MessageFormat.format("Property ''{0}'' of {1} has the following missing attributes ''{2}''",
					getClass().getName(), view.getClass().getName(), StringUtils.join(missingAttributes, ", ")));
		}
	}
}
