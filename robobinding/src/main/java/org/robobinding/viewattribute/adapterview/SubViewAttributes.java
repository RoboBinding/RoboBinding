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
package org.robobinding.viewattribute.adapterview;

import static com.google.common.collect.Maps.newLinkedHashMap;
import static org.robobinding.attribute.ChildAttributeResolvers.staticResourceAttributeResolver;
import static org.robobinding.attribute.ChildAttributeResolvers.valueModelAttributeResolver;

import java.util.Map;

import org.robobinding.BindingContext;
import org.robobinding.attribute.AbstractAttribute;
import org.robobinding.attribute.ChildAttributeResolverMappings;
import org.robobinding.attribute.GroupedAttribute;
import org.robobinding.attribute.StaticResourceAttribute;
import org.robobinding.attribute.ValueModelAttribute;
import org.robobinding.viewattribute.AbstractGroupedViewAttribute;
import org.robobinding.viewattribute.ChildViewAttribute;
import org.robobinding.viewattribute.DependentChildViewAttributeBindingException;
import org.robobinding.viewattribute.ViewAttribute;

import android.view.View;
import android.widget.AdapterView;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class SubViewAttributes<T extends AdapterView<?>> extends AbstractGroupedViewAttribute<T>
{
	private View subView;
	private SubViewAttributesStrategy<T> subViewAttributesStrategy;
	private SubViewCreator subViewCreator;
	private int layoutId;

	public SubViewAttributes(SubViewAttributesStrategy<T> subViewAttributesStrategy, SubViewCreator subViewCreator)
	{
		this.subViewAttributesStrategy = subViewAttributesStrategy;
		this.subViewCreator = subViewCreator;
	}

	@Override
	protected String[] getCompulsoryAttributes()
	{
		return new String[] { layoutAttribute() };
	}

	@Override
	public void mapChildAttributeResolvers(ChildAttributeResolverMappings resolverMappings)
	{
		resolverMappings.map(staticResourceAttributeResolver(), layoutAttribute());
		resolverMappings.map(valueModelAttributeResolver(), subViewPresentationModel());
		resolverMappings.map(valueModelAttributeResolver(), visibilityAttribute());
	}

	@Override
	protected void setupChildAttributeBindings(ChildAttributeBindings childAttributeBindings)
	{
		DependentChildViewAttributes dependentChildViewAttributes = new DependentChildViewAttributes();
		dependentChildViewAttributes.add(layoutAttribute(), new SubViewLayoutAttribute());

		ViewAttribute subViewAttribute = groupedAttribute.hasAttribute(subViewPresentationModel()) ? new SubViewPresentationModelAttribute()
				: new SubViewWithoutPresentationModelAttribute();

		dependentChildViewAttributes.add(subViewPresentationModel(), subViewAttribute);

		if (groupedAttribute.hasAttribute(visibilityAttribute()))
		{
			dependentChildViewAttributes.add(visibilityAttribute(), new SubViewVisibilityAttribute(subViewAttributesStrategy.createVisibility(view, subView)));
		}

		childAttributeBindings.addDependentChildAttributes(dependentChildViewAttributes);
	}

	public static class DependentChildViewAttributes implements ViewAttribute
	{

		private Map<String, ViewAttribute> dependentAttributeMap = newLinkedHashMap();

		public void add(String attributeName, ViewAttribute viewAttribute)
		{
			dependentAttributeMap.put(attributeName, viewAttribute);
		}

		@SuppressWarnings({ "unchecked", "rawtypes" })
		public void setAttributes(GroupedAttribute groupedAttribute)
		{
			for (Map.Entry<String, ViewAttribute> dependentAttributeEntry : dependentAttributeMap.entrySet())
			{
				if (dependentAttributeEntry instanceof ChildViewAttribute)
				{
					AbstractAttribute attribute = groupedAttribute.attributeFor(dependentAttributeEntry.getKey());
					((ChildViewAttribute) dependentAttributeEntry).setAttribute(attribute);
				}
			}
		}

		@Override
		public void bindTo(BindingContext bindingContext)
		{
			for (Map.Entry<String, ViewAttribute> dependentAttributeEntry : dependentAttributeMap.entrySet())
			{
				try
				{
					dependentAttributeEntry.getValue().bindTo(bindingContext);
				} catch (RuntimeException e)
				{
					throw new DependentChildViewAttributeBindingException(dependentAttributeEntry.getKey());
				}
			}
		}
	}
	
	private class SubViewLayoutAttribute implements ChildViewAttribute<StaticResourceAttribute>
	{
		private StaticResourceAttribute attribute;
		
		@Override
		public void bindTo(BindingContext bindingContext)
		{
			layoutId = attribute.getResourceId(bindingContext.getContext());
		}
		
		@Override
		public void setAttribute(StaticResourceAttribute attribute)
		{
			this.attribute = attribute;
		}
	}
	
	private class SubViewPresentationModelAttribute implements ChildViewAttribute<ValueModelAttribute>
	{
		private ValueModelAttribute attribute;
		
		@Override
		public void bindTo(BindingContext bindingContext)
		{
			subView = subViewCreator.createAndBindTo(attribute, layoutId, bindingContext);
			subViewAttributesStrategy.addSubView(view, subView, bindingContext.getContext());
		}
		
		@Override
		public void setAttribute(ValueModelAttribute attribute)
		{
			this.attribute = attribute;
		}
	}
	
	private class SubViewWithoutPresentationModelAttribute implements ViewAttribute
	{
		@Override
		public void bindTo(BindingContext bindingContext)
		{
			subView = subViewCreator.create(layoutId, bindingContext);
			subViewAttributesStrategy.addSubView(view, subView, bindingContext.getContext());
		}
	}

	private String visibilityAttribute()
	{
		return subViewAttributesStrategy.visibilityAttribute();
	}

	private String subViewPresentationModel()
	{
		return subViewAttributesStrategy.subViewPresentationModelAttribute();
	}

	private String layoutAttribute()
	{
		return subViewAttributesStrategy.layoutAttribute();
	}
}