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
package org.robobinding.viewattribute.adapterview;


import org.robobinding.viewattribute.listview.SubViewVisibilityAttribute;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class EmptyViewAttributes extends AbstractSubViewAttributes<AdapterView<?>> implements SubViewVisibility
{
	static final String EMPTY_VIEW_LAYOUT = "emptyViewLayout";
	static final String EMPTY_VIEW_PRESENTATION_MODEL = "emptyViewPresentationModel";
	static final String EMPTY_VIEW_VISIBILITY = "emptyViewVisibility";
	private View emptyView;
	
	@Override
	protected String layoutAttribute()
	{
		return EMPTY_VIEW_LAYOUT;
	}

	@Override
	protected String subViewPresentationModelAttribute()
	{
		return EMPTY_VIEW_PRESENTATION_MODEL;
	}

	@Override
	protected String visibilityAttribute()
	{
		return EMPTY_VIEW_VISIBILITY;
	}

	@Override
	protected void addSubView(View emptyView)
	{
		this.emptyView = emptyView;
		makeVisible();
	}

	@Override
	protected SubViewVisibilityAttribute createVisibilityAttribute(View emptyView)
	{
		return new SubViewVisibilityAttribute(this);
	}
	
	@Override
	public void makeVisible()
	{
		if (view.getEmptyView() == emptyView)
			return;
		
		ViewGroup viewGroupParent = (ViewGroup)view.getParent();
		viewGroupParent.addView(emptyView);
		view.setEmptyView(emptyView);
	}

	@Override
	public void makeGone()
	{
		if (view.getEmptyView() == null)
			return;
		
		ViewGroup viewGroupParent = (ViewGroup)view.getParent();
		viewGroupParent.removeView(emptyView);
		view.setEmptyView(null);
	}

	@Override
	public void setVisibility(int visibility)
	{
		if(View.VISIBLE == visibility)
		{
			makeVisible();
		}
		else
		{
			makeGone();
		}
	}
}
