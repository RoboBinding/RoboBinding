package org.robobinding.viewattribute.event;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.robobinding.BindingContext;
import org.robobinding.attribute.Command;
import org.robobinding.attribute.EventAttribute;
import org.robobinding.presentationmodel.PresentationModelAdapter;
import org.robobinding.viewattribute.ViewAttributeContractTest;
import org.robobinding.widget.view.ClickEvent;

import android.view.View;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
@RunWith(MockitoJUnitRunner.class)
public final class EventViewAttributeBinderTest extends ViewAttributeContractTest<EventViewAttributeBinder<View>> {
    @Mock
    private BindingContext bindingContext;
    @Mock
    private View view;
    @Mock
    private EventViewAttribute<View> viewAttribute;
    @Mock
    private EventAttribute attribute;
    private EventViewAttributeBinder<View> viewAttributeBinder;

    @Before
    public void setUp() {
	viewAttributeBinder = new EventViewAttributeBinder<View>(view, viewAttribute, attribute);
    }

    @Test
    public void givenAMatchingCommandWithArgs_whenBinding_thenBindWithTheCommand() {
	Command commandWithArgs = mock(Command.class);
	Mockito.<Class<?>>when(viewAttribute.getEventType()).thenReturn(ClickEvent.class);
	when(attribute.findCommand(bindingContext, ClickEvent.class)).thenReturn(commandWithArgs);

	viewAttributeBinder.bindTo(bindingContext);

	verify(viewAttribute).bind(view, commandWithArgs);
    }

    @Test
    public void givenAMatchingCommandWithNoArgs_whenBinding_thenBindWithTheCommand() {
	Command commandWithNoArgs = mock(Command.class);
	when(attribute.findCommand(bindingContext)).thenReturn(commandWithNoArgs);

	viewAttributeBinder.bindTo(bindingContext);

	verify(viewAttribute).bind(view, commandWithNoArgs);
    }

    @Test
    @Ignore
    @Override
    public void whenAnExceptionIsThrownDuringPreInitializingView_thenCatchAndRethrowAsBindingException() {
	//Ignored as PreInitializingView is not supported by event attribute.
    }

    @Override
    protected EventViewAttributeBinder<View> throwsExceptionDuringPreInitializingView() {
	throw new UnsupportedOperationException();
    }

    @Override
    protected EventViewAttributeBinder<View> throwsExceptionDuringBinding() {
	return new ExceptionDuringBinding(null, null, attribute);
    }

    private static class ExceptionDuringBinding extends EventViewAttributeBinder<View> {
	public ExceptionDuringBinding(View view, EventViewAttribute<View> viewAttribute, EventAttribute attribute) {
	    super(view, viewAttribute, attribute);
	}

	@Override
	void performBind(PresentationModelAdapter presentationModelAdapter) {
	    throw new RuntimeException();
	}
    }
}
