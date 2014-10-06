package org.robobinding.codegen.processor;

import org.robobinding.codegen.EventMethodInfo;




/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class EventMethodInfoImpl implements EventMethodInfo {
	private final MethodElementWrapper method;
	public EventMethodInfoImpl(MethodElementWrapper method) {
		this.method = method;
	}
	
	public String name() {
		return method.methodName();
	}
	
	public boolean hasEventArg() {
		return method.hasParameter();
	}
	
	public String eventArgType() {
		return method.firstParameterTypeName();
	}
	
	public boolean hasReturn() {
		return method.hasReturn();
	}

	public String returnType() {
		return method.returnTypeName();
	}
}
