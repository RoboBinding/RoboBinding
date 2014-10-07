package org.robobinding.function;

import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.Map;
import java.util.Set;

import org.robobinding.util.MethodUtils;

import com.google.android.collect.Maps;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class LazyFunctions implements Functions {
	private final Class<?> beanClass;
	private final Map<MethodDescriptor, Function> functions;
	private final FunctionSupply supply;

	public LazyFunctions(Class<?> beanClass, Set<MethodDescriptor> methods, FunctionSupply supply) {
		this.beanClass = beanClass;
		this.functions = Maps.newHashMap();
		this.supply = supply;
		
		initializeFunctions(methods);
	}

	private void initializeFunctions(Set<MethodDescriptor> methods) {
		for(MethodDescriptor method : methods) {
			functions.put(method, null);
		}
	}

	@Override
	public Function find(String functionName, Class<?>... parameterTypes) {
		Method method = MethodUtils.getMatchingAccessibleMethod(beanClass, functionName, parameterTypes);
		
		if(method == null) {
			return null;
		}
		
		MethodDescriptor methodDescriptor = new MethodDescriptor(method.getName(), method.getParameterTypes());
		
		Function function = functions.get(method);
		
		if (function == null) {
			functions.put(methodDescriptor, createFunction(method, methodDescriptor));
			function = functions.get(method);
		}

		return function;
	}
	
	private Function createFunction(Method method, MethodDescriptor methodDescriptor) {
		Function function = supply.tryToCreateFunction(methodDescriptor);
		if(function == null) {
			MethodDescription description = new MethodDescription(beanClass, method);
			throw new Bug(MessageFormat.format("The method '{0}' is not generated", description));
		} else {
			return function;
		}
	}
}
