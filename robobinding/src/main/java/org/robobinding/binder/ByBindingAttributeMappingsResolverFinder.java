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
