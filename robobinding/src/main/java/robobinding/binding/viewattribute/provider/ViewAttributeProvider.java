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
package robobinding.binding.viewattribute.provider;

import robobinding.binding.BindingAttribute;
import robobinding.binding.viewattribute.OnClickAttribute;
import robobinding.binding.viewattribute.VisibilityAttribute;
import android.view.View;

/**
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 *
 */
public class ViewAttributeProvider extends AbstractBindingAttributeProvider<View>
{
	@Override
	protected BindingAttribute getSupportedBindingAttribute(View view, String attributeName, String attributeValue)
	{
		if ("visibility".equals(attributeName))
		{
			return new BindingAttribute(attributeName, new VisibilityAttribute(view, attributeValue));
		}
		else if ("onClick".equals(attributeName))
		{
			return new BindingAttribute(attributeName, new OnClickAttribute(view, attributeValue));
		}
		
		return null;
	}
}
