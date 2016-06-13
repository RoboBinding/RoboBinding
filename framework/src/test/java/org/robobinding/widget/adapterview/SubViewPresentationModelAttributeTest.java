package org.robobinding.widget.adapterview;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.robobinding.attribute.Attributes.aValueModelAttribute;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.robobinding.BindingContext;
import org.robobinding.SubBindingContext;

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
	public void whenBind_thenBoundSubViewIsSetOnHolder() {
		int layoutId = 1;
		String presentationModelPropertyName = "propertyName";

		ViewGroup parent = mock(ViewGroup.class);
		View boundSubView = mock(View.class);
		BindingContext context = mock(BindingContext.class);
		SubViewHolder subViewHolder = mock(SubViewHolder.class);
		SubBindingContext subBindingContext = mock(SubBindingContext.class);
		when(context.navigateToSubContext(presentationModelPropertyName)).thenReturn(subBindingContext);
		when(subBindingContext.inflateAndBindWithoutAttachingToRoot(layoutId, parent)).thenReturn(boundSubView);
		
		SubViewPresentationModelAttribute subViewAttribute = new SubViewPresentationModelAttribute(parent, layoutId, subViewHolder);
		subViewAttribute.setAttribute(aValueModelAttribute(presentationModelPropertyName));
		
		subViewAttribute.bindTo(context);
		
		verify(subViewHolder).setSubView(boundSubView);
	}
}
