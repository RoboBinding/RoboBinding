package org.robobinding.widget.view;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robobinding.util.RandomValues;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import android.view.View;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@Config(manifest=Config.NONE)
@RunWith(RobolectricTestRunner.class)
public class FocusableAttributeTest {
	@Test
	public void whenUpdateView_thenViewShouldReflectChanges() {
		View view = new View(Robolectric.application);
		FocusableAttribute attribute = new FocusableAttribute();
		boolean focusable = RandomValues.trueOrFalse();

		attribute.updateView(view, focusable);

		assertThat(view.isFocusable(), equalTo(focusable));
	}

}
