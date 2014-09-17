package org.robobinding.viewattribute.property;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.robobinding.presentationmodel.PresentationModelAdapter;
import org.robobinding.property.ValueModel;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class MockPresentationModelAdapterBuilder {
	private PresentationModelAdapter mockPresentationModelAdapter;

	private MockPresentationModelAdapterBuilder() {
		mockPresentationModelAdapter = mock(PresentationModelAdapter.class);
	}

	public static <PropertyType> PresentationModelAdapter aPresentationModelAdapterWithReadOnlyProperty(String propertyName,
			ValueModel<PropertyType> propertyValueModel) {
		MockPresentationModelAdapterBuilder builder = new MockPresentationModelAdapterBuilder();
		builder.<PropertyType> declareReadOnlyProperty(propertyName, propertyValueModel);
		return builder.build();
	}

	private <PropertyType> MockPresentationModelAdapterBuilder declareReadOnlyProperty(String propertyName, ValueModel<PropertyType> propertyValueModel) {
		when(mockPresentationModelAdapter.<PropertyType> getReadOnlyPropertyValueModel(propertyName)).thenReturn(propertyValueModel);
		return this;
	}

	private PresentationModelAdapter build() {
		return mockPresentationModelAdapter;
	}

	public static <PropertyType> PresentationModelAdapter aPresentationModelAdapterWithProperty(String propertyName, ValueModel<PropertyType> propertyValueModel) {
		MockPresentationModelAdapterBuilder builder = new MockPresentationModelAdapterBuilder();
		builder.<PropertyType> declareProperty(propertyName, propertyValueModel);
		return builder.build();
	}

	private <PropertyType> MockPresentationModelAdapterBuilder declareProperty(String propertyName, ValueModel<PropertyType> propertyValueModel) {
		when(mockPresentationModelAdapter.<PropertyType> getPropertyValueModel(propertyName)).thenReturn(propertyValueModel);
		return this;
	}

}
