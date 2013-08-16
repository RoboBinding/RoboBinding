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

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

import org.robobinding.viewattribute.BindingAttributeMappingsProvider;
import org.robobinding.viewattribute.impl.BindingAttributeMappingsImpl;
import org.robobinding.viewattribute.impl.ViewAttributeInitializer;
import org.robobinding.viewattribute.impl.ViewAttributeInitializerFactory;

import android.view.View;


/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class ByBindingAttributeMappingsResolverFinder {
    private final BindingAttributeMappingsProviderResolver providerResolver;
    private final ViewAttributeInitializerFactory viewAttributeInitializerFactory;
    
    public ByBindingAttributeMappingsResolverFinder(BindingAttributeMappingsProviderResolver providerResolver,
	    ViewAttributeInitializerFactory viewAttributeInitializerFactory) {
	this.providerResolver = providerResolver;
	this.viewAttributeInitializerFactory = viewAttributeInitializerFactory;
    }
    
    public Iterable<ByBindingAttributeMappingsResolver> findCandidateResolvers(View view) {
	ViewAttributeInitializer viewAttributeInitializer = viewAttributeInitializerFactory.create(view);
	
	List<ByBindingAttributeMappingsResolver> resolvers = newArrayList(); 
	Iterable<BindingAttributeMappingsProvider<? extends View>> providers = providerResolver.findCandidateProviders(view);
	for (BindingAttributeMappingsProvider<? extends View> provider : providers) {
	    @SuppressWarnings("unchecked")
	    BindingAttributeMappingsProvider<View> bindingAttributeProvider = (BindingAttributeMappingsProvider<View>) provider;
	    BindingAttributeMappingsImpl<View> bindingAttributeMappings = bindingAttributeProvider.createBindingAttributeMappings(viewAttributeInitializer);
	    resolvers.add(new ByBindingAttributeMappingsResolver(bindingAttributeMappings));
	}
	
	return resolvers;
    }
}
