package org.robobinding.widget.textview;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robobinding.util.RandomValues;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import android.widget.TextView;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@Config(manifest = Config.NONE)
@RunWith(RobolectricTestRunner.class)
public class TextColorAttributeTest {
	@Test
	public void whenUpdateView_thenViewShouldReflectChanges() {
		TextView view = new TextView(RuntimeEnvironment.application);
		TextColorAttribute attribute = new TextColorAttribute();
		int newColor = RandomValues.anyColor();

		attribute.updateView(view, newColor);

		assertThat(view.getCurrentTextColor(), equalTo(newColor));
	}
}