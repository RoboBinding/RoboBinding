package org.robobinding.function;

import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.List;

import org.robobinding.util.Joiner;
import org.robobinding.util.Lists;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class MethodDescription {
	private final Class<?> targetType;
	private final Method method;
	
	public MethodDescription(Class<?> targetType, Method method) {
		this.targetType = targetType;
		this.method = method;
	}
	
	@Override
	public String toString() {
		List<String> parameterTypesInString = getParameterTypesInString();

		return MessageFormat.format("{0} {1}.{2}({3})",
				method.getReturnType().getName(),
				targetType.getName(), 
				method.getName(), 
				Joiner.on(", ").join(parameterTypesInString));

	}

	private List<String> getParameterTypesInString() {
		List<String> parameterTypesInString = Lists.newArrayList();
		for (Class<?> parameterType : method.getParameterTypes()) {
			parameterTypesInString.add(parameterType.getName());
		}
		return parameterTypesInString;
	}
}
