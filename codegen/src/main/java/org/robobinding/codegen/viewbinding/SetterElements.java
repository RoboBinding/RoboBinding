package org.robobinding.codegen.viewbinding;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.robobinding.codegen.apt.element.SetterElement;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class SetterElements implements Iterable<SetterElement> {
	private final Map<String, SetterElement> elementMap;
	private final Collection<String> ambiguousProperties;
	public SetterElements(List<SetterElement> elements) {
		elementMap = Maps.newHashMap();
		ambiguousProperties = Lists.newArrayList();
		for(SetterElement element : elements) {
			String property = element.propertyName();
			if(elementMap.containsKey(property)) {
				ambiguousProperties.add(property);
			}
			
			elementMap.put(property, element);
		}
	}

	@Override
	public Iterator<SetterElement> iterator() {
		return elementMap.values().iterator();
	}

	public boolean hasAmbiguousSetters() {
		return !ambiguousProperties.isEmpty();
	}

	public Collection<String> getPropertiesWithAmbiguousSetters() {
		return Collections.unmodifiableCollection(ambiguousProperties);
	}

	public boolean containsAll(Collection<String> properties) {
		return elementMap.keySet().containsAll(properties);
	}

	public Collection<String> findMissingProperties(Collection<String> properties) {
		Set<String> missingProperties = Sets.newHashSet(properties);
		missingProperties.removeAll(elementMap.keySet());
		return missingProperties;
	}

	
}
