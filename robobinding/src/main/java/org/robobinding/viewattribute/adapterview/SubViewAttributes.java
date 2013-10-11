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

import static org.robobinding.attribute.ChildAttributeResolvers.staticResourceAttributeResolver;
import static org.robobinding.attribute.ChildAttributeResolvers.valueModelAttributeResolver;

import org.robobinding.BindingContext;
import org.robobinding.ViewBinder;
import org.robobinding.attribute.ChildAttributeResolverMappings;
import org.robobinding.attribute.StaticResourceAttribute;
import org.robobinding.attribute.ValueModelAttribute;
import org.robobinding.viewattribute.AbstractGroupedViewAttribute;
import org.robobinding.viewattribute.ChildViewAttribute;
import org.robobinding.viewattribute.ChildViewAttributeWithAttribute;
import org.robobinding.viewattribute.ChildViewAttributes;

import android.view.View;
import android.widget.AdapterView;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class SubViewAttributes<T extends AdapterView<?>> extends AbstractGroupedViewAttribute<T> {
    private View subView;
    private SubViewAttributesStrategy<T> subViewAttributesStrategy;
    private int layoutId;

    public SubViewAttributes(SubViewAttributesStrategy<T> subViewAttributesStrategy) {
	this.subViewAttributesStrategy = subViewAttributesStrategy;
    }

    @Override
    public String[] getCompulsoryAttributes() {
	return new String[] {layoutAttribute()};
    }

    @Override
    public void mapChildAttributeResolvers(ChildAttributeResolverMappings resolverMappings) {
	resolverMappings.map(staticResourceAttributeResolver(), layoutAttribute());
	resolverMappings.map(valueModelAttributeResolver(), subViewPresentationModel());
	resolverMappings.map(valueModelAttributeResolver(), visibilityAttribute());
    }

    @Override
    protected void setupChildViewAttributes(ChildViewAttributes<T> childViewAttributes, BindingContext bindingContext) {
	childViewAttributes.failOnFirstBindingError();

	childViewAttributes.add(layoutAttribute(), new SubViewLayoutAttribute());

	ChildViewAttribute subViewAttribute = childViewAttributes.hasAttribute(subViewPresentationModel()) ? new SubViewPresentationModelAttribute()
		: new SubViewWithoutPresentationModelAttribute();
	childViewAttributes.add(subViewPresentationModel(), subViewAttribute);

	if (childViewAttributes.hasAttribute(visibilityAttribute())) {
	    //subView is null.
	    childViewAttributes.add(visibilityAttribute(), new SubViewVisibilityAttribute(subViewAttributesStrategy.createVisibility(view, subView)));
	}
    }

    private class SubViewLayoutAttribute implements ChildViewAttributeWithAttribute<StaticResourceAttribute> {
	private StaticResourceAttribute attribute;

	@Override
	public void bindTo(BindingContext bindingContext) {
	    layoutId = attribute.getResourceId(bindingContext.getContext());
	}

	@Override
	public void setAttribute(StaticResourceAttribute attribute) {
	    this.attribute = attribute;
	}
    }

    private class SubViewPresentationModelAttribute implements ChildViewAttributeWithAttribute<ValueModelAttribute> {
	private ValueModelAttribute attribute;

	@Override
	public void bindTo(BindingContext bindingContext) {
	    ViewBinder viewBinder = bindingContext.createViewBinder();
	    subView = viewBinder.inflateAndBind(layoutId, attribute.getPropertyName());
	    subViewAttributesStrategy.addSubView(view, subView, bindingContext.getContext());
	}

	@Override
	public void setAttribute(ValueModelAttribute attribute) {
	    this.attribute = attribute;
	}
    }

    private class SubViewWithoutPresentationModelAttribute implements ChildViewAttribute {
	@Override
	public void bindTo(BindingContext bindingContext) {
	    ViewBinder viewBinder = bindingContext.createViewBinder();
	    subView = viewBinder.inflate(layoutId);
	    subViewAttributesStrategy.addSubView(view, subView, bindingContext.getContext());
	}
    }

    private String visibilityAttribute() {
	return subViewAttributesStrategy.visibilityAttribute();
    }

    private String subViewPresentationModel() {
	return subViewAttributesStrategy.subViewPresentationModelAttribute();
    }

    private String layoutAttribute() {
	return subViewAttributesStrategy.layoutAttribute();
    }
}