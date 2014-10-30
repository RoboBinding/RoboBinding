package org.robobinding.codegen.presentationmodel.typemirror;

import java.lang.reflect.Method;

import org.apache.commons.lang3.ClassUtils;
import org.robobinding.codegen.presentationmodel.EventMethodInfo;

/**
 * @since 1.0
 * @author Cheng Wei
 * 
 */
public class EventMethodInfoImpl implements EventMethodInfo {
	private final Method method;

	public EventMethodInfoImpl(Method method) {
		this.method = method;
	}

	@Override
	public String name() {
		return method.getName();
	}

	@Override
	public boolean hasEventArg() {
		return method.getParameterTypes().length != 0;
	}

	@Override
	public String eventArgType() {
		return method.getParameterTypes()[0].getName();
	}

	@Override
	public boolean hasReturn() {
		return !Void.TYPE.equals(method.getReturnType());
	}
	
	@Override
	public String nonPrimitiveReturnType() {
		return ClassUtils.primitiveToWrapper(method.getReturnType()).getName();
	}
}
