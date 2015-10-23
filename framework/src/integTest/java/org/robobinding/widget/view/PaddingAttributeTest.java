package org.robobinding.widget.view;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robobinding.util.RandomValues;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import android.view.View;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Aurélien Catinon
 */
@Config(manifest = Config.NONE)
@RunWith(RobolectricTestRunner.class)
public class PaddingAttributeTest {
	@Test
	public void whenUpdateView_thenViewShouldReflectChanges() {
		View view = new View(RuntimeEnvironment.application);
		PaddingAttribute attribute = new PaddingAttribute();
		int padding = RandomValues.anyIntegerGreaterThanZero();

		attribute.updateView(view, padding);

		assertThat(view.getPaddingTop(), equalTo(padding));
		assertThat(view.getPaddingBottom(), equalTo(padding));
		assertThat(view.getPaddingLeft(), equalTo(padding));
		assertThat(view.getPaddingRight(), equalTo(padding));
	}
}
