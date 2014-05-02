package org.robobinding.viewattribute;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.robobinding.viewattribute.MockCommandViewAttributeConfigBuilder.aCommandViewAttributeConfig;

import java.lang.reflect.ParameterizedType;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.robobinding.BindingContext;
import org.robobinding.function.MockFunction;

import android.view.View;

import com.xtremelabs.robolectric.RobolectricTestRunner;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@RunWith(RobolectricTestRunner.class)
public abstract class AbstractCommandViewAttributeTest<ViewType extends View, 
	CommandViewAttributeType extends AbstractCommandViewAttribute<? super ViewType>> {
    private final String commandName = "someCommand";

    private MockFunction mockFunction;
    private BindingContext bindingContext;

    protected ViewType view;
    protected CommandViewAttributeType attribute;

    @Before
    public void initializeViewAndAttribute() {
	mockFunction = new MockFunction();

	bindingContext = mock(BindingContext.class);
	when(bindingContext.findFunction(eq(commandName), (Class<?>) any())).thenReturn(mockFunction);

	createViewAndAttribute();
	initializeAttribute();
    }

    private void createViewAndAttribute() {
	ParameterizedType superclass = (ParameterizedType) getClass().getGenericSuperclass();

	view = ParameterizedTypeUtils.createTypeArgument(superclass, 0);
	attribute = ParameterizedTypeUtils.createTypeArgument(superclass, 1);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private void initializeAttribute() {
	attribute.initialize((CommandViewAttributeConfig) aCommandViewAttributeConfig(view, commandName));
    }

    protected void bindAttribute() {
	attribute.bindTo(bindingContext);
    }

    protected void assertEventReceived(Class<?> eventClass) {
	assertTrue(mockFunction.commandInvoked);
	assertThat(mockFunction.argsPassedToInvoke[0], instanceOf(eventClass));
    }

    @SuppressWarnings("unchecked")
    protected <T> T getEventReceived() {
	return (T) mockFunction.argsPassedToInvoke[0];
    }

    protected int eventInvocationCount() {
	return mockFunction.invocationCount;
    }
}
