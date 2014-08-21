package org.robobinding.presentationmodel;

import java.text.MessageFormat;
import java.util.Map;

import org.robobinding.aspects.PresentationModel;
import org.robobinding.function.CachedFunctions;
import org.robobinding.function.Functions;
import org.robobinding.property.CachedProperties;
import org.robobinding.property.DependencyFactory;
import org.robobinding.property.DependencyValidation;
import org.robobinding.property.ItemPresentationModelFactories;
import org.robobinding.property.ObservableBean;
import org.robobinding.property.Properties;
import org.robobinding.property.PropertiesWithDependency;
import org.robobinding.property.PropertyAccessorFactory;
import org.robobinding.property.PropertyDescriptor;
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
	
	ObservableBean observableBean = asObservableBean(presentationModel);
	
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
    
    private ObservableBean asObservableBean(Object presentationModel) {
	if (presentationModel instanceof ObservableBean) {
            return (ObservableBean) presentationModel;
        } else {
            throw new RuntimeException(MessageFormat.format(
        	    "The presentationModel ''{0}'' is not observable. PresentationModels have to be annotated with @{1}, "
        		    + "implement the interface {2} or extend {3}", 
        	    presentationModel.getClass().getName(), 
        	    PresentationModel.class.getName(),
        	    ObservableBean.class.getName(),
        	    AbstractPresentationModel.class.getName()));
        }
    }
}
