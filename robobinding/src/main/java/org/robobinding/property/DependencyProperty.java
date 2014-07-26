package org.robobinding.property;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
class DependencyProperty extends PropertyValueModelWrapper {
    private final AbstractProperty property;
    private final Dependency dependency;

    public DependencyProperty(AbstractProperty property, Dependency dependency) {
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
	return property.decriptionWithExtraInformation(dependency.getDependencyDescription());
    }
}
