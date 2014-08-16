package org.robobinding.function;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;

import org.robobinding.internal.guava.Maps;
import org.robobinding.internal.guava.Objects;
import org.robobinding.util.MethodUtils;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class CachedFunctions implements Functions {
    private final Object object;
    Map<FunctionDescriptor, Function> functionCache;

    public CachedFunctions(Object object) {
	this.object = object;
	functionCache = Maps.newHashMap();
    }

    @Override
    public Function find(String functionName, Class<?>... parameterTypes) {
	FunctionDescriptor functionDescriptor = new FunctionDescriptor(functionName, parameterTypes);
	Function cachedFunction = functionCache.get(functionDescriptor);
	if (cachedFunction == null) {
	    return tryToCreateAndCacheFunction(functionDescriptor);
	}
	return cachedFunction;
    }

    private Function tryToCreateAndCacheFunction(FunctionDescriptor functionDescriptor) {
	Function function = createFunction(functionDescriptor);
	if (function != null) {
	    functionCache.put(functionDescriptor, function);
	}
	return function;
    }

    private Function createFunction(FunctionDescriptor functionDescriptor) {
	Method methodFound = MethodUtils.getMatchingAccessibleMethod(object.getClass(), functionDescriptor.functionName,
		functionDescriptor.parameterTypes);
	if (methodFound != null) {
	    return new FunctionImpl(object, methodFound);
	} else {
	    return null;
	}
    }

    static class FunctionDescriptor {
	private String functionName;
	private Class<?>[] parameterTypes;

	public FunctionDescriptor(String functionName, Class<?>... parameterTypes) {
	    this.functionName = functionName;
	    this.parameterTypes = parameterTypes;
	}

	@Override
	public boolean equals(Object other) {
	    if (this == other)
		return true;
	    if (!(other instanceof FunctionDescriptor))
		return false;

	    final FunctionDescriptor that = (FunctionDescriptor) other;
	    return Objects.equal(functionName, that.functionName) && Arrays.equals(parameterTypes, that.parameterTypes);
	}

	@Override
	public int hashCode() {
	    return Objects.hashCode(functionName) + Arrays.hashCode(parameterTypes);
	}
    }
}
