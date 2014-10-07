package org.robobinding.property;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class PropertyChangeSupportTest {
	private PropertyChangeSupport propertyChangeSupport;

	@Before
	public void setUp() {
		propertyChangeSupport = new PropertyChangeSupport(new PropertyValidation(Bean.class, PropertyUtils.getPropertyNames(Bean.class)));
	}

	@Test(expected = IllegalArgumentException.class)
	public void whenAddListenerToNonExistingProperty_thenThrowException() {
		createListenerOnProperty("nonExistingProperty");
	}

	@Test
	public void givenListenerOnProperty1_whenFirePropertyChange_thenShouldReceiveNotification() {
		MockPropertyChangeListener mockListener = createListenerOnProperty(Bean.PROPERTY1);

		propertyChangeSupport.firePropertyChange(Bean.PROPERTY1);

		assertTrue(mockListener.propertyChangedFired);
	}

	private MockPropertyChangeListener createListenerOnProperty(String propertyName) {
		MockPropertyChangeListener mockListener = new MockPropertyChangeListener();
		propertyChangeSupport.addPropertyChangeListener(propertyName, mockListener);
		return mockListener;
	}

	@Test
	public void givenAddListenerToPropertyTwice_whenFirePropertyChange_thenShouldReceiveNotificationOnlyOnce() {
		MockPropertyChangeListener mockListener = new MockPropertyChangeListener();
		propertyChangeSupport.addPropertyChangeListener(Bean.PROPERTY1, mockListener);
		propertyChangeSupport.addPropertyChangeListener(Bean.PROPERTY1, mockListener);

		propertyChangeSupport.firePropertyChange(Bean.PROPERTY1);

		assertThat(mockListener.timesNotified, is(1));
	}

	@Test
	public void givenListenerOnProperty1_whenRemoveIt_thenShouldNotReceiveNotification() {
		MockPropertyChangeListener mockListener = createListenerOnProperty(Bean.PROPERTY1);

		propertyChangeSupport.removePropertyChangeListener(Bean.PROPERTY1, mockListener);

		propertyChangeSupport.firePropertyChange(Bean.PROPERTY1);
		assertFalse(mockListener.propertyChangedFired);
	}

	@Test
	public void givenListenersOnProperty1AndProperty2_whenFireChangeAll_thenShouldAllReceiveNotifications() {
		MockPropertyChangeListener listenerOnProperty1 = createListenerOnProperty(Bean.PROPERTY1);
		MockPropertyChangeListener listenerOnProperty2 = createListenerOnProperty(Bean.PROPERTY2);

		propertyChangeSupport.fireChangeAll();

		assertTrue(listenerOnProperty1.propertyChangedFired);
		assertTrue(listenerOnProperty2.propertyChangedFired);
	}

	public static class Bean {
		public static final String PROPERTY1 = "property1";
		public static final String PROPERTY2 = "property2";

		public boolean getProperty1() {
			return true;
		}

		public String getProperty2() {
			return null;
		}
	}
}
