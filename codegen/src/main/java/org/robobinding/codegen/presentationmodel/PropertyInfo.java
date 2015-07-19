package org.robobinding.codegen.presentationmodel;

/**
 * @since 1.0
 * @author Cheng Wei
 * 
 */
public interface PropertyInfo {
	String name();

	boolean isWritable();

	boolean isReadable();

	String typeName();

	String getter();

	String setter();
}
