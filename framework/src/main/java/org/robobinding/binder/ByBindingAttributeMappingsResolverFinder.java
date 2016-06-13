package org.robobinding.binder;

import java.util.List;

import org.robobinding.util.Lists;
import org.robobinding.viewattribute.grouped.ViewAttributeBinderFactories;
import org.robobinding.viewattribute.grouped.ViewAttributeBinderFactory;
import org.robobinding.viewbinding.InitailizedBindingAttributeMappings;
import org.robobinding.viewbinding.InitializedBindingAttributeMappingsProvider;
import org.robobinding.viewbinding.InitializedBindingAttributeMappingsProviders;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class ByBindingAttributeMappingsResolverFinder {
	private final InitializedBindingAttributeMappingsProviders initializedBindingAttributeMappingsProviders;
	private final ViewAttributeBinderFactories viewAttributeBinderFactories;

	public ByBindingAttributeMappingsResolverFinder(
			InitializedBindingAttributeMappingsProviders initializedBindingAttributeMappingsProviders,
			ViewAttributeBinderFactories viewAttributeBinderFactories) {
		this.initializedBindingAttributeMappingsProviders = initializedBindingAttributeMappingsProviders;
		this.viewAttributeBinderFactories = viewAttributeBinderFactories;
	}

	public Iterable<ByBindingAttributeMappingsResolver> findCandidates(Object view) {
		List<ByBindingAttributeMappingsResolver> resolvers = Lists.newArrayList();
		Iterable<InitializedBindingAttributeMappingsProvider> providers = initializedBindingAttributeMappingsProviders.findCandidates(
				view.getClass());
		ViewAttributeBinderFactory viewAttributeBinderFactory = viewAttributeBinderFactories.create(view);
		for (InitializedBindingAttributeMappingsProvider provider : providers) {
			InitailizedBindingAttributeMappings bindingAttributeMappings = provider.create(viewAttributeBinderFactory);
			resolvers.add(new ByBindingAttributeMappingsResolver(bindingAttributeMappings));
		}

		return resolvers;
	}
}
