package org.robobinding.function;

import java.lang.reflect.Method;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public interface FunctionSupply {

	Function tryToCreateFunction(Method method);

}
