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
package org.robobinding.viewattribute.seekbar;

import java.util.List;

import org.robobinding.presentationmodel.PresentationModelAdapter;
import org.robobinding.viewattribute.AbstractGroupedViewAttribute;
import org.robobinding.viewattribute.ViewAttribute;
import org.robobinding.viewattribute.ViewAttributeInstantiator;

import android.content.Context;
import android.widget.SeekBar;

import com.google.common.collect.Lists;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class OnSeekBarChangeAttributes extends AbstractGroupedViewAttribute<SeekBar>
{
	public static final String ON_SEEK_BAR_CHANGE = "onSeekBarChange";
	public static final String PROGRESS = "progress";
	
	List<ViewAttribute> viewAttributes;

	@Override
	public void postInitialization()
	{
		viewAttributes = Lists.newArrayList();
		
		OnSeekBarChangeListeners onSeekBarChangeListeners = new OnSeekBarChangeListeners();
		
		ViewAttributeInstantiator<SeekBar> viewAttributeInstantiator = getViewAttributeInstantiator();
		if (groupedAttributeDetails.hasAttribute(PROGRESS))
		{
			TwoWayProgressAttribute twoWayProgressAttribute = viewAttributeInstantiator.newPropertyViewAttribute(TwoWayProgressAttribute.class, PROGRESS);
			twoWayProgressAttribute.setOnSeekBarChangeListeners(onSeekBarChangeListeners);
			viewAttributes.add(twoWayProgressAttribute);
		}
		if (groupedAttributeDetails.hasAttribute(ON_SEEK_BAR_CHANGE))
		{
			OnSeekBarChangeAttribute onSeekBarChangeAttribute = viewAttributeInstantiator.newCommandViewAttribute(OnSeekBarChangeAttribute.class, ON_SEEK_BAR_CHANGE);
			onSeekBarChangeAttribute.setOnSeekBarChangeListeners(onSeekBarChangeListeners);
			viewAttributes.add(onSeekBarChangeAttribute);
		}
		
		view.setOnSeekBarChangeListener(onSeekBarChangeListeners);
	}
	
	@Override
	public void bind(PresentationModelAdapter presentationModelAdapter, Context context)
	{
		for (ViewAttribute viewAttribute : viewAttributes)
			viewAttribute.bind(presentationModelAdapter, context);
	}

}
