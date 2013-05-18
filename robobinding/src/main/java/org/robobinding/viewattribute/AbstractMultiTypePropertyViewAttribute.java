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

import org.robobinding.BindingContext;
import org.robobinding.attribute.ValueModelAttribute;
import org.robobinding.presentationmodel.PresentationModelAdapter;

import android.view.View;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public abstract class AbstractMultiTypePropertyViewAttribute<T extends View> implements PropertyViewAttribute<T> {
    private T view;
    protected ValueModelAttribute attribute;

    private ViewListenersInjector viewListenersInjector;

    private AbstractPropertyViewAttribute<T, ?> propertyViewAttribute;

    public void initialize(MultiTypePropertyViewAttributeConfig<T> config) {
	this.view = config.getView();
	this.attribute = config.getAttribute();
	this.viewListenersInjector = config.getViewListenersInjector();
    }

    @Override
    public void bindTo(BindingContext bindingContext) {
	try {
	    initializePropertyViewAttribute(bindingContext);
	    performBind(bindingContext);
	} catch (RuntimeException e) {
	    throw new AttributeBindingException(attribute.getName(), e);
	}
    }

    private void initializePropertyViewAttribute(PresentationModelAdapter presentationModelAdapter) {
	propertyViewAttribute = lookupPropertyViewAttribute(presentationModelAdapter);
	viewListenersInjector.injectIfRequired(propertyViewAttribute, view);
    }

    private AbstractPropertyViewAttribute<T, ?> lookupPropertyViewAttribute(PresentationModelAdapter presentationModelAdapter) {
	Class<?> propertyType = presentationModelAdapter.getPropertyType(attribute.getPropertyName());
	AbstractPropertyViewAttribute<T, ?> propertyViewAttribute = createPropertyViewAttribute(propertyType);

	if (propertyViewAttribute == null)
	    throw new RuntimeException("Could not find a suitable attribute in " + getClass().getName() + " for property type: " + propertyType);

	propertyViewAttribute.initialize(new PropertyViewAttributeConfig<T>(view, attribute));
	return propertyViewAttribute;
    }

    protected abstract AbstractPropertyViewAttribute<T, ?> createPropertyViewAttribute(Class<?> propertyType);

    private void performBind(BindingContext bindingContext) {
	propertyViewAttribute.bindTo(bindingContext);
    }

    @Override
    public void preInitializeView(BindingContext bindingContext) {
	try {
	    propertyViewAttribute.preInitializeView(bindingContext);
	} catch (RuntimeException e) {
	    throw new AttributeBindingException(attribute.getName(), e);
	}
    }
}
