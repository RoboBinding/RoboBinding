package org.robobinding.customviewbinding;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.robobinding.viewattribute.property.OneWayPropertyViewAttribute;
import org.robobinding.viewattribute.property.OneWayPropertyViewAttributeFactory;
import org.robobinding.viewbinding.BindingAttributeMappings;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class KeepFirstAttributesTest {
	private String attributeName = "attributeName";
	@Mock
	private BindingAttributeMappings<ViewType> bindingAttributeMappings;
	private KeepFirstAttributes<ViewType> keepFirstAttributes;

	@Before
	public void setUp() {
		keepFirstAttributes = new KeepFirstAttributes<ViewType>(bindingAttributeMappings);
	}
	
	@Test
	public void whenMapSameOneWayPropertyAgain_thenMappedOnceOnly() {
		Class<OneWayPropertyViewAttribute1> viewAttributeClass = null;
		
		keepFirstAttributes.mapOneWayProperty(viewAttributeClass, attributeName);
		keepFirstAttributes.mapOneWayProperty(viewAttributeClass, attributeName);
		
		verify(bindingAttributeMappings, times(1)).mapOneWayProperty(viewAttributeClass, attributeName);
	}
	
	@Test
	public void whenMapSameOneWayPropertyByFactoryAgain_thenMappedOnceOnly() {
		Class<OneWayPropertyViewAttribute1> viewAttributeClass = null;
		OneWayPropertyViewAttributeFactory<ViewType> factory = null;
		
		keepFirstAttributes.mapOneWayProperty(viewAttributeClass, attributeName);
		keepFirstAttributes.mapOneWayProperty(factory, attributeName);
		
		verify(bindingAttributeMappings, times(1)).mapOneWayProperty(viewAttributeClass, attributeName);
	}
	
	public static interface ViewType {}
	public static interface OneWayPropertyViewAttribute1 extends OneWayPropertyViewAttribute<ViewType, Object>{}
}
