package org.robobinding.presentationmodel;

import org.robobinding.function.Functions;
import org.robobinding.function.LazyFunctions;
import org.robobinding.property.Dependencies;
import org.robobinding.property.LazyProperties;
import org.robobinding.property.Properties;
import org.robobinding.property.PropertyWithDependencySupply;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class PresentationModelAdapterFactory {
	public PresentationModelAdapter create(AbstractPresentationModelObject presentationModelObject) {
		Class<?> presentationModelClass = presentationModelObject.getPresentationModelClass();
		
		Dependencies dependencies = new Dependencies(presentationModelObject, presentationModelObject.propertyDependencies());
		PropertyWithDependencySupply propertyWithDependencySupply = new PropertyWithDependencySupply(
				presentationModelClass, presentationModelObject, dependencies);
		Properties properties = new LazyProperties(presentationModelClass, presentationModelObject.propertyNames(), 
				presentationModelObject.dataSetPropertyNames(), propertyWithDependencySupply);
		
		Functions functions = new LazyFunctions(presentationModelClass, 
				presentationModelObject.eventMethods(), presentationModelObject);
		
		return new PresentationModelAdapterImpl(presentationModelClass, properties, functions);
	}
}
