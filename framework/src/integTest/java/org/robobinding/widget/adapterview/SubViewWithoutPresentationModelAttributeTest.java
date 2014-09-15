package org.robobinding.widget.adapterview;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.robobinding.BindingContext;
import org.robobinding.SubViewBinder;

import android.view.View;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@RunWith(MockitoJUnitRunner.class)
public class SubViewWithoutPresentationModelAttributeTest {
	@Mock
	private SubViewHolder subViewHolder;
	@Mock
	private View staticSubView;
	private int layoutId = 2;

	@Test
	public void whenBindTo_thenStaticSubViewIsSetOnHolder() {
		BindingContext bindingContext = mock(BindingContext.class);
		SubViewBinder viewBinder = mock(SubViewBinder.class);
		when(bindingContext.createSubViewBinder()).thenReturn(viewBinder);
		when(viewBinder.inflate(layoutId)).thenReturn(staticSubView);
		SubViewWithoutPresentationModelAttribute attribute = new SubViewWithoutPresentationModelAttribute(
				layoutId, subViewHolder);

		attribute.bindTo(bindingContext);

		verify(subViewHolder).setSubView(staticSubView);
	}
}
