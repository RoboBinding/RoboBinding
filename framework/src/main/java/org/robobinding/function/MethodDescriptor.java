package org.robobinding.function;

import java.util.Arrays;

import com.google.common.base.Objects;

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
		return Objects.hashCode(name, parameterTypes);
	}
}
