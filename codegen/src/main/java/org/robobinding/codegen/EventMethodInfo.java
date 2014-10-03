package org.robobinding.codegen;

import java.lang.reflect.Method;


/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class EventMethodInfo {
	private final Method method;
	public EventMethodInfo(Method method) {
		this.method = method;
	}
	
	public String name() {
		return method.getName();
	}
	
	public boolean hasEventArg() {
		return method.getParameterTypes().length != 0;
	}
	
	public Class<?> eventArgType() {
		return method.getParameterTypes()[0];
	}
	
	public boolean hasReturn() {
		return !Void.TYPE.equals(method.getReturnType());
	}

	public Class<?> returnType() {
		return method.getReturnType();
	}
}
