package org.robobinding.function;

import java.util.Arrays;

import org.robobinding.util.Objects;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class MethodDescriptor {
	private final String name;
	private final Class<?>[] parameterTypes;
	
	public MethodDescriptor(String name, Class<?>[] parameterTypes) {
		this.name = name;
		this.parameterTypes = parameterTypes;
	}

	@Override
	public boolean equals(Object other) {
		if (this == other)
			return true;
		if (!(other instanceof MethodDescriptor))
			return false;

		final MethodDescriptor that = (MethodDescriptor) other;
		return Objects.equal(name, that.name) 
				&& Arrays.equals(parameterTypes, that.parameterTypes);
	}

	@Override
	public int hashCode() {
		int sum = Objects.hashCode(name);
		for(Class<?> parameterType : parameterTypes) {
			sum += Objects.hashCode(parameterType);
		}
		return sum;
	}

	public String getName() {
		return name;
	}

	public Class<?>[] getParameterTypes() {
		return parameterTypes;
	}
}
