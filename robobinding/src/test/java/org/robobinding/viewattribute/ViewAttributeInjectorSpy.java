package org.robobinding.viewattribute;

import java.util.List;

import org.robobinding.internal.com_google_common.collect.Lists;

import android.view.View;

public class ViewAttributeInjectorSpy extends ViewAttributeInjector
{
	private List<Class<? extends ViewAttribute>> attributesInjected = Lists.newArrayList();
	
	@Override
	public <T extends View> void injectPropertyAttributeValues(PropertyViewAttribute<T> propertyViewAttribute, T view, String attributeValue, boolean preInitializeViews)
	{
		attributesInjected.add(propertyViewAttribute.getClass());
	}

	@Override
	public <T extends View> void injectCommandAttributeValues(AbstractCommandViewAttribute<T> commandViewAttribute, T view, String commandName)
	{
		attributesInjected.add(commandViewAttribute.getClass());
	}
	
	public boolean viewAttributeValuesInjected(Class<? extends ViewAttribute> viewAttributeClass)
	{
		return attributesInjected.contains(viewAttributeClass);
	}
}