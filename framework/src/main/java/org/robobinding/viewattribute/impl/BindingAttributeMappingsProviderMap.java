package org.robobinding.viewattribute.impl;

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
public class BindingAttributeMappingsProviderMap {
	private final Map<Class<?>, BindingAttributeMappingsProvider<?>> mappings;
	private final SearchableClasses searchableViewClasses;

	public BindingAttributeMappingsProviderMap(Map<Class<?>, BindingAttributeMappingsProvider<?>> mappings) {
		this.mappings = Maps.newHashMap(mappings);
		searchableViewClasses = new SearchableClasses(mappings.keySet());
	}

	public Iterable<BindingAttributeMappingsProvider<?>> findCandidates(Class<?> viewClass) {
		Queue<BindingAttributeMappingsProvider<?>> candidates = Lists.newLinkedList();

		Queue<Class<?>> foundViewClasses = searchableViewClasses.findAssignablesInOrderFrom(viewClass);
		for (Class<?> foundViewClass : foundViewClasses) {
			candidates.add(mappings.get(foundViewClass));
		}

		return candidates;
	}
}
