package org.robobinding.property;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@RunWith(MockitoJUnitRunner.class)
public class ObservableBeanWrapTest {
    private static final String PROPERTY_NAME = "property";
    @Mock
    private PropertyChangeListener listener;

    @Test
    public void testGivenObservableBean_whenAddPropertyChangeListener_thenSuccessful() {
	Object bean = mock(ObservableBean.class);
	ObservableBeanWrap observableBeanWrap = new ObservableBeanWrap(bean);

	observableBeanWrap.addPropertyChangeListener(PROPERTY_NAME, listener);
    }

    @Test(expected = RuntimeException.class)
    public void givenNonObservableBean_whenAddPropertyChangeListener_thenThrowException() {
	Object bean = mock(Object.class);
	ObservableBeanWrap observableBeanWrap = new ObservableBeanWrap(bean);

	observableBeanWrap.addPropertyChangeListener(PROPERTY_NAME, listener);
    }

    @Test
    public void givenObservableBean_whenRemovePropertyChangeListener_thenSuccessful() {
	ObservableBean bean = mock(ObservableBean.class);
	ObservableBeanWrap observableBeanWrap = new ObservableBeanWrap(bean);

	observableBeanWrap.removePropertyChangeListener(PROPERTY_NAME, listener);
	verify(bean).removePropertyChangeListener(PROPERTY_NAME, listener);
    }

    @Test
    public void testGivenNonObservableBean_whenRemovePropertyChangeListener_thenOperationIgnored() {
	Object bean = mock(Object.class);
	ObservableBeanWrap observableBeanWrap = new ObservableBeanWrap(bean);

	observableBeanWrap.removePropertyChangeListener(PROPERTY_NAME, listener);
    }
}
