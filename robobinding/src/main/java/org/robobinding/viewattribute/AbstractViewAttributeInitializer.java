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
public abstract class AbstractViewAttributeInitializer
{
	protected final ViewListenersProvider viewListenersProvider;

	protected AbstractViewAttributeInitializer(ViewListenersProvider viewListenersProvider)
	{
		this.viewListenersProvider = viewListenersProvider;
	}
	
	public <PropertyViewAttributeType extends PropertyViewAttribute<? extends View>> PropertyViewAttributeType newPropertyViewAttribute(
			PropertyViewAttributeType propertyViewAttribute, ValueModelAttribute attribute)
	{
		if(MultiTypePropertyViewAttributeConfig.class.isAssignableFrom(propertyViewAttributeClass))
		{
			AbstractMultiTypePropertyViewAttribute<? extends View> viewAttribute = createMultiTypePropertyViewAttribute(
					(Class<AbstractMultiTypePropertyViewAttribute<? extends View>>)propertyViewAttributeClass, attribute);
			return (PropertyViewAttributeType)viewAttribute;
		}else
		{
			AbstractPropertyViewAttribute<? extends View, ?> viewAttribute = createPropertyViewAttribute(
					(Class<AbstractPropertyViewAttribute<? extends View, ?>>)propertyViewAttributeClass, attribute);
			return (PropertyViewAttributeType)viewAttribute;
		}
	}

	@SuppressWarnings("unchecked")
	private <PropertyViewAttributeType extends AbstractPropertyViewAttribute<? extends View, ?>> PropertyViewAttributeType createPropertyViewAttribute(
			Class<PropertyViewAttributeType> propertyViewAttributeClass, ValueModelAttribute attribute)
	{
		PropertyViewAttributeType viewAttribute = (PropertyViewAttributeType)newViewAttribute(propertyViewAttributeClass);
		viewAttribute.initialize(new PropertyViewAttributeConfig(getView(), attribute));
		setViewListenersIfRequired(viewAttribute);
		viewAttribute.postInitialization();
		return viewAttribute;
	}
	
	@SuppressWarnings("unchecked")
	private <PropertyViewAttributeType extends AbstractMultiTypePropertyViewAttribute<? extends View>> PropertyViewAttributeType createMultiTypePropertyViewAttribute(
			Class<PropertyViewAttributeType> propertyViewAttributeClass, ValueModelAttribute attribute)
	{
		PropertyViewAttributeType viewAttribute = (PropertyViewAttributeType)newViewAttribute(propertyViewAttributeClass);
		viewAttribute.initialize(new MultiTypePropertyViewAttributeConfig(getView(), attribute, viewListenersProvider));
		return viewAttribute;
	}

	@SuppressWarnings("unchecked")
	public <CommandViewAttributeType extends AbstractCommandViewAttribute<? extends View>> CommandViewAttributeType newCommandViewAttribute(
			Class<CommandViewAttributeType> commandViewAttributeClass, CommandAttribute attribute)
	{
		CommandViewAttributeType viewAttribute = (CommandViewAttributeType)newViewAttribute(commandViewAttributeClass);
		viewAttribute.initialize(new CommandViewAttributeConfig(getView(), attribute));
		setViewListenersIfRequired(viewAttribute);
		viewAttribute.postInitialization();
		return viewAttribute;
	}
	
	protected void setViewListenersIfRequired(ViewAttribute viewAttribute)
	{
		if(viewAttribute instanceof ViewListenersAware)
		{
			ViewListeners viewListeners = viewListenersProvider.forViewAndAttribute(getView(), (ViewListenersAware<?>)viewAttribute);
			@SuppressWarnings("unchecked")
			ViewListenersAware<ViewListeners> viewListenersAware = (ViewListenersAware<ViewListeners>)viewAttribute;
			viewListenersAware.setViewListeners(viewListeners);
		}
	}
	
	protected abstract View getView();
	
	protected <ViewAttributeConfigType extends AbstractViewAttributeConfig<View>> ViewAttribute newViewAttribute(Class<? extends ViewAttribute> viewAttributeClass)
	{
		try
		{
			return viewAttributeClass.newInstance();
		} catch (InstantiationException e)
		{
			throw new RuntimeException("Attribute class " + viewAttributeClass.getName() + " could not be instantiated: " + e);
		} catch (IllegalAccessException e)
		{
			throw new RuntimeException("Attribute class " + viewAttributeClass.getName() + " is not public");
		}
	}
}
