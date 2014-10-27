package org.robobinding.property;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class MockPropertyChangeListener implements PropertyChangeListener {
	public boolean propertyChangedFired = false;
	public int timesNotified = 0;

	@Override
	public void propertyChanged() {
		propertyChangedFired = true;
		timesNotified++;
	}
}