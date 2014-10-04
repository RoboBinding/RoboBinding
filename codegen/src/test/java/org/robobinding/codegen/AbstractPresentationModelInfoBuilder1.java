package org.robobinding.codegen;

import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.Set;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.robobinding.annotation.DependsOnStateOf;
import org.robobinding.annotation.Event;
import org.robobinding.function.MethodDescription;
import org.robobinding.property.PropertyUtils;

import com.google.common.collect.Sets;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public abstract class AbstractPresentationModelInfoBuilder1 {
	protected final Class<?> presentationModelClass;
	private String presentationModelObjectTypeName;
	protected final DependencyValidation validation;
	
	protected final Set<PropertyInfoForTest> properties;
	protected final Set<DataSetPropertyInfoForTest> dataSetProperties;
	protected final Set<PropertyDependencyInfo> propertyDependencies;
	private final Set<EventMethodInfo> eventMethods;
	
	public AbstractPresentationModelInfoBuilder1(Class<?> presentationModelClass, String presentationModelObjectTypeName) {
		this.presentationModelClass = presentationModelClass;
		
		validation = new DependencyValidation(PropertyUtils.getPropertyNames(presentationModelClass));
		properties = Sets.newHashSet();
		dataSetProperties = Sets.newHashSet();
		propertyDependencies = Sets.newHashSet();
		eventMethods = Sets.newHashSet();
	}
	
	public abstract void buildProperties();
	
	protected Set<String> dependentProperties(DependsOnStateOf dependsOnStateOfAnnotation) {
		if((dependsOnStateOfAnnotation == null)
				|| ArrayUtils.isEmpty(dependsOnStateOfAnnotation.value())) {
			return Collections.emptySet();
		}
		
		Set<String> dependentProperties = Sets.newHashSet();
		for(String dependentProperty : dependsOnStateOfAnnotation.value()) {
			if(StringUtils.isNotBlank(dependentProperty)) {
				dependentProperties.add(StringUtils.trim(dependentProperty));
			}
		}
		return dependentProperties;
	}

	public void buildEventMethods() {
		for(Method method : presentationModelClass.getMethods()) {
			if(isEventMethod(method)) {
				validateEventMethod(method);
				eventMethods.add(new EventMethodInfo(method));
			}
		}
	}

	private boolean isEventMethod(Method method) {
		return method.getAnnotation(Event.class) != null;
	}
	
	private void validateEventMethod(Method method) {
		if(method.getParameterTypes().length > 1) {
			MethodDescription description = new MethodDescription(presentationModelClass, method.getName(), method.getParameterTypes());
			throw new RuntimeException(MessageFormat.format("Event method can either have zero or one parameter, but {0} has {} parameters",
					description, method.getParameterTypes().length));
		}
	}
	
	public PresentationModelInfo getResult() {
		return new PresentationModelInfo(presentationModelClass, presentationModelObjectTypeName(),
				properties, dataSetProperties, propertyDependencies, eventMethods);
	}
	
	private String presentationModelObjectTypeName() {
		return presentationModelObjectTypeName;
	}
}
