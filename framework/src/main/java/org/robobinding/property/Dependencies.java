package org.robobinding.property;

import java.util.Map;
import java.util.Set;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class Dependencies {
	private final ObservableBean bean;
	private final Map<String, Set<String>> dependencyInfo;
	
	public Dependencies(ObservableBean bean, Map<String, Set<String>> dependencyInfo) {
		this.bean = bean;
		this.dependencyInfo = dependencyInfo;
	}

	public boolean hasDependency(String propertyName) {
		return dependencyInfo.containsKey(propertyName);
	}

	public Dependency createDependency(String propertyName) {
		return new Dependency(bean, dependencyInfo.get(propertyName));
	}

}
