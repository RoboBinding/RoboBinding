/**
 * Copyright 2012 Cheng Wei, Robert Taylor
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
package org.robobinding.viewattribute.compoundbutton;

import java.util.List;

import org.robobinding.internal.com_google_common.collect.Lists;
import org.robobinding.presentationmodel.PresentationModelAdapter;
import org.robobinding.viewattribute.AbstractGroupedViewAttribute;
import org.robobinding.viewattribute.ViewAttribute;
import org.robobinding.viewattribute.ViewAttributeInstantiator;

import android.content.Context;
import android.widget.CompoundButton;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class OnCheckedChangeAttributes extends AbstractGroupedViewAttribute<CompoundButton>
{
	static final String CHECKED = "checked";
	static final String ON_CHECKED_CHANGE = "onCheckedChange";
	
	List<ViewAttribute> viewAttributes;

	@Override
	public void postInitialization()
	{
		viewAttributes = Lists.newArrayList();
		
		OnCheckedChangeListeners onCheckedChangeListeners = new OnCheckedChangeListeners();
		
		ViewAttributeInstantiator<CompoundButton> viewAttributeInstantiator = getViewAttributeInstantiator();
		if (groupedAttributeDetails.hasAttribute(CHECKED))
		{
			CheckedAttribute checkedAttribute = viewAttributeInstantiator.newPropertyViewAttribute(CheckedAttribute.class, CHECKED);
			checkedAttribute.setOnCheckedChangeListeners(onCheckedChangeListeners);
			viewAttributes.add(checkedAttribute);
		}
		if (groupedAttributeDetails.hasAttribute(ON_CHECKED_CHANGE))
		{
			OnCheckedChangeAttribute onCheckedChangeAttribute = viewAttributeInstantiator.newCommandViewAttribute(OnCheckedChangeAttribute.class, ON_CHECKED_CHANGE);
			onCheckedChangeAttribute.setOnCheckedChangeListeners(onCheckedChangeListeners);
			viewAttributes.add(onCheckedChangeAttribute);
		}
	}
	
	@Override
	public void bind(PresentationModelAdapter presentationModelAdapter, Context context)
	{
		for (ViewAttribute viewAttribute : viewAttributes)
			viewAttribute.bind(presentationModelAdapter, context);
	}
}
