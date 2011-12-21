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
package org.robobinding.viewattribute.listview;

import org.robobinding.binding.viewattribute.AbstractReadOnlyPropertyViewAttribute;

import android.view.View;
import android.widget.ListView;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class FooterVisibilityAttribute extends AbstractReadOnlyPropertyViewAttribute<Boolean>
{
	private ListView listView;
	private View footerView;

	public FooterVisibilityAttribute(ListView listView, View footerView, String attributeValue)
	{
		super(attributeValue, false);
		this.listView = listView;
		this.footerView = footerView;
	}

	@Override
	protected void valueModelUpdated(Boolean newValue)
	{
		if(newValue)
		{
			makeVisible();
		}else
		{
			makeGone();
		}
	}
	private void makeVisible()
	{
		if(listView.getFooterViewsCount() == 0)
		{
			listView.addFooterView(footerView);
		}
	}
	private void makeGone()
	{
		if(listView.getFooterViewsCount()>0)
		{
			listView.removeFooterView(footerView);
		}
	}
}
