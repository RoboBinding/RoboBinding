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

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.lang3.reflect.ConstructorUtils;
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
		PropertyViewAttributeType viewAttribute = (PropertyViewAttributeType)newViewAttribute(propertyViewAttributeClass,
				PropertyViewAttributeConfig.class, 
				new PropertyViewAttributeConfig<View>(getView(), attribute));
		setViewListenersIfRequired(viewAttribute);
		return viewAttribute;
	}
	
	@SuppressWarnings("unchecked")
	private <PropertyViewAttributeType extends AbstractMultiTypePropertyViewAttribute<? extends View>> PropertyViewAttributeType createMultiTypePropertyViewAttribute(
			Class<PropertyViewAttributeType> propertyViewAttributeClass, ValueModelAttribute attribute)
	{
		PropertyViewAttributeType viewAttribute = (PropertyViewAttributeType)newViewAttribute(propertyViewAttributeClass,
				MultiTypePropertyViewAttributeConfig.class, 
				new MultiTypePropertyViewAttributeConfig<View>(getView(), attribute, viewListenersProvider));
		return viewAttribute;
	}

	@SuppressWarnings("unchecked")
	public <CommandViewAttributeType extends AbstractCommandViewAttribute<? extends View>> CommandViewAttributeType newCommandViewAttribute(
			Class<CommandViewAttributeType> commandViewAttributeClass, CommandAttribute attribute)
	{
		CommandViewAttributeType commandViewAttribute = (CommandViewAttributeType)newViewAttribute(commandViewAttributeClass,
				CommandViewAttributeConfig.class,
				new CommandViewAttributeConfig<View>(getView(), attribute));
		setViewListenersIfRequired(commandViewAttribute);
		return commandViewAttribute;
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
	
	protected <ViewAttributeConfigType extends AbstractViewAttributeConfig<View>> ViewAttribute newViewAttribute(Class<? extends ViewAttribute> viewAttributeClass, Class<ViewAttributeConfigType> configClass, ViewAttributeConfigType config)
	{
		try
		{
			return ConstructorUtils.invokeConstructor(viewAttributeClass, new Object[]{config}, new Class[]{configClass});
		} catch (InstantiationException e)
		{
			throw new RuntimeException("Attribute class " + viewAttributeClass.getName() + " could not be instantiated: " + e);
		} catch (IllegalAccessException e)
		{
			throw new RuntimeException("Attribute class " + viewAttributeClass.getName() + " is not public");
		} catch (NoSuchMethodException e)
		{
			throw new RuntimeException("Attribute class " + viewAttributeClass.getName() + " does not have a public constructor with single parameter of type '" + configClass.getName()+"'");
		} catch (InvocationTargetException e)
		{
			throw new RuntimeException(e);
		}
	}
}
