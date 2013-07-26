/**
 * Copyright 2013 Cheng Wei, Robert Taylor
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

import org.robobinding.BindingContext;
import org.robobinding.attribute.AbstractAttribute;
import org.robobinding.attribute.ValueModelAttribute;

import android.view.View;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class ChildViewAttributeInitializer {
    private final StandaloneViewAttributeInitializer standaloneViewAttributeInitializer;
    
    public ChildViewAttributeInitializer(StandaloneViewAttributeInitializer standaloneViewAttributeInitializer) {
	this.standaloneViewAttributeInitializer = standaloneViewAttributeInitializer;
    }
    
    public <ViewType extends View, PropertyViewAttributeType extends PropertyViewAttribute<ViewType>> 
	PropertyViewAttributeType initializePropertyViewAttribute(
	    PropertyViewAttributeType propertyViewAttribute, ValueModelAttribute attribute) {
	return standaloneViewAttributeInitializer.initializePropertyViewAttribute(propertyViewAttribute, attribute);
    }
    
    @SuppressWarnings("unchecked")
    public ViewAttribute initializeChildViewAttribute(ChildViewAttribute childAttribute,
	    AbstractAttribute attribute) {
	if (childAttribute instanceof ChildViewAttributeWithAttribute<?>) {
	    ((ChildViewAttributeWithAttribute<AbstractAttribute>) childAttribute).setAttribute(attribute);
	}
	return new ViewAttributeAdapter(childAttribute, attribute);
    }
    
    private static class ViewAttributeAdapter implements ViewAttribute {
	private final ChildViewAttribute childViewAttribute;
	private final AbstractAttribute attribute;

	public ViewAttributeAdapter(ChildViewAttribute childViewAttribute, AbstractAttribute attribute) {
	    this.childViewAttribute = childViewAttribute;
	    this.attribute = attribute;
	}

	@Override
	public void preInitializeView(BindingContext bindingContext) {
	}

	@Override
	public void bindTo(BindingContext bindingContext) {
	    try {
	    childViewAttribute.bindTo(bindingContext);
	    } catch (RuntimeException e) {
		throw new AttributeBindingException(attribute.getName(), e);
	    }
	}
    }

}
