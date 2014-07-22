package org.robobinding.presentationmodel;

import org.robobinding.function.CachedFunctions;
import org.robobinding.function.Functions;
import org.robobinding.property.CachedProperties;
import org.robobinding.property.Properties;
import org.robobinding.property.PropertyCreator;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class PresentationModelAdapterFactory {

    public PresentationModelAdapter create(Object presentationModel) {
	Properties properties = new CachedProperties(new PropertyCreator(presentationModel));
	Functions functions = new CachedFunctions(presentationModel);
	Class<?> presentationModelClass = presentationModel.getClass();

	return new PresentationModelAdapterImpl(properties, functions, presentationModelClass);
    }

}
