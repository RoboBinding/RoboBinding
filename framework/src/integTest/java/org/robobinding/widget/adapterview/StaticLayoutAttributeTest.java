package org.robobinding.widget.adapterview;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.robobinding.util.RandomValues.anyInteger;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.robobinding.BindingContext;
import org.robobinding.attribute.StaticResourceAttribute;

import android.content.Context;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
@RunWith(MockitoJUnitRunner.class)
public class StaticLayoutAttributeTest {
	@Mock
	StaticResourceAttribute staticResourceAttribute;
	@Mock
	RowLayoutUpdater rowLayoutUpdater;
	@InjectMocks
	StaticLayoutAttribute staticLayoutAttribute;

	@Mock
	Context context;
	@Mock
	BindingContext bindingContext;

	@Test
	public void whenBinding_thenSetRowLayout() {
		int resourceId = anyInteger();
		when(bindingContext.getContext()).thenReturn(context);
		when(staticResourceAttribute.getResourceId(context)).thenReturn(resourceId);

		staticLayoutAttribute.bindTo(bindingContext);

		verify(rowLayoutUpdater).updateRowLayout(resourceId);
	}
}
