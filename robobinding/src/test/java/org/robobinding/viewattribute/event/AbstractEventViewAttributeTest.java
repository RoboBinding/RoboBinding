package org.robobinding.viewattribute.event;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.ParameterizedType;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.robobinding.viewattribute.ParameterizedTypeUtils;

import android.view.View;

import com.xtremelabs.robolectric.RobolectricTestRunner;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@RunWith(RobolectricTestRunner.class)
public abstract class AbstractEventViewAttributeTest<ViewType extends View,
	EventViewAttributeType extends EventViewAttribute<? super ViewType>> {

    protected ViewType view;
    protected EventViewAttributeType attribute;

    private MockCommand command;
    @Before
    public void initializeViewAndAttribute() {
	ParameterizedType superclass = (ParameterizedType) getClass().getGenericSuperclass();

	view = ParameterizedTypeUtils.createTypeArgument(superclass, 0);
	attribute = ParameterizedTypeUtils.createTypeArgument(superclass, 1);

	command = new MockCommand();
    }

    protected void bindAttribute() {
	attribute.bind(view, command);
    }

    protected void assertEventReceived(Class<?> expectedEventClass) {
	assertTrue("No event has been received yet", command.invocationCount >= 1);
	assertThat(command.lastArg, instanceOf(expectedEventClass));
    }

    @SuppressWarnings("unchecked")
    protected <T> T getEventReceived() {
	return (T) command.lastArg;
    }

    protected void assertTimesOfEventReceived(int expectedTimes) {
	assertThat(command.invocationCount, is(expectedTimes));
    }
}
