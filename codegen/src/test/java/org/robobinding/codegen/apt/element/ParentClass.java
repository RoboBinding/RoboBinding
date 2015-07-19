package org.robobinding.codegen.apt.element;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class ParentClass<T> {
	public static final int NUM_METHODS = 2;
	public static final int NUM_SETTERS = 1;
	
	public void parentMethod1() {}

	public void setParentPrimitiveSetter(int value) {}
}
