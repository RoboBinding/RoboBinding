package org.robobinding.codegen.viewbinding;

import java.lang.reflect.Method;
import java.util.List;

import org.robobinding.annotation.ViewBinding;
import org.robobinding.codegen.typewrapper.AbstractTypeElementWrapper;
import org.robobinding.codegen.typewrapper.AnnotationMirrorWrapper;

import com.google.common.collect.Lists;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class ViewBindingInfoBuilder {
	private final AbstractTypeElementWrapper typeElement;
	private final String viewBindingObjectTypeName;
	
	public ViewBindingInfoBuilder(AbstractTypeElementWrapper typeElement, String viewBindingObjectTypeName) {
		this.typeElement = typeElement;
		this.viewBindingObjectTypeName = viewBindingObjectTypeName;
	}
	
	public ViewBindingInfo build() {
		Class<?> viewType = extractViewType();
		List<SimpleOneWayPropertyInfo> simpleOneWayPropertyInfoList = extractSimpleOneWayPropertyInfoList(viewType);
		return new ViewBindingInfo(typeElement.typeName(), viewBindingObjectTypeName, viewType, simpleOneWayPropertyInfoList);
	}

	private Class<?> extractViewType() {
		AbstractTypeElementWrapper superclass = typeElement.getSuperclass();
		return null;
	}

	private List<SimpleOneWayPropertyInfo> extractSimpleOneWayPropertyInfoList(Class<?> viewType) {
		AnnotationMirrorWrapper annotationMirror = typeElement.getAnnotation(ViewBinding.class);
		List<String> simpleOneWayProperties = annotationMirror.annotationValueAsList("simpleOneWayProperties");
		
		List<SimpleOneWayPropertyInfo> result = Lists.newArrayList();
		for(String property : simpleOneWayProperties) {
			List<Method> setters = PropertyUtils.findSetters(viewType, property);
			if(setters.size() != 1) {
				//report conflicts or zero setter.
			}
			Method setter = setters.get(0);
			result.add(new SimpleOneWayPropertyInfo(setter.getParameterTypes()[0], property));
		}
		return result;
	}
}
