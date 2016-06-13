package org.robobinding.codegen.presentationmodel.processor;

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
