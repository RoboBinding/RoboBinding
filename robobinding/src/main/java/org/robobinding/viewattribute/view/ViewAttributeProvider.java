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
package org.robobinding.viewattribute.view;

import org.robobinding.viewattribute.ViewAttributeMappings;
import org.robobinding.viewattribute.WidgetViewAttributeProvider;

import android.view.View;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class ViewAttributeProvider implements WidgetViewAttributeProvider<View>
{
	@Override
	public void populateViewAttributeMappings(ViewAttributeMappings<View> mappings)
	{
		mappings.mapProperty(VisibilityAttribute.class, "visibility");
		mappings.mapProperty(EnabledAttribute.class, "enabled");
		mappings.mapProperty(BackgroundAttribute.class, "background");
		mappings.mapProperty(BackgroundColorAttribute.class, "backgroundColor");
		
		mappings.mapCommand(OnClickAttribute.class, "onClick");
		mappings.mapCommand(OnLongClickAttribute.class, "onLongClick");
		mappings.mapCommand(OnFocusChangeAttribute.class, "onFocusChange");
		mappings.mapCommand(OnFocusAttribute.class, "onFocus");
		mappings.mapCommand(OnFocusLostAttribute.class, "onFocusLost");
	}
}
