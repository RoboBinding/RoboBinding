package org.robobinding;

import static org.mockito.Mockito.when;

import org.mockito.Mockito;
import org.robobinding.presentationmodel.PresentationModelAdapter;
import org.robobinding.property.DataSetValueModel;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class MockPresentationModelAdapterBuilder {
	private PresentationModelAdapter mock;
	
	private MockPresentationModelAdapterBuilder() {
		mock = Mockito.mock(PresentationModelAdapter.class);
	}
	
	public MockPresentationModelAdapterBuilder withDataSetProperty(String propertyName,
			DataSetValueModel valueModel) {
		when(mock.getDataSetPropertyValueModel(propertyName)).thenReturn(valueModel);
		
		return this;
	}
	
	public PresentationModelAdapter build() {
		return mock;
	}
	
	public static MockPresentationModelAdapterBuilder aPresentationModelAdapter() {
		return new MockPresentationModelAdapterBuilder();
	}
	
	public static PresentationModelAdapter aPresentationModelAdapterWithDataSetProperty(String propertyName,
			DataSetValueModel valueModel) {
		return aPresentationModelAdapter().withDataSetProperty(propertyName, valueModel).build();
	}
}
