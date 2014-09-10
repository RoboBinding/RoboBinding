package org.robobinding.function;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.List;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
class FunctionImpl implements Function {
    private Object object;
    Method method;

    public FunctionImpl(Object object, Method method) {
	this.object = object;
	this.method = method;
    }

    @Override
    public Object call(Object... args) {
	try {
	    return method.invoke(object, args);
	} catch (IllegalArgumentException e) {
	    throw new RuntimeException(e);
	} catch (IllegalAccessException e) {
	    throw new RuntimeException(e);
	} catch (InvocationTargetException e) {
	    throw new RuntimeException("Error occurred when invoking method '" + describeMethod() + "', please check the original error stack below",
		    e.getCause());
	}
    }

    private String describeMethod() {
	List<String> parameterTypesInString = getParameterTypesInString();

	return MessageFormat.format("{0}.{1}({2})", method.getDeclaringClass().getName(), method.getName(),
		Joiner.on(", ").join(parameterTypesInString));
    }

    private List<String> getParameterTypesInString() {
	Class<?>[] parameterTypes = method.getParameterTypes();
	List<String> parameterTypesInString = Lists.newArrayList();
	for (Class<?> parameterType : parameterTypes) {
	    parameterTypesInString.add(parameterType.getName());
	}
	return parameterTypesInString;
    }
}