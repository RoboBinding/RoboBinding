package org.robobinding.widget.adapterview;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.robobinding.BindingContext;
import org.robobinding.PredefinedPendingAttributesForView;
import org.robobinding.attribute.PredefinedMappingsAttribute;

import android.content.Context;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
@RunWith(MockitoJUnitRunner.class)
public class ItemMappingAttributeTest {
	@Mock
	private BindingContext bindingContext;
	@Mock
	private Context context;
	@Mock
	private PredefinedMappingsAttribute predefinedMappingsAttribute;
	@Mock
	private Collection<PredefinedPendingAttributesForView> viewMappings;
	@Mock
	private PredefinedMappingUpdater predefinedMappingUpdater;

	@Test
	public void whenBinding_thenUpdateViewMappingsOnPredefinedMappingUpdater() {
		when(bindingContext.getContext()).thenReturn(context);
		when(predefinedMappingsAttribute.getViewMappings(context)).thenReturn(viewMappings);

		ItemMappingAttribute itemMappingAttribute = new ItemMappingAttribute(predefinedMappingUpdater);
		itemMappingAttribute.setAttribute(predefinedMappingsAttribute);

		itemMappingAttribute.bindTo(bindingContext);

		verify(predefinedMappingUpdater).updateViewMappings(viewMappings);
	}

}
