package org.robobinding.property;

import java.util.Set;

import org.robobinding.internal.guava.Joiner;

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

    public String getDependencyDescription() {
	return "dependentProperties:[" + Joiner.on(",").join(dependentProperties) + "]";
    }

}