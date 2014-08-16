package org.robobinding.binder;

import java.util.List;

import org.robobinding.internal.guava.Lists;
import org.robobinding.viewattribute.grouped.ViewAttributeBinderFactory;
import org.robobinding.viewattribute.impl.BindingAttributeMappingsProvider;
import org.robobinding.viewattribute.impl.InitailizedBindingAttributeMappings;

import android.view.View;


/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class ByBindingAttributeMappingsResolverFinder {
    private final BindingAttributeMappingsProviderResolver providerResolver;
    private final ViewAttributeBinderFactoryProvider viewAttributeBinderFactoryProvider;

    public ByBindingAttributeMappingsResolverFinder(
	    BindingAttributeMappingsProviderResolver providerResolver,
	    ViewAttributeBinderFactoryProvider viewAttributeBinderFactoryProvider) {
	this.providerResolver = providerResolver;
	this.viewAttributeBinderFactoryProvider = viewAttributeBinderFactoryProvider;
    }

    public Iterable<ByBindingAttributeMappingsResolver> findCandidateResolvers(View view) {
	ViewAttributeBinderFactory<View> viewAttributeBinderFactory = viewAttributeBinderFactoryProvider.create(view);

	List<ByBindingAttributeMappingsResolver> resolvers = Lists.newArrayList();
	Iterable<BindingAttributeMappingsProvider<? extends View>> providers = providerResolver.findCandidateProviders(view);
	for (BindingAttributeMappingsProvider<? extends View> provider : providers) {
	    @SuppressWarnings("unchecked")
	    BindingAttributeMappingsProvider<View> bindingAttributeProvider = (BindingAttributeMappingsProvider<View>) provider;
	    InitailizedBindingAttributeMappings<View> bindingAttributeMappings = bindingAttributeProvider.createBindingAttributeMappings();
	    resolvers.add(new ByBindingAttributeMappingsResolver(bindingAttributeMappings, viewAttributeBinderFactory));
	}

	return resolvers;
    }
}
