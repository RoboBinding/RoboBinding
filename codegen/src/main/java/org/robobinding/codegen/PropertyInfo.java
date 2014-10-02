package org.robobinding.codegen;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class PropertyInfo {
	public String name() {
		return null;
	}
	
	public boolean isWritable() {
		return false;
	}
	
	public boolean isReadable() {
		return false;
	}
	
	public String typeName() {
		return type().getName();
	}
	private Class<?> type() {
		return null;
	}
}
