package org.robobinding.codegen.apt.element;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class Klass extends ParentClass<Integer> {
	public static final int NUM_METHODS = 5;
	public static final int NUM_SETTERS = 3;
	
	public void method1() {}
	
	public void setNotSetter(){}
	
	public void setPrimitiveSetter(int value) {}
	
	public void setObjectSetter(Object value){}
	
	public boolean setLooseSetter(boolean value){return false;}
}
