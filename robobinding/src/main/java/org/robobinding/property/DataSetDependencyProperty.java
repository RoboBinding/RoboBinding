package org.robobinding.property;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
class DataSetDependencyProperty<T> extends DataSetPropertyValueModelWrapper<T> {
    private Dependency dependency;

    public DataSetDependencyProperty(AbstractDataSetProperty<T> dataSetProperty, Dependency dependency) {
	super(dataSetProperty);
	this.dependency = dependency;
	dependency.addListenerToDependentProperties(dataSetProperty);
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