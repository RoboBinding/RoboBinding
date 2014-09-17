package org.robobinding.property;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
class DataSetDependencyProperty extends DataSetPropertyValueModelWrapper {
	private final Dependency dependency;
	private final AbstractDataSetProperty dataSetProperty;

	public DataSetDependencyProperty(AbstractDataSetProperty dataSetProperty, Dependency dependency) {
		super(dataSetProperty);
		this.dataSetProperty = dataSetProperty;
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
		return dataSetProperty.decriptionWithExtraInformation(dependency.getDependencyDescription());
	}
}