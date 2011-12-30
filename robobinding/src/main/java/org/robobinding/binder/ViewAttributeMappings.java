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

import java.util.List;

import org.robobinding.internal.com_google_common.collect.Lists;
import org.robobinding.viewattribute.CommandViewAttribute;
import org.robobinding.viewattribute.GroupedPropertyViewAttribute;
import org.robobinding.viewattribute.PropertyViewAttribute;

import android.view.View;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class ViewAttributeMappings<T extends View>
{
	private List<AbstractViewAttributeMapping<T>> viewAttributeMappings = Lists.newArrayList();
	
	ViewAttributeMappings(){}
	
	public void mapProperty(Class<? extends PropertyViewAttribute<T>> viewAttributeClass, String attributeName)
	{
		add(new PropertyViewAttributeMapping<T>(attributeName, viewAttributeClass));
	}
	public void mapCommand(Class<? extends CommandViewAttribute<T>> viewAttributeClasses, String attributeName)
	{
		add(new CommandViewAttributeMapping<T>(attributeName, viewAttributeClasses));
	}
	public void mapPropertyGroup(Class<? extends GroupedPropertyViewAttribute<T>> groupedViewAttributeClass, String... attributes)
	{
		add(new GroupedViewAttributeMapping<T>(groupedViewAttributeClass, attributes));		
	}
	private void add(AbstractViewAttributeMapping<T> mapping)
	{
		viewAttributeMappings.add(mapping);
	}
	void resolveAttributes(T view, BindingAttributeResolver bindingAttributeResolver, boolean preInitializeViews)
	{
		for (AbstractViewAttributeMapping<T> mapping : viewAttributeMappings)
			mapping.initializeAndResolveAttribute(view, bindingAttributeResolver, preInitializeViews);
	}
	
}
