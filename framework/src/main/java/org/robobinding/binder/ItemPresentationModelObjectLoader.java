package org.robobinding.binder;

import com.google.common.base.Preconditions;
import org.robobinding.Bug;
import org.robobinding.presentationmodel.AbstractItemPresentationModelObject;
import org.robobinding.presentationmodel.AbstractPresentationModelObject;
import org.robobinding.presentationmodel.HasPresentationModelChangeSupport;
import org.robobinding.util.ConstructorUtils;

import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class ItemPresentationModelObjectLoader {
	public static final String CLASS_SUFFIX = "$$IPM";
	public static AbstractItemPresentationModelObject load(Object itemPresentationModel) {
		if(itemPresentationModel instanceof HasPresentationModelChangeSupport) {
			Preconditions.checkNotNull(((HasPresentationModelChangeSupport)itemPresentationModel).getPresentationModelChangeSupport(),
					"The PresentationModelChangeSupport from itemPresentationModel.getPresentationModelChangeSupport() must not be null");
		}
		
		String itemPresentationModelObjectClassName = itemPresentationModel.getClass().getName()+CLASS_SUFFIX;
		Class<?> itemPresentationModelObjectType;
		try {
			itemPresentationModelObjectType = Class.forName(itemPresentationModelObjectClassName);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(MessageFormat.format(
					"The source code for ''{0}'' is not generated. Is Java annotation processing(source code generation) correctly configured?",
					itemPresentationModelObjectClassName));
		}
			
		try {
			return (AbstractItemPresentationModelObject)ConstructorUtils.invokeConstructor(itemPresentationModelObjectType, itemPresentationModel);
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
