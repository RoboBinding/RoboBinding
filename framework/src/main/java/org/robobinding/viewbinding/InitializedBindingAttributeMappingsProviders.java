package org.robobinding.viewbinding;

import java.util.Map;
import java.util.Queue;

import org.robobinding.util.SearchableClasses;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class InitializedBindingAttributeMappingsProviders {
	private final Map<Class<?>, ViewBinding<?>> mappings;
	private final SearchableClasses searchableViewClasses;

	public InitializedBindingAttributeMappingsProviders(Map<Class<?>, ViewBinding<?>> mappings) {
		this.mappings = Maps.newHashMap(mappings);
		searchableViewClasses = new SearchableClasses(mappings.keySet());
	}

	public Iterable<InitializedBindingAttributeMappingsProvider> findCandidates(Class<?> viewClass) {
		Queue<InitializedBindingAttributeMappingsProvider> candidates = Lists.newLinkedList();

		Queue<Class<?>> foundViewClasses = searchableViewClasses.findAssignablesInOrderFrom(viewClass);
		for (Class<?> foundViewClass : foundViewClasses) {
			ViewBinding<?> viewBinding = mappings.get(foundViewClass);
			
			candidates.add(new ViewBindingAdapter(viewBinding));
		}

		return candidates;
	}
}
