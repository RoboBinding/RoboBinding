package org.robobinding.viewattribute;

import java.util.List;

import org.robobinding.internal.com_google_common.collect.Lists;
import org.robobinding.viewattribute.AbstractCommandViewAttribute;
import org.robobinding.viewattribute.PropertyViewAttribute;
import org.robobinding.viewattribute.ViewAttribute;
import org.robobinding.viewattribute.ViewAttributeInstantiator;

import android.view.View;

public class ViewAttributeInjectorSpy extends ViewAttributeInstantiator
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