package org.robobinding.viewattribute;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.robobinding.BindingContext;
import org.robobinding.property.ValueModel;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class MockBindingContextBuilder {
	private BindingContext bindingContext;

	private MockBindingContextBuilder() {
		bindingContext = mock(BindingContext.class);
	}
	
	public <PropertyType> MockBindingContextBuilder withReadOnlyProperty(String propertyName, 
			ValueModel<PropertyType> propertyValueModel) {
		when(bindingContext.<PropertyType> getReadOnlyPropertyValueModel(propertyName)).thenReturn(propertyValueModel);
		return this;
	}

	public <PropertyType> MockBindingContextBuilder withProperty(String propertyName, 
			ValueModel<PropertyType> propertyValueModel) {
		when(bindingContext.<PropertyType> getPropertyValueModel(propertyName)).thenReturn(propertyValueModel);
		return this;
	}

	public BindingContext build() {
		return bindingContext;
	}
	
	public static MockBindingContextBuilder aBindingContext() {
		return new MockBindingContextBuilder();
	}
}
