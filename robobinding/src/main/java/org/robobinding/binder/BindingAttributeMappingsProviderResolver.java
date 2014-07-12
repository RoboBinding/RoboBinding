package org.robobinding.binder;

import java.util.Queue;

import org.robobinding.viewattribute.impl.BindingAttributeMappingsProvider;
import org.robobinding.viewattribute.impl.BindingAttributeMappingsProviderMap;

import android.view.View;

import com.google.common.collect.Lists;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 * @author Robert Taylor
 */
public class BindingAttributeMappingsProviderResolver {
    private final BindingAttributeMappingsProviderMap bindingAttributeMappingsProviderMap;

    public BindingAttributeMappingsProviderResolver(BindingAttributeMappingsProviderMap bindingAttributeMappingsProviderMap) {
	this.bindingAttributeMappingsProviderMap = bindingAttributeMappingsProviderMap;
    }

    public Iterable<BindingAttributeMappingsProvider<? extends View>> findCandidateProviders(View view) {
	Queue<BindingAttributeMappingsProvider<? extends View>> candidateProviders = Lists.newLinkedList();

	processViewHierarchy(view.getClass(), candidateProviders);

	return candidateProviders;
    }

    @SuppressWarnings("unchecked")
    private void processViewHierarchy(Class<? extends View> clazz, Queue<BindingAttributeMappingsProvider<? extends View>> candidateProviders) {
	BindingAttributeMappingsProvider<? extends View> provider = bindingAttributeMappingsProviderMap.find(clazz);

	if (provider != null)
	    candidateProviders.add(provider);

	if (clazz != View.class)
	    processViewHierarchy((Class<? extends View>) clazz.getSuperclass(), candidateProviders);
    }
}