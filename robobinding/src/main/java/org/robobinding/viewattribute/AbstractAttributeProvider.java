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
package org.robobinding.viewattribute;

import java.util.List;
import java.util.Map;

import org.robobinding.binder.BindingAttributeResolver;
import org.robobinding.customwidget.Attribute;
import org.robobinding.internal.com_google_common.collect.Lists;
import org.robobinding.presentationmodel.PresentationModelAdapter;

import android.content.Context;
import android.view.View;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public abstract class AbstractAttributeProvider<T extends View> implements BindingAttributeProvider<T>, GroupedAttributeProvider<T>
{
	@Override
	public void resolveSupportedBindingAttributes(T view, BindingAttributeResolver bindingAttributeResolver, boolean preInitializeViews)
	{
		ViewAttributeMappings<T> mappings = new ViewAttributeMappings<T>();
		populateViewAttributeMappings(mappings);
		mappings.process(view, bindingAttributeResolver, preInitializeViews);
	}
	
	public static class ViewAttributeMappings<T extends View>
	{
		private List<ViewAttributeMapping<T>> viewAttributeMappings = Lists.newArrayList();
		
		private void add(ViewAttributeMapping<T> mapping)
		{
			viewAttributeMappings.add(mapping);
		}
		private void process(T view, BindingAttributeResolver bindingAttributeResolver, boolean preInitializeViews)
		{
			for (ViewAttributeMapping<T> mapping : viewAttributeMappings)
				mapping.initializeAndResolveAttribute(view, bindingAttributeResolver, preInitializeViews);
		}
		public void addPropertyMapping(String attributeName, Class<? extends PropertyViewAttribute<?, T>> viewAttributeClass)
		{
			add(new PropertyViewAttributeMapping<T>(attributeName, viewAttributeClass));
		}
		
		public void addTypePropertyMapping(String attributeName, TypeMap... typeMap)
		{
			//add(new PropertyViewAttributeMapping<T>(attributeName, viewAttributeClass));
		}
		
		public void addCommandMapping(String attributeName, Class<? extends CommandViewAttribute<T>> viewAttributeClasses)
		{
			add(new CommandViewAttributeMapping<T>(attributeName, viewAttributeClasses));
		}
	}
	
	@Override
	public ViewAttribute createGroupedAttribute(Map<String, Attribute> attributes, Class<GroupedViewAttribute<T>> attributeClass)
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	protected PropertyViewAttribute<?, ?> withCompulsoryAttribute(String attributeName, Class<PropertyViewAttribute<?, ?>> propertyViewAttributeClass)
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	public static class TypeMap<S, T extends View>
	{
		private final Class<S> propertyTypeClass;
		private final Class<? extends PropertyViewAttribute<S, T>> propertyAttributeClass;

		public TypeMap(Class<S> propertyTypeClass, Class<? extends PropertyViewAttribute<S, T>> propertyAttributeClass)
		{
			this.propertyTypeClass = propertyTypeClass;
			this.propertyAttributeClass = propertyAttributeClass;
		}
		
	}
	
	protected <S> TypeMap typeMap(Class<S> propertyTypeClass, Class<? extends PropertyViewAttribute<S, T>> propertyAttributeClass)
	{
		return new TypeMap<S, T>(propertyTypeClass, propertyAttributeClass);
	}
	
	public abstract static class ViewAttributeMapping<T extends View>
	{
		public abstract void initializeAndResolveAttribute(T view, BindingAttributeResolver bindingAttributeResolver, boolean preInitializeViews);
	}
	
	public static class CommandViewAttributeMapping<T extends View> extends ViewAttributeMapping<T>
	{
		private String attributeName;
		private Class<? extends CommandViewAttribute<T>> commandViewAttributeClass;
		public CommandViewAttributeMapping(String attributeName, Class<? extends CommandViewAttribute<T>> commandViewAttributeClass)
		{
			this.attributeName = attributeName;
			this.commandViewAttributeClass = commandViewAttributeClass;
		}

		@SuppressWarnings("unchecked")
		public void initializeAndResolveAttribute(T view, BindingAttributeResolver bindingAttributeResolver, boolean preInitializeViews)
		{
			ViewAttribute viewAttribute = null;
			
			try
			{
				viewAttribute = commandViewAttributeClass.newInstance();
			} catch (InstantiationException e)
			{
				// TODO Give your view attribute a default constructor!
				e.printStackTrace();
			} catch (IllegalAccessException e)
			{
				// TODO Make your view attribute class public!
				e.printStackTrace();
			}
			
			String attributeValue = bindingAttributeResolver.findAttributeValue(attributeName);
			
			CommandViewAttribute<T> commandViewAttribute = (CommandViewAttribute<T>)viewAttribute;
			commandViewAttribute.setView(view);
			commandViewAttribute.setCommandName(attributeValue);
		}
	}
	
	public static class PropertyViewAttributeMapping<T extends View> extends ViewAttributeMapping<T>
	{
		private String attributeName;
		private Class<? extends PropertyViewAttribute<?, T>> viewAttributeClass;
		public PropertyViewAttributeMapping(String attributeName, Class<? extends PropertyViewAttribute<?, T>> viewAttributeClass)
		{
			this.attributeName = attributeName;
			this.viewAttributeClass = viewAttributeClass;
		}

		@SuppressWarnings("unchecked")
		public void initializeAndResolveAttribute(T view, BindingAttributeResolver bindingAttributeResolver, boolean preInitializeViews)
		{
			ViewAttribute viewAttribute = null;
			
			try
			{
				viewAttribute = viewAttributeClass.newInstance();
			} catch (InstantiationException e)
			{
				// TODO Give your view attribute a default constructor!
				e.printStackTrace();
			} catch (IllegalAccessException e)
			{
				// TODO Make your view attribute class public!
				e.printStackTrace();
			}
			
			String attributeValue = bindingAttributeResolver.findAttributeValue(attributeName);
			
			PropertyViewAttribute<?, T> propertyViewAttribute = (PropertyViewAttribute<?, T>)viewAttribute;
			propertyViewAttribute.setView(view);
			propertyViewAttribute.setPropertyName(attributeValue);
			propertyViewAttribute.setPreInitializeViews(preInitializeViews);
		}
	}
	
	private static class ViewAttributeDelegate<S, T extends View> implements PropertyViewAttribute<S, T>
	{
		private T view;
		private String propertyName;
		private boolean preInitializeViews;
		private Class<? extends PropertyViewAttribute<S, T>>[] viewAttributeClasses;

		public ViewAttributeDelegate(Class<? extends PropertyViewAttribute<S, T>>[] viewAttributeClasses)
		{
			this.viewAttributeClasses = viewAttributeClasses;
		}

		@Override
		public void bind(PresentationModelAdapter presentationModelAdapter, Context context)
		{
			Class<?> propertyType = presentationModelAdapter.getPropertyType(propertyName);
			
			for (Class<? extends PropertyViewAttribute<S, T>> viewAttributeClass : viewAttributeClasses)
			{
				//Probably better if the user supplies the class somehow, then we don't have to instantiate the class to check it's type. Or is their another way?
				propertyType.isAssignableFrom(viewAttributeClass);
			}
		}

		@Override
		public void setView(T view)
		{
			this.view = view;
		}

		@Override
		public void setPropertyName(String propertyName)
		{
			this.propertyName = propertyName;
		}

		@Override
		public void setPreInitializeViews(boolean preInitializeViews)
		{
			this.preInitializeViews = preInitializeViews;
		}

		@Override
		public Class<S> getPropertyType()
		{
			throw new UnsupportedOperationException();
		}

	}
	
	protected abstract void populateViewAttributeMappings(ViewAttributeMappings<T> mappings);

}
