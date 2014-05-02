package org.robobinding.property;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class PresentationModelPropertyChangeSupportTest {
    private Bean bean;
    private PresentationModelPropertyChangeSupport propertyChangeSupport;

    @Before
    public void setUp() {
	bean = new Bean();
	propertyChangeSupport = new PresentationModelPropertyChangeSupport(bean);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenAddListenerToNonExistingProperty_thenThrowException() {
	createListenerOnProperty("nonExistingProperty");
    }

    @Test
    public void givenListenerOnProperty1_whenFirePropertyChange_thenShouldReceiveNotification() {
	MockPresentationModelPropertyChangeListener mockListener = createListenerOnProperty(Bean.PROPERTY1);

	propertyChangeSupport.firePropertyChange(Bean.PROPERTY1);

	assertTrue(mockListener.propertyChangedFired);
    }

    @Test
    public void givenListenerOnProperty1_whenRemoveIt_thenShouldNotReceiveNotification() {
	MockPresentationModelPropertyChangeListener mockListener = createListenerOnProperty(Bean.PROPERTY1);

	propertyChangeSupport.removePropertyChangeListener(Bean.PROPERTY1, mockListener);

	propertyChangeSupport.firePropertyChange(Bean.PROPERTY1);
	assertFalse(mockListener.propertyChangedFired);
    }

    @Test
    public void givenListenersOnProperty1AndProperty2_whenFireChangeAll_thenShouldAllReceiveNotifications() {
	MockPresentationModelPropertyChangeListener listenerOnProperty1 = createListenerOnProperty(Bean.PROPERTY1);
	MockPresentationModelPropertyChangeListener listenerOnProperty2 = createListenerOnProperty(Bean.PROPERTY2);

	propertyChangeSupport.fireChangeAll();

	assertTrue(listenerOnProperty1.propertyChangedFired);
	assertTrue(listenerOnProperty2.propertyChangedFired);
    }

    private MockPresentationModelPropertyChangeListener createListenerOnProperty(String propertyName) {
	MockPresentationModelPropertyChangeListener mockListener = new MockPresentationModelPropertyChangeListener();
	propertyChangeSupport.addPropertyChangeListener(propertyName, mockListener);
	return mockListener;
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
