package org.robobinding.codegen.presentationmodel.processor;

import org.robobinding.codegen.apt.element.MethodElement;
import org.robobinding.codegen.apt.type.WrappedPrimitiveType;
import org.robobinding.codegen.apt.type.WrappedTypeMirror;
import org.robobinding.codegen.presentationmodel.EventMethodInfo;




/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class EventMethodInfoImpl implements EventMethodInfo {
	private final MethodElement method;
	public EventMethodInfoImpl(MethodElement method) {
		this.method = method;
	}
	
	public String name() {
		return method.methodName();
	}
	
	public boolean hasEventArg() {
		return method.hasParameter();
	}
	
	public String eventArgType() {
		return method.firstParameterType().className();
	}
	
	public boolean hasReturn() {
		return method.hasReturn();
	}
	
	@Override
	public String nonPrimitiveReturnType() {
		WrappedTypeMirror type = method.returnType();
		if (type.isPrimitive()) {
			return ((WrappedPrimitiveType)type).boxedClassName();
		} else {
			return type.className();
		}
	}
}
