package org.robobinding.binder;

import java.util.List;

import org.robobinding.viewattribute.grouped.ViewAttributeBinderFactory;
import org.robobinding.viewattribute.impl.BindingAttributeMappingsProvider;
import org.robobinding.viewattribute.impl.BindingAttributeMappingsProviderMap;
import org.robobinding.viewattribute.impl.InitailizedBindingAttributeMappings;

import com.google.common.collect.Lists;


/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class ByBindingAttributeMappingsResolverFinder {
    private final BindingAttributeMappingsProviderMap bindingAttributeMappingsProviderMap;
    private final ViewAttributeBinderFactoryProvider viewAttributeBinderFactoryProvider;

    public ByBindingAttributeMappingsResolverFinder(
	    BindingAttributeMappingsProviderMap bindingAttributeMappingsProviderMap,
	    ViewAttributeBinderFactoryProvider viewAttributeBinderFactoryProvider) {
	this.bindingAttributeMappingsProviderMap = bindingAttributeMappingsProviderMap;
	this.viewAttributeBinderFactoryProvider = viewAttributeBinderFactoryProvider;
    }

    public Iterable<ByBindingAttributeMappingsResolver> findCandidates(Object view) {
	ViewAttributeBinderFactory<Object> viewAttributeBinderFactory = viewAttributeBinderFactoryProvider.create(view);

	List<ByBindingAttributeMappingsResolver> resolvers = Lists.newArrayList();
	Iterable<BindingAttributeMappingsProvider<?>> providers = bindingAttributeMappingsProviderMap.findCandidates(view.getClass());
	for (BindingAttributeMappingsProvider<?> provider : providers) {
	    @SuppressWarnings("unchecked")
	    BindingAttributeMappingsProvider<Object> bindingAttributeProvider = (BindingAttributeMappingsProvider<Object>) provider;
	    InitailizedBindingAttributeMappings<Object> bindingAttributeMappings = bindingAttributeProvider.createBindingAttributeMappings();
	    resolvers.add(new ByBindingAttributeMappingsResolver(bindingAttributeMappings, viewAttributeBinderFactory));
	}

	return resolvers;
    }
}
