package org.robobinding.widget.textview;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.robobinding.util.RandomValues;
import org.robobinding.widget.AbstractPropertyViewAttributeTest;
import org.robolectric.annotation.Config;

import android.widget.TextView;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@Config(manifest=Config.NONE)
public class TextColorAttributeTest extends
		AbstractPropertyViewAttributeTest<TextView, TextColorAttribute> {
	@Test
	public void whenUpdateView_thenViewShouldReflectChanges() {
		int newColor = RandomValues.anyColor();

		attribute.updateView(view, newColor);

		assertThat(view.getCurrentTextColor(), equalTo(newColor));
	}
}
