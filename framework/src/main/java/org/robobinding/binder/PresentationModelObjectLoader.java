package org.robobinding.binder;

import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;

import org.robobinding.Bug;
import org.robobinding.presentationmodel.AbstractPresentationModelObject;
import org.robobinding.presentationmodel.HasPresentationModelChangeSupport;
import org.robobinding.util.ConstructorUtils;
import org.robobinding.util.Preconditions;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class PresentationModelObjectLoader {
	public static final String CLASS_SUFFIX = "$$PM";
	public static final String ITEM_CLASS_SUFFIX = "$$IPM";
	
	public static String getObjectClassName(String binaryName) {
		return binaryName.replace('$', '_') + CLASS_SUFFIX;
	}
	
	public static String getItemObjectClassName(String binaryName) {
		return binaryName.replace('$', '_') + ITEM_CLASS_SUFFIX;
	}
	
	public AbstractPresentationModelObject load(Object presentationModel) {
		if(presentationModel instanceof HasPresentationModelChangeSupport) {
			Preconditions.checkNotNull(((HasPresentationModelChangeSupport)presentationModel).getPresentationModelChangeSupport(), 
					"The PresentationModelChangeSupport from presentationModel.getPresentationModelChangeSupport() must not be null");
		}
		
		String presentationModelObjectClassName = getObjectClassName(presentationModel.getClass().getName());
		Class<?> presentationModelObjectType;
		try {
			presentationModelObjectType = Class.forName(presentationModelObjectClassName);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(MessageFormat.format(
					"The source code for ''{0}'' is not generated. Is Java annotation processing(source code generation) correctly configured?",
					presentationModelObjectClassName));
		}
			
		try {
			return (AbstractPresentationModelObject)ConstructorUtils.invokeConstructor(presentationModelObjectType, presentationModel);
		}catch (NoSuchMethodException e) {
			throw new Bug("This is a bug of constructor code generation", e);
		} catch (IllegalAccessException e) {
			throw new Bug("This is a bug of constructor code generation", e);
		} catch (InvocationTargetException e) {
			throw new Bug("This is a bug of constructor code generation", e);
		} catch (InstantiationException e) {
			throw new Bug("This is a bug of constructor code generation", e);
		}
	}
}
