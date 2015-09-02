package org.robobinding.customviewbinding;

import java.lang.reflect.InvocationTargetException;
import java.text.MessageFormat;

import org.robobinding.Bug;
import org.robobinding.util.ConstructorUtils;
import org.robobinding.viewbinding.ViewBinding;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class ViewBindingLoader {
	public static final String CLASS_SUFFIX = "$$VB";
	public static String getViewBindingClassName(String binaryName) {
		return binaryName.replace('$', '_') + CLASS_SUFFIX;
	}
	@SuppressWarnings("unchecked")
	public <ViewType> ViewBinding<ViewType> load(CustomViewBinding<ViewType> customViewBinding) {
		String viewBindingClassName = getViewBindingClassName(customViewBinding.getClass().getName());
		Class<?> viewBindingType;
		try {
			viewBindingType = Class.forName(viewBindingClassName);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(MessageFormat.format(
					"The source code for ''{0}'' is not generated. Is Java annotation processing(source code generation) correctly configured?",
					viewBindingClassName));
		}
			
		try {
			return (ViewBinding<ViewType>)ConstructorUtils.invokeConstructor(viewBindingType, customViewBinding);
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
