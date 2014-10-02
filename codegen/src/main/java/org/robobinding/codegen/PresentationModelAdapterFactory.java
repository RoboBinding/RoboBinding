package org.robobinding.codegen;

import org.robobinding.codegen.samples.PresentationModelWithChangeSupport;
import org.robobinding.codegen.samples.PresentationModelWithChangeSupport_PM;
import org.robobinding.function.FunctionSupply;
import org.robobinding.function.Functions;
import org.robobinding.function.LazyFunctions;
import org.robobinding.presentationmodel.AbstractPresentationModelObject;
import org.robobinding.property.Dependencies;
import org.robobinding.property.LazyProperties;
import org.robobinding.property.Properties;
import org.robobinding.property.PropertySupply;
import org.robobinding.property.PropertyWithDependencySupply;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class PresentationModelAdapterFactory {
	public PresentationModelAdapter create(PresentationModelWithChangeSupport presentationModel) {
		AbstractPresentationModelObject presentationModelPair = new PresentationModelWithChangeSupport_PM(presentationModel);
		
		PropertySupply propertySupply = presentationModelPair;
		Dependencies dependencies = new Dependencies(presentationModelPair, presentationModelPair.propertyDependencies());
		PropertyWithDependencySupply propertyWithDependencySupply = new PropertyWithDependencySupply(
				presentationModel.getClass(), propertySupply, dependencies);
		Properties properties = new LazyProperties(presentationModel.getClass(), presentationModelPair.propertyNames(), 
				presentationModelPair.dataSetPropertyNames(), propertyWithDependencySupply);
		
		FunctionSupply functionSupply = presentationModelPair;
		Functions functions = new LazyFunctions(presentationModel.getClass(), presentationModelPair.eventMethods(), functionSupply);
		
		return new PresentationModelAdapterImpl1(presentationModel.getClass(), properties, functions);
	}
}
