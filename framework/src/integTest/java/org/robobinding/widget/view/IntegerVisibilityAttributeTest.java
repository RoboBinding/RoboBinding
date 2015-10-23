package org.robobinding.widget.view;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robobinding.util.RandomValues;
import org.robobinding.widget.view.VisibilityAttribute.IntegerVisibilityAttribute;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import android.view.View;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
@Config(manifest = Config.NONE)
@RunWith(RobolectricTestRunner.class)
public class IntegerVisibilityAttributeTest {
	private AbstractVisibility visibility;

	@Test
	public void whenUpdateView_thenViewShouldReflectChanges() {
		View view = new View(RuntimeEnvironment.application);
		IntegerVisibilityAttribute<View> attribute = createAttribute();
		int visibilityValue = RandomValues.anyVisibility();

		attribute.updateView(view, visibilityValue);

		verify(visibility).setVisibility(visibilityValue);
	}

	private IntegerVisibilityAttribute<View> createAttribute() {
		visibility = mock(AbstractVisibility.class);
		return new IntegerVisibilityAttribute<View>(visibility);
	}
}
