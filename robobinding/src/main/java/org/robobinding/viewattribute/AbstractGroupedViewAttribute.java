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
import org.robobinding.attribute.GroupAttributes;
import org.robobinding.attribute.PendingGroupAttributes;

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

    public void initialize(GroupedViewAttributeConfig<T> config) {
	view = config.getView();
	childViewAttributes = createChildViewAttributes(config.getPendingGroupAttributes(), config.getViewListenersInjector());
    }

    private ChildViewAttributes<T> createChildViewAttributes(PendingGroupAttributes pendingGroupAttributes,
	    ViewListenersInjector viewListenersInjector) {
	GroupAttributes groupAttributes = createGroupAttributes(pendingGroupAttributes);

	ViewAttributeInitializer viewAttributeInitializer = new ViewAttributeInitializer(viewListenersInjector);

	return new ChildViewAttributes<T>(groupAttributes, viewAttributeInitializer);
    }

    private GroupAttributes createGroupAttributes(PendingGroupAttributes pendingGroupAttributes) {
	pendingGroupAttributes.assertAttributesArePresent(getCompulsoryAttributes());
	ChildAttributeResolverMappings resolverMappings = createResolverMappings();
	GroupAttributes groupAttributes = new GroupAttributes(pendingGroupAttributes, resolverMappings);

	validateResolvedChildAttributes(groupAttributes);

	return groupAttributes;
    }

    private ChildAttributeResolverMappings createResolverMappings() {
	ChildAttributeResolverMappings resolverMappings = new ChildAttributeResolverMappings();
	mapChildAttributeResolvers(resolverMappings);
	return resolverMappings;
    }

    protected abstract void mapChildAttributeResolvers(ChildAttributeResolverMappings resolverMappings);

    public void validateResolvedChildAttributes(GroupAttributes groupAttributes) {
    }

    protected abstract void setupChildViewAttributes(ChildViewAttributes<T> childViewAttributes, BindingContext bindingContext);

    @Override
    public final void bindTo(BindingContext bindingContext) {
	setupChildViewAttributes(childViewAttributes, bindingContext);
	childViewAttributes.markSetupCompleted();

	childViewAttributes.bindTo(bindingContext);

	postBind(bindingContext);
    }

    protected void postBind(BindingContext bindingContext) {

    }

    protected String[] getCompulsoryAttributes() {
	return NO_COMPULSORY_ATTRIBUTES;
    }

    @Override
    public final void preInitializeView(BindingContext bindingContext) {
	childViewAttributes.preInitializeView(bindingContext);
    }

    private class ViewAttributeInitializer extends AbstractViewAttributeInitializer {
	public ViewAttributeInitializer(ViewListenersInjector viewListenersInjector) {
	    super(viewListenersInjector);
	}

	@Override
	protected T getView() {
	    return view;
	}
    }
}
