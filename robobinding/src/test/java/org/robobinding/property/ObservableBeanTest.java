package org.robobinding.property;

import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class ObservableBeanTest {
    private static final String PROPERTY_NAME = "property";

    private ObservableBean observableBean;
    private PresentationModelPropertyChangeListener listener;

    @Before
    public void setUp() {
	listener = new MockPresentationModelPropertyChangeListener();
    }

    @Test
    public void testGivenObservableTestBean_whenAddPropertyChangeListener_thenSuccessful() {
	ObservableProperties testBean = mock(ObservableProperties.class);
	createObservableBean(testBean);

	addPropertyChangeListener();
    }

    @Test(expected = RuntimeException.class)
    public void givenNonObservableTestBean_whenAddPropertyChangeListener_thenThrowException() {
	Object testBean = mock(Object.class);
	createObservableBean(testBean);

	addPropertyChangeListener();
    }

    private void addPropertyChangeListener() {
	observableBean.addPropertyChangeListener(PROPERTY_NAME, listener);
    }

    @Test
    public void givenObservableTestBean_whenRemovePropertyChangeListener_thenSuccessful() {
	ObservableProperties testBean = mock(ObservableProperties.class);
	createObservableBean(testBean);

	removePropertyChangeListener();
    }

    @Test
    public void testGivenNonObservableBean_whenRemovePropertyChangeListener_thenOperationIgnored() {
	Object testBean = mock(Object.class);
	createObservableBean(testBean);

	removePropertyChangeListener();
    }

    private void removePropertyChangeListener() {
	observableBean.removePropertyChangeListener(PROPERTY_NAME, listener);
    }

    private void createObservableBean(Object bean) {
	observableBean = new ObservableBean(bean);
    }
}
