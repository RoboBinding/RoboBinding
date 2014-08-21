package org.robobinding.property;

import org.robobinding.annotation.DependsOnStateOf;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class PropertiesWithDependency {
    private final PropertyAccessorFactory propertyAccessorFactory;
    private final DependencyFactory dependencyFactory;
    private final PropertyFactory propertyFactory;
    
    public PropertiesWithDependency(PropertyAccessorFactory propertyAccessorFactory, DependencyFactory dependencyFactory, PropertyFactory propertyFactory) {
        this.propertyAccessorFactory = propertyAccessorFactory;
        this.dependencyFactory = dependencyFactory;
        this.propertyFactory = propertyFactory;
    }

    public PropertyValueModel createProperty(String propertyName) {
	PropertyAccessor propertyAccessor = propertyAccessorFactory.create(propertyName);
	AbstractProperty property = propertyFactory.createProperty(propertyAccessor);

	return convertAsDependencyPropertyIfNeccessary(property, propertyAccessor);
    }

    private PropertyValueModel convertAsDependencyPropertyIfNeccessary(
	    AbstractProperty property, PropertyAccessor propertyAccessor) {
	if (propertyAccessor.hasAnnotation(DependsOnStateOf.class)) {
	    Dependency dependency = dependencyFactory.create(propertyAccessor);
	    return new DependencyProperty(property, dependency);
	}
	
	return property;
    }

    public DataSetPropertyValueModel createDataSetProperty(String propertyName) {
	PropertyAccessor propertyAccessor = propertyAccessorFactory.create(propertyName);
	AbstractDataSetProperty dataSetProperty = propertyFactory.createDataSetProperty(propertyAccessor);
	return convertAsDataSetDependencyPropertyIfNeccessary(dataSetProperty, propertyAccessor);
    }
 
    private DataSetPropertyValueModel convertAsDataSetDependencyPropertyIfNeccessary(
	    AbstractDataSetProperty dataSetProperty, PropertyAccessor propertyAccessor) {
	if (propertyAccessor.hasAnnotation(DependsOnStateOf.class)) {
	    Dependency dependency = dependencyFactory.create(propertyAccessor);
	    DataSetDependencyProperty dataSetDependencyProperty = new DataSetDependencyProperty(dataSetProperty, dependency);
	    dependency.addListenerToDependentProperties(dataSetProperty);
	    return dataSetDependencyProperty;
	}
	return dataSetProperty;
    }

}
