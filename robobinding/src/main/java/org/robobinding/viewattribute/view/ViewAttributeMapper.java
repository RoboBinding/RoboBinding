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

import org.robobinding.viewattribute.BindingAttributeMapper;
import org.robobinding.viewattribute.BindingAttributeMappings;

import android.view.View;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class ViewAttributeMapper implements BindingAttributeMapper<View> {
    @Override
    public void mapBindingAttributes(BindingAttributeMappings<View> mappings) {
	mappings.mapPropertyAttribute(ViewVisibilityAttribute.class, "visibility");
	mappings.mapPropertyAttribute(EnabledAttribute.class, "enabled");
	mappings.mapPropertyAttribute(BackgroundAttribute.class, "background");
	mappings.mapPropertyAttribute(BackgroundColorAttribute.class, "backgroundColor");
	mappings.mapPropertyAttribute(FocusableAttribute.class, "focusable");

	mappings.mapCommandAttribute(OnClickAttribute.class, "onClick");
	mappings.mapCommandAttribute(OnLongClickAttribute.class, "onLongClick");
	mappings.mapCommandAttribute(OnFocusChangeAttribute.class, "onFocusChange");
	mappings.mapCommandAttribute(OnFocusAttribute.class, "onFocus");
	mappings.mapCommandAttribute(OnFocusLostAttribute.class, "onFocusLost");
    }
}
