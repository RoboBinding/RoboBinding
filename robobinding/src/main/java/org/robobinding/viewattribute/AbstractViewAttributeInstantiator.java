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

import org.robobinding.attribute.CommandAttribute;
import org.robobinding.attribute.ValueModelAttribute;
import org.robobinding.viewattribute.view.ViewListeners;
import org.robobinding.viewattribute.view.ViewListenersAware;

import android.view.View;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
public abstract class AbstractViewAttributeInstantiator
{
	protected final ViewListenersProvider viewListenersProvider;

	protected AbstractViewAttributeInstantiator(ViewListenersProvider viewListenersProvider)
	{
		this.viewListenersProvider = viewListenersProvider;
	}

	@SuppressWarnings("unchecked")
	public <PropertyViewAttributeType extends PropertyViewAttribute<? extends View>> PropertyViewAttributeType newPropertyViewAttribute(
			Class<PropertyViewAttributeType> propertyViewAttributeClass, ValueModelAttribute attribute)
	{
		PropertyViewAttributeType propertyViewAttribute = (PropertyViewAttributeType)newViewAttribute(propertyViewAttributeClass);
		return injectProperties(propertyViewAttribute, attribute);
	}

	@SuppressWarnings("unchecked")
	public <PropertyViewAttributeType extends PropertyViewAttribute<? extends View>> PropertyViewAttributeType injectProperties(
			PropertyViewAttributeType propertyViewAttribute, ValueModelAttribute attribute)
	{
		View view = getView();
		((PropertyViewAttribute<View>)propertyViewAttribute).setView(view);
		propertyViewAttribute.setAttribute(attribute);
		
		if (propertyViewAttribute instanceof AbstractMultiTypePropertyViewAttribute<?>)
			((AbstractMultiTypePropertyViewAttribute<?>)propertyViewAttribute).setViewListenersProvider(viewListenersProvider);
		
		setViewListenersIfRequired(propertyViewAttribute, view);
		return propertyViewAttribute;
	}

	@SuppressWarnings("unchecked")
	public <CommandViewAttributeType extends AbstractCommandViewAttribute<? extends View>> CommandViewAttributeType newCommandViewAttribute(
			Class<CommandViewAttributeType> commandViewAttributeClass, CommandAttribute attributeValue)
	{
		CommandViewAttributeType commandViewAttribute = (CommandViewAttributeType)newViewAttribute(commandViewAttributeClass);
		View view = getView();
		((AbstractCommandViewAttribute<View>)commandViewAttribute).setView(view);
		commandViewAttribute.setAttribute(attributeValue);
		setViewListenersIfRequired(commandViewAttribute, view);
		return commandViewAttribute;
	}
	
	protected void setViewListenersIfRequired(ViewAttribute viewAttribute, View view)
	{
		if(viewAttribute instanceof ViewListenersAware)
		{
			ViewListeners viewListeners = viewListenersProvider.forViewAndAttribute(view, (ViewListenersAware<?>)viewAttribute);
			@SuppressWarnings("unchecked")
			ViewListenersAware<ViewListeners> viewListenersAware = (ViewListenersAware<ViewListeners>)viewAttribute;
			viewListenersAware.setViewListeners(viewListeners);
		}
	}
	
	protected abstract View getView();
	
	protected ViewAttribute newViewAttribute(Class<? extends ViewAttribute> viewAttributeClass)
	{
		try
		{
			return viewAttributeClass.newInstance();
		} catch (InstantiationException e)
		{
			throw new RuntimeException("Attribute class " + viewAttributeClass.getName() + " does not have an empty default constructor");
		} catch (IllegalAccessException e)
		{
			throw new RuntimeException("Attribute class " + viewAttributeClass.getName() + " is not public");
		}
	}
}
