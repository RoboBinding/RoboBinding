package org.robobinding.widget.adapterview;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.robobinding.viewattribute.MockBindingContextBuilder.aBindingContext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.robobinding.BindingContext;
import org.robobinding.SubViewBinder;
import org.robobinding.property.ValueModel;
import org.robobinding.viewattribute.ValueModelUtils;

import android.view.View;
import android.view.ViewGroup;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@RunWith(MockitoJUnitRunner.class)
public class SubViewPresentationModelAttributeTest {
	@Test
	public void whenGetPresentatonModel_thenReturnCorrectResult() {
		String presentationModelPropertyName = "propertyName";
		Object presentationModel = mock(Object.class);
		ValueModel<Object> valueModel = ValueModelUtils.create(presentationModel);
		BindingContext bindingContext = aBindingContext()
				.withReadOnlyProperty(presentationModelPropertyName, valueModel)
				.build();
		
		SubViewPresentationModelAttribute subViewAttribute = new SubViewPresentationModelAttribute(null, 0, null);
		Object actualPM = subViewAttribute.getPresentationModel(bindingContext, presentationModelPropertyName);
		
		assertThat(actualPM, is(presentationModel));
	}
	
	@Test
	public void whenBind_thenBoundSubViewIsSetOnHolder() {
		int layoutId = 1;
		ViewGroup parent = mock(ViewGroup.class);
		View boundSubView = mock(View.class);
		Object presentationModel = mock(Object.class);
		SubViewBinder viewBinder = mock(SubViewBinder.class);
		SubViewHolder subViewHolder = mock(SubViewHolder.class);
		when(viewBinder.inflateAndBindWithoutAttachingToRoot(layoutId, presentationModel, parent)).thenReturn(boundSubView);
		SubViewPresentationModelAttribute subViewAttribute = new SubViewPresentationModelAttribute(parent, layoutId, subViewHolder);
		
		subViewAttribute.bind(viewBinder, presentationModel);
		
		verify(subViewHolder).setSubView(boundSubView);
	}
}
