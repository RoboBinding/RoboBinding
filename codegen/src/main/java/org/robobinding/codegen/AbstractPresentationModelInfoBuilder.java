package org.robobinding.codegen;

import java.util.List;
import java.util.Set;

/**
 * @since 1.0
 * @author Cheng Wei
 * 
 */
public abstract class AbstractPresentationModelInfoBuilder {
	private TypeElementWrapper typeElement;
	private ProcessingContext context;

	private Set<String> filteredMethodNames;
	
	private Set<EventMethodInfo> eventMethods;

	public AbstractPresentationModelInfoBuilder() {
	}
	
	public PresentationModelInfo build() {
		visit(typeElement);
		return null;
	}

	private void visit(TypeElementWrapper type) {
		if (type.isObjectType()) {
			return;
		}

		List<MethodElementWrapper> methods = type.getMethods();
		classifyMethods(methods);

		visit(type.getSuperclass());
	}

	private void classifyMethods(List<MethodElementWrapper> methods) {
		for(MethodElementWrapper method : methods) {
			if(isFilteredMethod(method) || method.isStaticOrNonPublic() || method.hasMoreThanOneParameters()) {
				continue;
			}
			
			if(GetterUtils.isGetter(method)) {
				addGetter(method);
			} else if(GetterUtils.isSetter(method)) {
				addSetter(method);
			} else {//the rest are event methods.
				addEventMethod(method);
			}
		}
		
	}

	private boolean isFilteredMethod(MethodElementWrapper method) {
		return filteredMethodNames.contains(method.methodName());
	}

	protected abstract void addGetter(MethodElementWrapper method);

	protected abstract void addSetter(MethodElementWrapper method);

	private void addEventMethod(MethodElementWrapper method) {
		eventMethods.add(new EventMethodInfo(method));
	}
}
