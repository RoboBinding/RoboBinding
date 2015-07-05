package org.robobinding.codegen.viewbinding;

import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.List;

import org.robobinding.annotation.ViewBinding;
import org.robobinding.codegen.typewrapper.DeclaredTypeElementWrapper;
import org.robobinding.customviewbinding.CustomViewBinding;

import com.google.common.collect.Lists;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class ViewBindingInfoBuilder {
	private final DeclaredTypeElementWrapper typeElement;
	private final String viewBindingObjectTypeName;
	
	public ViewBindingInfoBuilder(DeclaredTypeElementWrapper typeElement, String viewBindingObjectTypeName) {
		this.typeElement = typeElement;
		this.viewBindingObjectTypeName = viewBindingObjectTypeName;
	}
	
	public ViewBindingInfo build() {
		Class<?> viewType = extractViewType();
		List<SimpleOneWayPropertyInfo> simpleOneWayPropertyInfoList = extractSimpleOneWayPropertyInfoList(viewType);
		return new ViewBindingInfo(typeElement.typeName(), viewBindingObjectTypeName, viewType, simpleOneWayPropertyInfoList);
	}

	private Class<?> extractViewType() {
		DeclaredTypeElementWrapper customViewBinding = typeElement.findDirectSuperclassOf(CustomViewBinding.class);
		if(customViewBinding == null) {
			throw new RuntimeException(MessageFormat.format("{0} has to be direct SubClass of {1}", 
					typeElement.typeName(), CustomViewBinding.class.getName()));
		}
		
		String viewTypeName = customViewBinding.getFirstTypeArgumentClassName();
		try {
			return Class.forName(viewTypeName);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	private List<SimpleOneWayPropertyInfo> extractSimpleOneWayPropertyInfoList(Class<?> viewType) {
		ViewBindingAnnotationMirror annotation = new ViewBindingAnnotationMirror(typeElement.getAnnotation(ViewBinding.class));
		List<String> simpleOneWayProperties = annotation.getSimpleOneWayProperties();
		
		Setters setters = new BeanInfo(viewType).parseSingleParameterSetters();
		if(setters.hasPropertyWithAmbiguousSetters()) {
			OneWayBindingPropertyGenerationException.setterWithDifferentParameterTypes(viewType, setters.getPropertiesWithAmbiguousSetters());
		}
		
		List<SimpleOneWayPropertyInfo> result = Lists.newArrayList();
		for(String property : simpleOneWayProperties) {
			Method setter = setters.find(property);
			if(setter == null) {
				throw OneWayBindingPropertyGenerationException.noSetterFound(viewType, property);
			}
			result.add(new SimpleOneWayPropertyInfo(setter.getParameterTypes()[0], property));
		}
		return result;
	}
}
