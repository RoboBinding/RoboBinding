package org.robobinding.property;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class DependencyProperty extends PropertyValueModelWrapper {
	private final SimpleProperty property;
	private final Dependency dependency;

	public DependencyProperty(SimpleProperty property, Dependency dependency) {
		super(property);
		this.property = property;
		this.dependency = dependency;
	}

	@Override
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		super.addPropertyChangeListener(listener);
		dependency.addListenerToDependentProperties(listener);
	}

	@Override
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		super.removePropertyChangeListener(listener);
		dependency.removeListenerOffDependentProperties(listener);
	}

	@Override
	public String toString() {
		return property.decriptionWithDependencies(dependency.getDependentProperties());
	}
}
