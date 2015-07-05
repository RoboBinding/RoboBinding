package org.robobinding.codegen.viewbinding;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class Setters {
	private final Map<String, Method> setterMap;
	private final Set<String> propertiesWithAmbiguousSetters;
	
	public Setters(Map<String, Method> setterMap, Set<String> propertiesWithAmbiguousSetters) {
		this.setterMap = setterMap;
		this.propertiesWithAmbiguousSetters = propertiesWithAmbiguousSetters;
	}
	
	public Method find(String property) {
		return setterMap.get(property);
	}
	
	public boolean hasPropertyWithAmbiguousSetters() {
		return !propertiesWithAmbiguousSetters.isEmpty();
	}

	public Set<String> getPropertiesWithAmbiguousSetters() {
		return Collections.unmodifiableSet(propertiesWithAmbiguousSetters);
	}
}
