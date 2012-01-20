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
package org.robobinding.viewattribute;

import java.util.Collection;

import org.robobinding.viewattribute.ViewAttribute;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class ViewAttributeUtils
{
	public static ViewAttribute findFirstViewAttributeOfType(
			Collection<ViewAttribute> viewAttributes, Class<? extends ViewAttribute> viewAttributeClass)
	{
		for (ViewAttribute viewAttribute : viewAttributes)
		{
			if (viewAttribute.getClass().isAssignableFrom(viewAttributeClass))
			{
				return viewAttribute;
			}
		}
		
		return null;
	}
}
