package org.robobinding.function;

import java.text.MessageFormat;
import java.util.List;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class MethodDescription {
	private final Class<?> targetType;
	private final String name;
	private final Class<?>[] parameterTypes;
	
	public MethodDescription(Class<?> targetType, String name, Class<?>[] parameterTypes) {
		this.targetType = targetType;
		this.name = name;
		this.parameterTypes = parameterTypes;
	}
	
	@Override
	public String toString() {
		List<String> parameterTypesInString = getParameterTypesInString();

		return MessageFormat.format("{0}.{1}({2})", 
				targetType, 
				name, 
				Joiner.on(", ").join(parameterTypesInString));

	}

	private List<String> getParameterTypesInString() {
		List<String> parameterTypesInString = Lists.newArrayList();
		for (Class<?> parameterType : parameterTypes) {
			parameterTypesInString.add(parameterType.getName());
		}
		return parameterTypesInString;
	}
}
