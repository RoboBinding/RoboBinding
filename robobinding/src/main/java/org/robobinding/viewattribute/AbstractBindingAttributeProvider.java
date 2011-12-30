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

import org.robobinding.binder.BindingAttributeResolver;
import org.robobinding.customwidget.Attribute;
import org.robobinding.internal.com_google_common.collect.Lists;
import org.robobinding.presentationmodel.PresentationModelAdapter;
import org.robobinding.viewattribute.adapterview.AdaptedDataSetAttributes;

import android.content.Context;
import android.view.View;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public abstract class AbstractBindingAttributeProvider<T extends View> implements BindingAttributeProvider<T>
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
		private List<AbstractViewAttributeMapping<T>> viewAttributeMappings = Lists.newArrayList();
		private List<TypeMap<?, T>> typeDependentMappings = Lists.newArrayList();
		
		private void add(AbstractViewAttributeMapping<T> mapping)
		{
			viewAttributeMappings.add(mapping);
		}
		private void process(T view, BindingAttributeResolver bindingAttributeResolver, boolean preInitializeViews)
		{
			for (AbstractViewAttributeMapping<T> mapping : viewAttributeMappings)
				mapping.initializeAndResolveAttribute(view, bindingAttributeResolver, preInitializeViews);
		}
		public void addPropertyMapping(String attributeName, Class<? extends PropertyViewAttribute<?, T>> viewAttributeClass)
		{
			add(new PropertyViewAttributeMapping<T>(attributeName, viewAttributeClass));
		}
		
		public <S> TypeMap<S, T> createTypeMap(Class<S> propertyTypeClass, Class<? extends PropertyViewAttribute<S, T>> propertyAttributeClass)
		{
			return new TypeMap<S, T>(propertyTypeClass, propertyAttributeClass);
		}
		
		public void addTypeDependentPropertyMappings(String attributeName, @SuppressWarnings("rawtypes") TypeMap... typeMaps)
		{
			//ViewAttributeDelegate<?, T> viewAttributeDelegate = new ViewAttributeDelegate<?, T>(typeMaps);
			//add(new PropertyViewAttributeMapping<T>(attributeName, viewAttributeClass));
		}
		
		public void addCommandMapping(String attributeName, Class<? extends CommandViewAttribute<T>> viewAttributeClasses)
		{
			add(new CommandViewAttributeMapping<T>(attributeName, viewAttributeClasses));
		}
		
		public void addGroupedMapping(Class<? extends GroupedViewAttribute<T>> class1, String... attributes)
		{
			add(new GroupedViewAttributeMapping(class1, attributes));		
		}
	}
	
	public static class GroupedViewAttributeMapping<T extends View> extends AbstractViewAttributeMapping<T>
	{
		private final Class<? extends GroupedViewAttribute<T>> attributeClass;
		private final String[] attributeNames;
		
		public GroupedViewAttributeMapping(Class<? extends GroupedViewAttribute<T>> attributeClass, String[] attributeNames)
		{
			this.attributeClass = attributeClass;
			this.attributeNames = attributeNames;
		}

		@Override
		public void initializeAndResolveAttribute(T view, BindingAttributeResolver bindingAttributeResolver, boolean preInitializeViews)
		{
			if (bindingAttributeResolver.hasOneOfAttributes(attributeNames))
			{
				GroupedViewAttribute<T> groupedViewAttribute = createNewInstance(attributeClass);
				List<Attribute> attributes = Lists.newArrayList();
				
				for (String name : attributeNames)
				{
					Attribute attribute = new Attribute(name, bindingAttributeResolver.findAttributeValue(name));
					attributes.add(attribute);
				}
				
				groupedViewAttribute.setChildAttributes(attributes);
				
				bindingAttributeResolver.resolveAttributes(attributeNames, viewAttribute);
				//Supply it to the GroupedAttribute. GroupedAttribute can inspect it and reject/accept as he sees fit
			}
		}
	}
	
	public abstract static class AbstractViewAttributeMapping<T extends View>
	{
		public abstract void initializeAndResolveAttribute(T view, BindingAttributeResolver bindingAttributeResolver, boolean preInitializeViews);
	
		protected <S> S createNewInstance(Class<S> clazz)
		{
			try
			{
				S newInstance = clazz.newInstance();
				return newInstance;
			} catch (InstantiationException e)
			{
				// TODO Give your view attribute a default constructor!
				e.printStackTrace();
			} catch (IllegalAccessException e)
			{
				// TODO Make your view attribute class public!
				e.printStackTrace();
			}
			
			return null;
		}
	}
	
	public static class CommandViewAttributeMapping<T extends View> extends AbstractViewAttributeMapping<T>
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
			ViewAttribute viewAttribute = createNewInstance(commandViewAttributeClass);
			
			String attributeValue = bindingAttributeResolver.findAttributeValue(attributeName);
			
			CommandViewAttribute<T> commandViewAttribute = (CommandViewAttribute<T>)viewAttribute;
			commandViewAttribute.setView(view);
			commandViewAttribute.setCommandName(attributeValue);
		}
	}
	
	public static class PropertyViewAttributeMapping<T extends View> extends AbstractViewAttributeMapping<T>
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
			ViewAttribute viewAttribute = createNewInstance(viewAttributeClass);
			
			String attributeValue = bindingAttributeResolver.findAttributeValue(attributeName);
			
			PropertyViewAttribute<?, T> propertyViewAttribute = (PropertyViewAttribute<?, T>)viewAttribute;
			propertyViewAttribute.setView(view);
			propertyViewAttribute.setPropertyName(attributeValue);
			propertyViewAttribute.setPreInitializeViews(preInitializeViews);
		}
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
	
	public static class ViewAttributeDelegate<S, T extends View> implements PropertyViewAttribute<S, T>
	{
		private T view;
		private String propertyName;
		private boolean preInitializeViews;
		private TypeMap<S, T>[] typeMaps;

		public ViewAttributeDelegate(TypeMap<S, T>[] typeMaps)
		{
			this.typeMaps = typeMaps;
		}

		@Override
		public void bind(PresentationModelAdapter presentationModelAdapter, Context context)
		{
			Class<?> propertyType = presentationModelAdapter.getPropertyType(propertyName);

			for (TypeMap<S, T> typeMap : typeMaps)
			{
				if (typeMap.propertyTypeClass.isAssignableFrom(propertyType))
				{
					try
					{
						PropertyViewAttribute<S, T> newInstance = typeMap.propertyAttributeClass.newInstance();
						newInstance.setView(view);
						newInstance.setPropertyName(propertyName);
						newInstance.setPreInitializeViews(preInitializeViews);
						return;
					} catch (InstantiationException e)
					{
						// TODO Give your view attribute a default constructor!
						e.printStackTrace();
					} catch (IllegalAccessException e)
					{
						// TODO Make your view attribute class public!
						e.printStackTrace();
					}
				}
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

	}

	public static class GroupChildMapping
	{
		public String attributeName;
		public Class<? extends ChildViewAttribute> attributeClass;
		public GroupChildMapping(String attributeName, Class<? extends ChildViewAttribute> attributeClass)
		{
			this.attributeName = attributeName;
			this.attributeClass = attributeClass;
		}
	}
	
	protected abstract void populateViewAttributeMappings(ViewAttributeMappings<T> mappings);

}
