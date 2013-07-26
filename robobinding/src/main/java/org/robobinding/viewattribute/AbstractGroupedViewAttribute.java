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
import org.robobinding.attribute.ChildAttributeResolverMappings;
import org.robobinding.attribute.PendingGroupAttributes;
import org.robobinding.attribute.ResolvedGroupAttributes;

import android.view.View;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
public abstract class AbstractGroupedViewAttribute<T extends View> implements ViewAttribute {
    private static final String[] NO_COMPULSORY_ATTRIBUTES = new String[0];

    protected T view;
    ChildViewAttributes<T> childViewAttributes;
    private InitializedChildViewAttributes initializedChildViewAttributes;

    public void initialize(GroupedViewAttributeConfig<T> config) {
	view = config.getView();
	childViewAttributes = createChildViewAttributes(config.getPendingGroupAttributes(), config.getViewListenersInjector());
    }

    private ChildViewAttributes<T> createChildViewAttributes(PendingGroupAttributes pendingGroupAttributes,
	    ViewListenersInjector viewListenersInjector) {
	ResolvedGroupAttributes resolvedGroupAttributes = createResolvedGroupAttributes(pendingGroupAttributes);

	ChildViewAttributeInitializer viewAttributeInitializer = new ChildViewAttributeInitializer(
		new StandaloneViewAttributeInitializer(viewListenersInjector, view));

	return new ChildViewAttributes<T>(resolvedGroupAttributes, viewAttributeInitializer);
    }

    private ResolvedGroupAttributes createResolvedGroupAttributes(PendingGroupAttributes pendingGroupAttributes) {
	pendingGroupAttributes.assertAttributesArePresent(getCompulsoryAttributes());
	ChildAttributeResolverMappings resolverMappings = createResolverMappings();
	ResolvedGroupAttributes resolvedGroupAttributes = new ResolvedGroupAttributes(pendingGroupAttributes, resolverMappings);

	validateResolvedChildAttributes(resolvedGroupAttributes);

	return resolvedGroupAttributes;
    }

    protected String[] getCompulsoryAttributes() {
        return NO_COMPULSORY_ATTRIBUTES;
    }

    private ChildAttributeResolverMappings createResolverMappings() {
	ChildAttributeResolverMappings resolverMappings = new ChildAttributeResolverMappings();
	mapChildAttributeResolvers(resolverMappings);
	return resolverMappings;
    }

    protected abstract void mapChildAttributeResolvers(ChildAttributeResolverMappings resolverMappings);

    public void validateResolvedChildAttributes(ResolvedGroupAttributes groupAttributes) {
    }

    @Override
    public final void bindTo(BindingContext bindingContext) {
	initializedChildViewAttributes = initializeChildViewAttributes(bindingContext);
	initializedChildViewAttributes.bindTo(bindingContext);
	postBind(bindingContext);
    }
    
    private InitializedChildViewAttributes initializeChildViewAttributes(BindingContext bindingContext)
    {
	setupChildViewAttributes(childViewAttributes, bindingContext);
	return childViewAttributes.createInitializedChildViewAttributes();
    }

    protected abstract void setupChildViewAttributes(ChildViewAttributes<T> childViewAttributes, BindingContext bindingContext);

    protected void postBind(BindingContext bindingContext) {

    }

    @Override
    public final void preInitializeView(BindingContext bindingContext) {
	initializedChildViewAttributes.preInitializeView(bindingContext);
    }
}
