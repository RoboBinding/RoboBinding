package org.robobinding.codegen;




/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class PropertyInfo {
	private final PropertyDescriptor descriptor;
	public PropertyInfo(PropertyDescriptor descriptor) {
		this.descriptor = descriptor;
	}
	
	public String name() {
		return descriptor.getName();
	}
	
	public boolean isWritable() {
		return descriptor.isWritable();
	}
	
	public boolean isReadable() {
		return descriptor.isReadable();
	}
	
	public Class<?> type() {
		return descriptor.getPropertyType();
	}

	public String getter() {
		return descriptor.getReadMethodName();
	}

	public String setter() {
		return descriptor.getWriteMethodName();
	}
}
