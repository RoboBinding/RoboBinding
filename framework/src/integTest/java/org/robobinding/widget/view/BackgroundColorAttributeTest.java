package org.robobinding.widget.view;

import static org.fest.assertions.api.Assertions.assertThat;

import org.junit.Test;
import org.robobinding.util.RandomValues;
import org.robobinding.widget.AbstractPropertyViewAttributeTest;
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
public class BackgroundColorAttributeTest extends
		AbstractPropertyViewAttributeTest<View, BackgroundColorAttribute> {
	@Test
	public void whenUpdateView_thenViewShouldReflectChanges() {
		int newColor = RandomValues.anyColor();

		attribute.updateView(view, newColor);

		assertThat(view.getBackground()).isEqualTo(new ColorDrawable(newColor));
	}

}
