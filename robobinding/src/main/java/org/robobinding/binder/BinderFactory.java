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
package org.robobinding.binder;

import org.robobinding.ActivityBinder;
import org.robobinding.BinderImplementor;
import org.robobinding.DialogBinder;
import org.robobinding.InternalViewBinder;
import org.robobinding.NonBindingViewInflater;
import org.robobinding.ViewFactoryInstaller;
import org.robobinding.attribute.PropertyAttributeParser;
import org.robobinding.viewattribute.impl.BindingAttributeMappingsProviderMap;
import org.robobinding.viewattribute.impl.ViewAttributeInitializerFactory;
import org.robobinding.viewattribute.view.ViewListenersMap;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class BinderFactory {
    private final ViewListenersMap viewListenersMap;
    private final BindingAttributeMappingsProviderMap bindingAttributeMappingsProviderMap;
    private final PropertyAttributeParser propertyAttributeParser;
    public BinderFactory(ViewListenersMap viewListenersMap,
	   BindingAttributeMappingsProviderMap bindingAttributeMappingsProviderMap,
	   PropertyAttributeParser propertyAttributeParser) {
	this.viewListenersMap = viewListenersMap;
	this.bindingAttributeMappingsProviderMap = bindingAttributeMappingsProviderMap;
	this.propertyAttributeParser = propertyAttributeParser;
    }

    public ActivityBinder createActivityBinder(Activity activity, boolean withPreInitializingViews) {
	NonBindingViewInflater nonBindingViewInflater = new NonBindingViewInflater(createLayoutInflater(activity));
	BinderImplementor binderImplementor = createBinderImplementor(activity, nonBindingViewInflater, withPreInitializingViews);
	return new ActivityBinder(activity, binderImplementor);
    }
    
    private LayoutInflater createLayoutInflater(Context context) {
	return LayoutInflater.from(context).cloneInContext(context);
    }

    private BinderImplementor createBinderImplementor(Context context, NonBindingViewInflater nonBindingViewInflater, boolean withPreInitializingViews) {
	BindingViewInflater bindingViewInflater = createBindingViewInflater(context);
	BindingContextFactory bindingContextFactory = new BindingContextFactory(context, withPreInitializingViews, nonBindingViewInflater);
	BinderImplementor binderImplementor = new InternalBinder(bindingViewInflater, bindingContextFactory, new PlainTextErrorFormatter());
	return binderImplementor;
    }

    private BindingViewInflater createBindingViewInflater(Context context) {
	LayoutInflater layoutInflater = createLayoutInflater(context);
	NonBindingViewInflater nonBindingViewInflater = new NonBindingViewInflater(layoutInflater);
	ByBindingAttributeMappingsResolverFinder byBindingAttributeProviderResolverFinder = new ByBindingAttributeMappingsResolverFinder(
		new BindingAttributeMappingsProviderResolver(bindingAttributeMappingsProviderMap, propertyAttributeParser),
		new ViewAttributeInitializerFactory(viewListenersMap));
	BindingAttributeResolver bindingAttributeResolver = new BindingAttributeResolver(byBindingAttributeProviderResolverFinder);
	BindingViewInflater bindingViewInflater = new BindingViewInflater(nonBindingViewInflater, bindingAttributeResolver,
		new BindingAttributeParser());
	
	new ViewFactoryInstaller(bindingViewInflater).install(layoutInflater);
	return bindingViewInflater;
    }

    public DialogBinder createDialogBinder(Dialog dialog) {
	Context context = dialog.getContext();
	NonBindingViewInflater nonBindingViewInflater = new NonBindingViewInflater(createLayoutInflater(context));
	BinderImplementor binderImplementor = createBinderImplementor(context, nonBindingViewInflater, true);
	return new DialogBinder(dialog, binderImplementor);
    }

    public InternalViewBinder createInternalViewBinder(Context context) {
	NonBindingViewInflater nonBindingViewInflater = new NonBindingViewInflater(createLayoutInflater(context));
	BinderImplementor binderImplementor = createBinderImplementor(context, nonBindingViewInflater, true);
	return new InternalViewBinder(binderImplementor, nonBindingViewInflater);
    }

}
