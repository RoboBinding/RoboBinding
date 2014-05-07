package org.robobinding.property;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
class DependencyProperty<T> extends PropertyValueModelWrapper<T> {
    private Dependency dependency;

    public DependencyProperty(PropertyValueModel<T> propertyValueModel, Dependency dependency) {
	super(propertyValueModel);
	this.dependency = dependency;
    }

    @Override
    public void addPropertyChangeListener(PresentationModelPropertyChangeListener listener) {
	super.addPropertyChangeListener(listener);
	dependency.addListenerToDependentProperties(listener);
    }

    @Override
    public void removePropertyChangeListener(PresentationModelPropertyChangeListener listener) {
	super.removePropertyChangeListener(listener);
	dependency.removeListenerOffDependentProperties(listener);
    }

    @Override
    public String toString() {
	return dependency.decribeDependencyProperty();
    }
}
