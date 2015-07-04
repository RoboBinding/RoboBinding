package org.robobinding.binder;

import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;

import org.robobinding.Bug;
import org.robobinding.presentationmodel.AbstractPresentationModelObject;
import org.robobinding.presentationmodel.HasPresentationModelChangeSupport;
import org.robobinding.util.ConstructorUtils;

import com.google.common.base.Preconditions;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class PresentationModelObjectLoader {
	public static final String CLASS_SUFFIX = "$$PM";
	public AbstractPresentationModelObject load(Object presentationModel) {
		if(presentationModel instanceof HasPresentationModelChangeSupport) {
			Preconditions.checkNotNull(((HasPresentationModelChangeSupport)presentationModel).getPresentationModelChangeSupport(), 
					"The PresentationModelChangeSupport from presentationModel.getPresentationModelChangeSupport() must not be null");
		}
		
		String presentationModelObjectClassName = presentationModel.getClass().getName()+CLASS_SUFFIX;
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
