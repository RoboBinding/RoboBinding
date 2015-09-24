package org.robobinding.codegen.presentationmodel;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public interface VariousEventMethods {
	int numEventMethods = 4;
	
	void eventMethod();
	
	void eventMethodWithArg(Object event);
	
	int eventMethodWithReturn();
	
	int eventMethodWithReturnAndArg(Object event);

}
