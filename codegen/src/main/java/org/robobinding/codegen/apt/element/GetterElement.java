package org.robobinding.codegen.apt.element;

import org.robobinding.codegen.apt.type.WrappedTypeMirror;


/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class GetterElement extends WrappedElementAdapter {
	private final MethodElement element;
	private final String propertyName;

	public GetterElement(MethodElement element, String propertyName) {
		super(element);
		
		this.element = element;
		this.propertyName = propertyName;
	}

	public String propertyName() {
		return propertyName;
	}
	
	public <T extends WrappedTypeMirror> T returnType() {
		return element.returnType();
	}
	
	public String methodName() {
		return element.methodName();
	}
	
	@Override
	public String toString() {
		return element.toString();
	}
}
