package org.robobinding.widget.view;

import static org.fest.assertions.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robobinding.util.RandomValues;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import android.graphics.drawable.ColorDrawable;
import android.view.View;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@Config(manifest=Config.NONE)
@RunWith(RobolectricTestRunner.class)
public class BackgroundColorAttributeTest {
	@Test
	public void whenUpdateView_thenViewShouldReflectChanges() {
		View view = new View(Robolectric.application);
		BackgroundColorAttribute attribute = new BackgroundColorAttribute();
		int newColor = RandomValues.anyColor();

		attribute.updateView(view, newColor);

		assertThat(view.getBackground()).isEqualTo(new ColorDrawable(newColor));
	}

}
