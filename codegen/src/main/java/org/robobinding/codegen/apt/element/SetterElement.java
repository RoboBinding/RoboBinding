package org.robobinding.codegen.apt.element;

import org.robobinding.codegen.apt.type.WrappedTypeMirror;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class SetterElement extends WrappedElementAdapter {
	private final MethodElement element;
	private final String propertyName;

	public SetterElement(MethodElement element, String propertyName) {
		super(element);
		
		this.element = element;
		this.propertyName = propertyName;
	}

	public String propertyName() {
		return propertyName;
	}

	public WrappedTypeMirror parameterType() {
		return element.firstParameterType();
	}

	public String methodName() {
		return element.methodName();
	}
	
}
