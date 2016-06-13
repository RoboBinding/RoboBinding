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
import org.robobinding.viewattribute.ViewAttributeContractTest;
import org.robobinding.widgetaddon.ViewAddOn;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
@RunWith(MockitoJUnitRunner.class)
public final class EventViewAttributeBinderTest extends ViewAttributeContractTest<EventViewAttributeBinder> {
	@Mock
	private BindingContext bindingContext;
	@Mock
	private Object view;
	@Mock
	private ViewAddOn viewAddOn;
	@Mock
	private EventViewAttribute<Object, ViewAddOn> viewAttribute;
	@Mock
	private EventAttribute attribute;
	private EventViewAttributeBinder viewAttributeBinder;

	@Before
	public void setUp() {
		viewAttributeBinder = new EventViewAttributeBinder(view, viewAddOn, viewAttribute, attribute);
	}

	@Test
	public void givenAMatchingCommandWithArgs_whenBinding_thenBindWithTheCommand() {
		Command commandWithArgs = mock(Command.class);
		Mockito.<Class<?>> when(viewAttribute.getEventType()).thenReturn(EventType.class);
		when(attribute.findCommand(bindingContext, EventType.class)).thenReturn(commandWithArgs);

		viewAttributeBinder.bindTo(bindingContext);

		verify(viewAttribute).bind(viewAddOn, commandWithArgs, view);
	}

	@Test
	public void givenAMatchingCommandWithNoArgs_whenBinding_thenBindWithTheCommand() {
		Command commandWithNoArgs = mock(Command.class);
		when(attribute.findCommand(bindingContext)).thenReturn(commandWithNoArgs);

		viewAttributeBinder.bindTo(bindingContext);

		verify(viewAttribute).bind(viewAddOn, commandWithNoArgs, view);
	}

	@Override
	protected EventViewAttributeBinder throwsExceptionDuringBinding() {
		return new ExceptionDuringBinding(attribute);
	}

	private static class ExceptionDuringBinding extends EventViewAttributeBinder {
		public ExceptionDuringBinding(EventAttribute attribute) {
			super(null, null, null, attribute);
		}
	
		@Override
		void performBind(BindingContext context) {
			throw new RuntimeException();
		}
	}

	@Test@Ignore("as PreInitializingView is not supported by event attribute")
	@Override
	public void whenAnExceptionIsThrownDuringPreInitializingView_thenCatchAndRethrowAsBindingException() {
	}

	@Override
	protected EventViewAttributeBinder throwsExceptionDuringPreInitializingView() {
		throw new UnsupportedOperationException();
	}

	public static class EventType{}
}
