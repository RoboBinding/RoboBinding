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
package org.robobinding.viewattribute.listview;

import org.robobinding.viewattribute.adapterview.SubViewVisibility;

import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ListView;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class HeaderOrFooterVisibility implements SubViewVisibility
{
	private static LayoutParams ZERO_LAYOUT_PARAMS = new ListView.LayoutParams(0, 1);
	
	private View view;
	private LayoutParams originalLayoutParams;
	
	public HeaderOrFooterVisibility(View headerOrFooterView)
	{
		this.view = headerOrFooterView;
	}
	
	public void makeVisible()
	{
		if(originalLayoutParams != null)
		{
			view.setLayoutParams(originalLayoutParams);
		}
		view.setVisibility(View.VISIBLE);
	}
	
	public void makeGone()
	{
		if(originalLayoutParams == null)
		{
			originalLayoutParams = view.getLayoutParams();
		}
		view.setLayoutParams(ZERO_LAYOUT_PARAMS);
		view.setVisibility(View.GONE);
	}
	
	public final void setVisibility(int visibility)
	{
		if(View.VISIBLE == visibility)
		{
			makeVisible();
		}else if(View.INVISIBLE == visibility)
		{
			makeInvisible();
		}else
		{
			makeGone();
		}
	}
	
	void makeInvisible()
	{
		if(originalLayoutParams != null)
		{
			view.setLayoutParams(originalLayoutParams);
		}
		view.setVisibility(View.INVISIBLE);
	}
}
