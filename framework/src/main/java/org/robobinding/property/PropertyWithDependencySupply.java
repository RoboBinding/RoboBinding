package org.robobinding.property;

import java.text.MessageFormat;

import org.robobinding.Bug;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class PropertyWithDependencySupply {
	private final Class<?> beanClass;
	private final PropertySupply supply;
	private final Dependencies dependencies;
	
	public PropertyWithDependencySupply(Class<?> beanClass, PropertySupply supply, Dependencies dependencies) {
		this.beanClass = beanClass;
		this.supply = supply;
		this.dependencies = dependencies;
	}

	public PropertyValueModel createProperty(String propertyName) {
		SimpleProperty property = supply.tryToCreateProperty(propertyName);
		if(property == null) {
			throw new Bug(MessageFormat.format("no known property '{0}' generated", describeProperty(propertyName)));
		}
		
		if(dependencies.hasDependency(propertyName)) {
			Dependency dependency = dependencies.createDependency(propertyName);
			return new DependencyProperty(property, dependency);
		} else {
			return property;
		}
	}

	private String describeProperty(String propertyName) {
		return PropertyUtils.shortDescription(beanClass, propertyName);
	}

	public DataSetPropertyValueModel createDataSetProperty(String propertyName) {
		DataSetProperty property = supply.tryToCreateDataSetProperty(propertyName);
		if(property == null) {
			throw new Bug(MessageFormat.format("no known dataSet property '{0}' generated", describeProperty(propertyName)));
		}

		property.addListener(property);
		
		if(dependencies.hasDependency(propertyName)) {
			Dependency dependency = dependencies.createDependency(propertyName);
			DataSetDependencyProperty dataSetDependencyProperty = new DataSetDependencyProperty(property, dependency);
			dependency.addListenerToDependentProperties(property);
			return dataSetDependencyProperty;
		} else {
			return property;
		}
	}
}
