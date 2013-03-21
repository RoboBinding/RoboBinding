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
	
	@SuppressWarnings("unchecked")
	public <ViewType extends View, PropertyViewAttributeType extends PropertyViewAttribute<ViewType>> PropertyViewAttributeType initializePropertyViewAttribute(
			PropertyViewAttributeType propertyViewAttribute, ValueModelAttribute attribute)
	{
		if(propertyViewAttribute instanceof AbstractMultiTypePropertyViewAttribute<?>)
		{
			return (PropertyViewAttributeType)initializeMultiTypePropertyViewAttribute((AbstractMultiTypePropertyViewAttribute<ViewType>)propertyViewAttribute, attribute);
		}else
		{
			return (PropertyViewAttributeType)initializePropertyViewAttribute((AbstractPropertyViewAttribute<ViewType, ?>)propertyViewAttribute, attribute);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private <ViewType extends View, PropertyViewAttributeType extends AbstractPropertyViewAttribute<ViewType, ?>> PropertyViewAttributeType initializePropertyViewAttribute(
			PropertyViewAttributeType viewAttribute, ValueModelAttribute attribute)
	{
		viewAttribute.initialize(new PropertyViewAttributeConfig(getView(), attribute));
		setViewListenersIfRequired(viewAttribute);
		return viewAttribute;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private <ViewType extends View, PropertyViewAttributeType extends AbstractMultiTypePropertyViewAttribute<ViewType>> PropertyViewAttributeType initializeMultiTypePropertyViewAttribute(
			PropertyViewAttributeType viewAttribute, ValueModelAttribute attribute)
	{
		viewAttribute.initialize(new MultiTypePropertyViewAttributeConfig(getView(), attribute, viewListenersProvider));
		return viewAttribute;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <ViewType extends View, CommandViewAttributeType extends AbstractCommandViewAttribute<ViewType>> CommandViewAttributeType initializeCommandViewAttribute(
			CommandViewAttributeType viewAttribute, CommandAttribute attribute)
	{
		viewAttribute.initialize(new CommandViewAttributeConfig(getView(), attribute));
		setViewListenersIfRequired(viewAttribute);
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
}
