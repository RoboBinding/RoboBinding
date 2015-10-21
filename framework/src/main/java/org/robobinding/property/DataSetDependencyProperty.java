package org.robobinding.property;


/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class DataSetDependencyProperty extends DataSetPropertyValueModelWrapper {
	private final Dependency dependency;
	private final DataSetProperty dataSetProperty;

	public DataSetDependencyProperty(DataSetProperty dataSetProperty, Dependency dependency) {
		super(dataSetProperty);
		this.dataSetProperty = dataSetProperty;
		this.dependency = dependency;
	}
	
	@Override
	public void addPropertyChangeListener(DataSetPropertyChangeListener listener) {
		super.addPropertyChangeListener(listener);
		dependency.addListenerToDependentProperties(listener);
	}

	@Override
	public void removePropertyChangeListener(DataSetPropertyChangeListener listener) {
		super.removePropertyChangeListener(listener);
		dependency.removeListenerOffDependentProperties(listener);
	}

	@Override
	public String toString() {
		return dataSetProperty.decriptionWithDependencies(dependency.getDependentProperties());
	}
}