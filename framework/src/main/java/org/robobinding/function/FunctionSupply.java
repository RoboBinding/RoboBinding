package org.robobinding.function;


/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public interface FunctionSupply {

	Function tryToCreateFunction(MethodDescriptor method);

}
