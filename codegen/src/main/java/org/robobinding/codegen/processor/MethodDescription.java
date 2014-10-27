package org.robobinding.codegen.processor;

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
	private final String name;
	private final String returnTypeName;
	private final Class<?>[] parameterTypes;
	
	public MethodDescription(String name, String returnTypeName, Class<?>[] parameterTypes) {
		this.name = name;
		this.returnTypeName = returnTypeName;
		this.parameterTypes = parameterTypes;
	}
	
	@Override
	public String toString() {
		List<String> parameterTypesInString = getParameterTypesInString();

		return MessageFormat.format("{0} {1}({2})",
				returnTypeName,
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
