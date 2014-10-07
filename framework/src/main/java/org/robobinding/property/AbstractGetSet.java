package org.robobinding.property;

import org.robobinding.Bug;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public abstract class AbstractGetSet<T> {
	private final PropertyDescriptor descriptor;
	public AbstractGetSet(PropertyDescriptor descriptor) {
		this.descriptor = descriptor;
	}
	
	public T getValue() {
		throw new Bug("The property " + descriptor.getShortDescription() + " is not readable");
	}

	public void setValue(T newValue) {
		throw new Bug("The property " + descriptor.getShortDescription() + " is not writable");
	}

}
