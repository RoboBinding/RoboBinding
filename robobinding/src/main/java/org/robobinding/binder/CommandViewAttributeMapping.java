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
package org.robobinding.binder;

import org.robobinding.viewattribute.CommandViewAttribute;

import android.view.View;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
class CommandViewAttributeMapping<T extends View> extends AbstractViewAttributeMapping<T>
{
	private String attributeName;
	private Class<? extends CommandViewAttribute<T>> commandViewAttributeClass;
	
	public CommandViewAttributeMapping(String attributeName, Class<? extends CommandViewAttribute<T>> commandViewAttributeClass)
	{
		this.attributeName = attributeName;
		this.commandViewAttributeClass = commandViewAttributeClass;
	}

	public void initializeAndResolveAttribute(T view, BindingAttributeResolver bindingAttributeResolver, boolean preInitializeViews)
	{
		if (bindingAttributeResolver.hasAttribute(attributeName))
		{
			String attributeValue = bindingAttributeResolver.findAttributeValue(attributeName);
			
			CommandViewAttribute<T> commandViewAttribute = createNewInstance(commandViewAttributeClass);
			commandViewAttribute.setView(view);
			commandViewAttribute.setCommandName(attributeValue);
			
			bindingAttributeResolver.resolveAttribute(attributeValue, commandViewAttribute);
		}
	}
}