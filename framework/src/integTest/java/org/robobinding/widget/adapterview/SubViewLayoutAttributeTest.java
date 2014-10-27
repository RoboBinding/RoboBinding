package org.robobinding.widget.adapterview;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.robobinding.BindingContext;
import org.robobinding.attribute.StaticResourceAttribute;

import android.content.Context;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@RunWith(MockitoJUnitRunner.class)
public class SubViewLayoutAttributeTest {
	@Mock
	private BindingContext bindingContext;
	@Mock
	private StaticResourceAttribute attribute;
	private int layoutId = 1;

	@Test
	public void givenAttribute_whenBindTo_thenLayoutIdIsCorrectlySet() {
		when(attribute.getResourceId(any(Context.class))).thenReturn(layoutId);
		SubViewLayoutAttribute subViewLayoutAttribute = new SubViewLayoutAttribute();
		subViewLayoutAttribute.setAttribute(attribute);

		subViewLayoutAttribute.bindTo(bindingContext);

		assertThat(subViewLayoutAttribute.getLayoutId(), is(layoutId));
	}
}
