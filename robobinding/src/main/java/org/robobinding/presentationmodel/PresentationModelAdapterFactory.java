package org.robobinding.presentationmodel;

import java.util.Map;

import org.robobinding.function.CachedFunctions;
import org.robobinding.function.Functions;
import org.robobinding.internal.java_beans.PropertyDescriptor;
import org.robobinding.property.CachedProperties;
import org.robobinding.property.DependencyFactory;
import org.robobinding.property.DependencyValidation;
import org.robobinding.property.ItemPresentationModelFactories;
import org.robobinding.property.ObservableBeanWrap;
import org.robobinding.property.Properties;
import org.robobinding.property.PropertiesWithDependency;
import org.robobinding.property.PropertyAccessorFactory;
import org.robobinding.property.PropertyFactory;
import org.robobinding.property.PropertyUtils;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class PresentationModelAdapterFactory {

    public PresentationModelAdapter create(Object presentationModel) {
	Map<String, PropertyDescriptor> propertyDescriptorMap = PropertyUtils.getPropertyDescriptorMap(presentationModel.getClass());
	
	PropertyAccessorFactory propertyAccessorFactory = new PropertyAccessorFactory(
		presentationModel, propertyDescriptorMap);
	
	ObservableBeanWrap observableBean = new ObservableBeanWrap(presentationModel);
	
	DependencyFactory dependencyFactory = new DependencyFactory(observableBean, 
		new DependencyValidation(propertyDescriptorMap.keySet()));
	
	PropertyFactory propertyFactory = new PropertyFactory(observableBean, 
		new ItemPresentationModelFactories(presentationModel));
	
	PropertiesWithDependency propertiesWithDependency = new PropertiesWithDependency(propertyAccessorFactory, dependencyFactory, propertyFactory);
	
	Properties properties = new CachedProperties(propertiesWithDependency);
	Functions functions = new CachedFunctions(presentationModel);
	Class<?> presentationModelClass = presentationModel.getClass();

	return new PresentationModelAdapterImpl(properties, functions, presentationModelClass);
    }

}
