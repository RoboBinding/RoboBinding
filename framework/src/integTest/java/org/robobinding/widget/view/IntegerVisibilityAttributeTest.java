package org.robobinding.widget.view;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.robobinding.util.RandomValues;
import org.robobinding.widget.AbstractPropertyViewAttributeTest;
import org.robobinding.widget.view.VisibilityAttribute.IntegerVisibilityAttribute;
import org.robolectric.annotation.Config;

import android.view.View;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
@Config(manifest=Config.NONE)
public class IntegerVisibilityAttributeTest
		extends
		AbstractPropertyViewAttributeTest<View, IntegerVisibilityAttribute<View>> {
	private AbstractVisibility visibility;

	@Test
	public void whenUpdateView_thenViewShouldReflectChanges() {
		int visibilityValue = RandomValues.anyVisibility();

		attribute.updateView(view, visibilityValue);

		verify(visibility).setVisibility(visibilityValue);
	}

	@Override
	protected IntegerVisibilityAttribute<View> createAttribute() {
		visibility = mock(AbstractVisibility.class);
		return new IntegerVisibilityAttribute<View>(visibility);
	}
}
