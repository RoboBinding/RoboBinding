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

import java.util.List;

import org.robobinding.internal.com_google_common.collect.Lists;

import android.view.View;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public abstract class AbstractGroupedViewAttribute<S, T extends View> implements GroupedViewAttribute<S, T>
{
	protected T view;
	protected List<S> childViewAttributes;
	protected boolean preInitializeViews;
	
	@Override
	public void setView(T view)
	{
		this.view = view;
	}
	@Override
	public void setPreInitializeViews(boolean preInitializeViews)
	{
		this.preInitializeViews = preInitializeViews;
	}
	@Override
	public void setChildAttributes(List<S> childViewAttributes)
	{
		this.childViewAttributes = Lists.newArrayList();
		
		for (S childViewAttribute : childViewAttributes)
		{
			if (childViewAttribute != null)
				this.childViewAttributes.add(childViewAttribute);
		}
	}
}
