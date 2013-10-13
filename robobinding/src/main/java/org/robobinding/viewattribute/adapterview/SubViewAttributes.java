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
import org.robobinding.attribute.ChildAttributeResolverMappings;
import org.robobinding.viewattribute.AbstractGroupedViewAttribute;
import org.robobinding.viewattribute.ChildViewAttribute;
import org.robobinding.viewattribute.ChildViewAttributeFactory;
import org.robobinding.viewattribute.ChildViewAttributesBuilder;
import org.robobinding.viewattribute.ViewAttributeFactory;

import android.view.View;
import android.widget.AdapterView;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class SubViewAttributes<T extends AdapterView<?>> extends AbstractGroupedViewAttribute<T>
	implements SubViewHolder {
    private View subView;
    private SubViewAttributesStrategy<T> subViewAttributesStrategy;

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
    protected void setupChildViewAttributes(ChildViewAttributesBuilder<T> childViewAttributesBuilder, BindingContext bindingContext) {
	childViewAttributesBuilder.failOnFirstBindingError();

	SubViewLayoutAttribute subViewLayoutAttribute = new SubViewLayoutAttribute();
	childViewAttributesBuilder.add(layoutAttribute(), subViewLayoutAttribute);

	boolean hasSubViewPresentationModel = childViewAttributesBuilder.hasAttribute(subViewPresentationModel());
	childViewAttributesBuilder.add(subViewPresentationModel(),
		new SubViewAttributeFactory(subViewLayoutAttribute, hasSubViewPresentationModel));

	if (childViewAttributesBuilder.hasAttribute(visibilityAttribute())) {
	    childViewAttributesBuilder.add(visibilityAttribute(), new SubViewVisibilityAttributeFactory());
	}
    }

    @Override
    protected void postBind(BindingContext bindingContext) {
	subViewAttributesStrategy.addSubView(view, subView, bindingContext.getContext());
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

    @Override
    public void setSubView(View subView) {
        this.subView = subView;
    }

    private class SubViewAttributeFactory implements ChildViewAttributeFactory {
	private final SubViewLayoutAttribute subViewLayoutAttribute;
	private final boolean hasSubViewPresentationModel;

	public SubViewAttributeFactory(SubViewLayoutAttribute subViewLayoutAttribute, boolean hasSubViewPresentationModel) {
	    this.subViewLayoutAttribute = subViewLayoutAttribute;
	    this.hasSubViewPresentationModel = hasSubViewPresentationModel;
	}

	@Override
	public ChildViewAttribute create() {
	    int layoutId = subViewLayoutAttribute.getLayoutId();
	    return hasSubViewPresentationModel ? new SubViewPresentationModelAttribute(layoutId, SubViewAttributes.this)
	    	: new SubViewWithoutPresentationModelAttribute(layoutId, SubViewAttributes.this);
	}
    }

    private class SubViewVisibilityAttributeFactory implements ViewAttributeFactory<SubViewVisibilityAttribute> {
	public SubViewVisibilityAttribute create() {
	    return new SubViewVisibilityAttribute(subViewAttributesStrategy.createVisibility(view, subView));
	}
    }


}