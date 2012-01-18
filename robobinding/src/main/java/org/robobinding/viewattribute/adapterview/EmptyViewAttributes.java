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



import android.view.View;
import android.widget.AdapterView;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class EmptyViewAttributes extends AbstractSubViewAttributes<AdapterView<?>>
{
	static final String EMPTY_VIEW_LAYOUT = "emptyViewLayout";
	static final String EMPTY_VIEW_PRESENTATION_MODEL = "emptyViewPresentationModel";
	static final String EMPTY_VIEW_VISIBILITY = "emptyViewVisibility";
	private EmptyViewVisibility emptyViewVisibility;
	
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
		emptyViewVisibility = new EmptyViewVisibility(view, emptyView);
		emptyViewVisibility.makeVisible();
	}

	@Override
	protected SubViewVisibilityAttribute createVisibilityAttribute(View emptyView)
	{
		return new SubViewVisibilityAttribute(emptyViewVisibility);
	}
}
