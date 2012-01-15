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
package org.robobinding.customview;

import org.robobinding.viewattribute.AbstractCommandViewAttribute;
import org.robobinding.viewattribute.AbstractGroupedViewAttribute;
import org.robobinding.viewattribute.BindingAttributeMappings;
import org.robobinding.viewattribute.PropertyViewAttribute;

import android.view.View;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public interface CustomBindingAttributeMappings<T extends View> extends BindingAttributeMappings<T>
{
	<S extends View> void mapPropertyAttribute(S alternateView, Class<? extends PropertyViewAttribute<? extends View>> propertyViewAttributeClass, String attributeName);
	
	<S extends View> void mapCommandAttribute(S alternateView, Class<? extends AbstractCommandViewAttribute<? extends View>> commandViewAttributeClass, String attributeName);
	
	<S extends View> void mapGroupedAttribute(S alternateView, Class<? extends AbstractGroupedViewAttribute<? extends View>> groupedViewAttributeClass, String... attributeNames);
}
