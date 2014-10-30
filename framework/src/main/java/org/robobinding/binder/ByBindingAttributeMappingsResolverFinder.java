package org.robobinding.binder;

import java.util.List;

import org.robobinding.viewbinding.InitailizedBindingAttributeMappings;
import org.robobinding.viewbinding.InitializedBindingAttributeMappingsProvider;
import org.robobinding.viewbinding.InitializedBindingAttributeMappingsProviders;

import com.google.common.collect.Lists;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class ByBindingAttributeMappingsResolverFinder {
	private final InitializedBindingAttributeMappingsProviders initializedBindingAttributeMappingsProviders;

	public ByBindingAttributeMappingsResolverFinder(
			InitializedBindingAttributeMappingsProviders initializedBindingAttributeMappingsProviders) {
		this.initializedBindingAttributeMappingsProviders = initializedBindingAttributeMappingsProviders;
	}

	public Iterable<ByBindingAttributeMappingsResolver> findCandidates(Object view) {
		List<ByBindingAttributeMappingsResolver> resolvers = Lists.newArrayList();
		Iterable<InitializedBindingAttributeMappingsProvider> providers = initializedBindingAttributeMappingsProviders.findCandidates(view.getClass());
		for (InitializedBindingAttributeMappingsProvider provider : providers) {
			InitailizedBindingAttributeMappings bindingAttributeMappings = provider.create();
			resolvers.add(new ByBindingAttributeMappingsResolver(bindingAttributeMappings));
		}

		return resolvers;
	}
}
