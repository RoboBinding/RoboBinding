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
	private final Map<Method, Function> functions;
	private final FunctionSupply supply;

	public LazyFunctions(Class<?> beanClass, Set<Method> methods, FunctionSupply supply) {
		this.beanClass = beanClass;
		this.functions = Maps.newHashMap();
		this.supply = supply;
		
		initializeFunctions(methods);
	}

	private void initializeFunctions(Set<Method> methods) {
		for(Method method : methods) {
			functions.put(method, null);
		}
	}

	@Override
	public Function find(String functionName, Class<?>... parameterTypes) {
		Method method = MethodUtils.getMatchingAccessibleMethod(beanClass, functionName, parameterTypes);
		
		if(method == null) {
			return null;
		}
		
		Function function = functions.get(method);
		
		if (function == null) {
			functions.put(method, createFunction(method));
			function = functions.get(method);
		}

		return function;
	}
	
	private Function createFunction(Method method) {
		Function function = supply.tryToCreateFunction(method);
		if(function == null) {
			MethodDescription description = createMethodDecription(method.getName(), method.getParameterTypes());
			throw new Bug(MessageFormat.format("The method '{0}' is not generated", description));
		} else {
			return function;
		}
	}
	
	private MethodDescription createMethodDecription(String name, Class<?>... parameterTypes) {
		return  new MethodDescription(beanClass, name, parameterTypes);
	}
}
