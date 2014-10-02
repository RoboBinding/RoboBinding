package org.robobinding.property;

import java.util.Collections;
import java.util.Set;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class Dependency {
	private final ObservableBean observableBean;
	private final Set<String> dependentProperties;

	public Dependency(ObservableBean observableBean, Set<String> dependentProperties) {
		this.observableBean = observableBean;
		this.dependentProperties = dependentProperties;
	}

	public void addListenerToDependentProperties(PropertyChangeListener listener) {
		for (String dependentProperty : dependentProperties) {
			observableBean.addPropertyChangeListener(dependentProperty, listener);
		}
	}

	public void removeListenerOffDependentProperties(PropertyChangeListener listener) {
		for (String dependentProperty : dependentProperties) {
			observableBean.removePropertyChangeListener(dependentProperty, listener);
		}
	}
	
	public Set<String> getDependentProperties() {
		return Collections.unmodifiableSet(dependentProperties);
	}

}