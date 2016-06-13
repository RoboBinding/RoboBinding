package org.robobinding.property;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.Set;

import org.robobinding.util.Joiner;


/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class PropertyDescriptor {
	private Class<?> beanType;
	private Class<?> propertyType;
	private String name;
	private boolean readable;
	private boolean writable;
	
	public PropertyDescriptor(Class<?> beanType, Class<?> propertyType, String name, boolean readable, boolean writable) {
		this.beanType = beanType;
		this.propertyType = propertyType;
		this.name = name;
		this.readable = readable;
		this.writable = writable;
	}

	public Class<?> getPropertyType() {
		return propertyType;
	}

	public String getName() {
		return name;
	}
	
	public void checkReadWriteProperty(boolean isReadWriteProperty) {
		checkReadable();
		if (isReadWriteProperty) {
			checkWritable();
		}
	}
	
	private void checkReadable() {
		if (!readable) {
			throw new RuntimeException("The property " + getShortDescription() + " is not readable");
		}
	}

	private void checkWritable() {
		if (!writable) {
			throw new RuntimeException("The property " + getShortDescription() + " is not writable");
		}
	}
	
	public String getShortDescription() {
		return PropertyUtils.shortDescription(beanType, name);
	}

	public String getDescription() {
		return decriptionWithDependencies(Collections.<String>emptySet());
	}

	public String decriptionWithDependencies(Set<String> dependentProperties) {
		String description = MessageFormat.format(
				"property(name:{0}, propertyType:{1}, isReadable:{2}, isWritable:{3}, beanType:{4}", 
				name, propertyType.getName(), readable, writable, beanType.getName());

		if (!dependentProperties.isEmpty()) {
			description += MessageFormat.format(", dependencies:{0}", Joiner.on(",").join(dependentProperties));
		}
		description += ")";
		return description;
	}

}
