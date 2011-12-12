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
package org.robobinding.binding.viewattribute.provider;

import org.robobinding.binding.viewattribute.BackgroundColorAttribute;
import org.robobinding.binding.viewattribute.EnabledAttribute;
import org.robobinding.binding.viewattribute.OnClickAttribute;
import org.robobinding.binding.viewattribute.OnFocusChangeAttribute;
import org.robobinding.binding.viewattribute.OnLongClickAttribute;
import org.robobinding.binding.viewattribute.VisibilityAttribute;
import org.robobinding.binding.viewattribute.provider.BindingAttributeProvider;
import org.robobinding.binding.viewattribute.provider.ViewAttributeProvider;


import android.view.View;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class ViewAttributeProviderTest extends AbstractBindingAttributeProviderTest<View>
{
	@Override
	protected BindingAttributeProvider<View> getBindingAttributeProvider()
	{
		return new ViewAttributeProvider();
	}

	@Override
	protected View createNewViewInstance()
	{
		return new View(null);
	}

	@Override
	protected void populateAttributeClassMappings(AttributeClassMappings attributeClassMappings)
	{
		attributeClassMappings.add("visibility", VisibilityAttribute.class);
		attributeClassMappings.add("enabled", EnabledAttribute.class);
		attributeClassMappings.add("onClick", OnClickAttribute.class);
		attributeClassMappings.add("backgroundColor", BackgroundColorAttribute.class);
		attributeClassMappings.add("onLongClick", OnLongClickAttribute.class);
		attributeClassMappings.add("onFocusChange", OnFocusChangeAttribute.class);
		attributeClassMappings.add("onFocus", OnFocusChangeAttribute.class);
		attributeClassMappings.add("onFocusLost", OnFocusChangeAttribute.class);
	}
}
