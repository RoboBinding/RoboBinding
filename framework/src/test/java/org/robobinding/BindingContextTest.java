package org.robobinding;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.robobinding.MockPresentationModelAdapterBuilder.aPresentationModelAdapterWithDataSetProperty;

import org.junit.Test;
import org.robobinding.presentationmodel.PresentationModelAdapter;
import org.robobinding.property.DataSetValueModel;
import org.robobinding.util.RandomValues;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class BindingContextTest {
	@Test
	public void shouldItemBindingContextWithExpectedPreInitializeViews() {
		boolean expectedPreInitializeViews = RandomValues.trueOrFalse();
		
		String propertyName = "pName";
		DataSetValueModel dataSetProperty = mock(DataSetValueModel.class);
		when(dataSetProperty.preInitializingViewsWithDefault(anyBoolean())).thenReturn(expectedPreInitializeViews);
		PresentationModelAdapter presentationModelAdapter = aPresentationModelAdapterWithDataSetProperty(
				propertyName, dataSetProperty);
		
		BindingContext context = newBindingContext(presentationModelAdapter);
		ItemBindingContext itemBindingContext = context.navigateToItemContext(propertyName);
		
		assertThat(itemBindingContext.shouldPreInitializeViews(), is(expectedPreInitializeViews));
	}
	
	private BindingContext newBindingContext(PresentationModelAdapter presentationModelAdapter) {
		return new BindingContext(null, null, presentationModelAdapter, PreInitializingViews.initial(RandomValues.trueOrFalse()));
	}
}
