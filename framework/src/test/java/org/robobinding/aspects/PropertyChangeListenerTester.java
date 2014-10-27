package org.robobinding.aspects;

import org.junit.Assert;
import org.robobinding.property.PropertyChangeListener;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class PropertyChangeListenerTester implements PropertyChangeListener {
	private int timesOfPropertyChanged = 0;

	@Override
	public void propertyChanged() {
		timesOfPropertyChanged++;
	}

	public void assertPropertyChangedOnce() {
		assertTimesOfPropertyChanged(1);
	}

	public void assertTimesOfPropertyChanged(int expectedTimes) {
		Assert.assertEquals(expectedTimes, timesOfPropertyChanged);
	}
}
