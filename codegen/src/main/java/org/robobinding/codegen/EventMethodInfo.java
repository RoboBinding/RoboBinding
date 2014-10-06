package org.robobinding.codegen;




/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public interface EventMethodInfo {
	
	String name();
	
	boolean hasEventArg();
	
	String eventArgType();
	
	boolean hasReturn();

	String returnType();
}
