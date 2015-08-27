package org.robobinding.codegen.viewbinding;

import java.text.MessageFormat;
import java.util.List;

import org.robobinding.annotation.ViewBinding;
import org.robobinding.codegen.apt.element.SetterElement;
import org.robobinding.codegen.apt.element.WrappedAnnotationMirror;
import org.robobinding.codegen.apt.element.WrappedTypeElement;
import org.robobinding.codegen.apt.type.WrappedDeclaredType;
import org.robobinding.customviewbinding.CustomViewBinding;

import com.google.common.collect.Lists;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class ViewBindingInfoBuilder {
	private final WrappedTypeElement typeElement;
	private final String viewBindingObjectTypeName;
	
	public ViewBindingInfoBuilder(WrappedTypeElement typeElement, String viewBindingObjectTypeName) {
		this.typeElement = typeElement;
		this.viewBindingObjectTypeName = viewBindingObjectTypeName;
	}
	
	public ViewBindingInfo build() {
		WrappedTypeElement viewType = extractViewType();
		List<SimpleOneWayPropertyInfo> simpleOneWayPropertyInfoList = extractSimpleOneWayPropertyInfoList(viewType);
		List<TwoWayPropertyInfo> twoWayPropertyInfoList = extractTwoWayPropertyInfoList(typeElement);
		return new ViewBindingInfo(typeElement.qName(), viewBindingObjectTypeName, viewType,
				simpleOneWayPropertyInfoList, twoWayPropertyInfoList);
	}

	private WrappedTypeElement extractViewType() {
		WrappedTypeElement customViewBinding = typeElement.findDirectSuperclassOf(CustomViewBinding.class);
		if(customViewBinding == null) {
			throw new RuntimeException(MessageFormat.format("{0} has to be direct SubClass of {1}", 
					typeElement.qName(), CustomViewBinding.class.getName()));
		}
		
		return customViewBinding.firstTypeArgument();
	}

	private List<SimpleOneWayPropertyInfo> extractSimpleOneWayPropertyInfoList(WrappedTypeElement viewType) {
		ViewBindingAnnotationMirror annotation = new ViewBindingAnnotationMirror(typeElement.getAnnotation(ViewBinding.class));
		List<String> simpleOneWayProperties = annotation.getSimpleOneWayProperties();
		
		SetterElements setters = new SetterElements(
				viewType.looseSetters(new SimpleOneWayPropertySetterFilter(simpleOneWayProperties)));

		if(setters.hasAmbiguousSetters()) {
			throw OneWayBindingPropertyGenerationException.setterWithDifferentParameterTypes(viewType, 
					setters.getPropertiesWithAmbiguousSetters());
		}
		
		if(!setters.containsAll(simpleOneWayProperties)) {
			throw OneWayBindingPropertyGenerationException.noSettersFound(viewType, 
					setters.findMissingProperties(simpleOneWayProperties));
		}
		
		List<SimpleOneWayPropertyInfo> result = Lists.newArrayList();
		for(SetterElement setter : setters) {
			result.add(new SimpleOneWayPropertyInfo(setter));
		}
		
		return result;
	}

	private List<TwoWayPropertyInfo> extractTwoWayPropertyInfoList(WrappedTypeElement typeElement) {
		ViewBindingAnnotationMirror annotation = new ViewBindingAnnotationMirror(typeElement.getAnnotation(ViewBinding.class));
		List<WrappedAnnotationMirror> twoWayProperties = annotation.getTwoWayProperties();
		List<TwoWayPropertyInfo> result = Lists.newArrayList();
		for (WrappedAnnotationMirror annotationMirror : twoWayProperties) {
			String propertyName = annotationMirror.annotationValueAsText("name");
			WrappedDeclaredType propertyType = annotationMirror.annotationValueAsType("type");
			result.add(new TwoWayPropertyInfo(propertyName, propertyType.className()));
		}
		return result;
	}

}
