package org.robobinding.codegen.presentationmodel.typemirror;

import org.apache.commons.lang3.ClassUtils;
import org.robobinding.codegen.presentationmodel.PropertyInfo;




/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class PropertyInfoImpl implements PropertyInfo {
	private final PropertyDescriptor descriptor;
	public PropertyInfoImpl(PropertyDescriptor descriptor) {
		this.descriptor = descriptor;
	}
	
	@Override
	public String name() {
		return descriptor.getName();
	}
	
	@Override
	public boolean isWritable() {
		return descriptor.isWritable();
	}
	
	@Override
	public boolean isReadable() {
		return descriptor.isReadable();
	}
	
	@Override
	public String typeName() {
		return ClassUtils.primitiveToWrapper(descriptor.getPropertyType()).getName();
	}

	@Override
	public String getter() {
		return descriptor.getReadMethodName();
	}

	@Override
	public String setter() {
		return descriptor.getWriteMethodName();
	}
}
