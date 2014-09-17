package org.robobinding.test;

import org.robobinding.property.PropertyChangeListener;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class PresentationModelPropertyChangeSpy implements PropertyChangeListener {
	private boolean propertyChanged;
	private int propertyChangedCount;

	PresentationModelPropertyChangeSpy() {
	}

	@Override
	public void propertyChanged() {
		propertyChanged = true;
		propertyChangedCount++;
	}

	public boolean isPropertyChanged() {
		return propertyChanged;
	}

	public int getPropertyChangedCount() {
		return propertyChangedCount;
	}

}
