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
 * @author Aurélien Catinon
 */
@Config(manifest = Config.NONE)
@RunWith(RobolectricTestRunner.class)
public class ActivatedAttributeTest {
	@Test
	public void whenUpdateView_thenViewShouldReflectChanges() {
		View view = new View(Robolectric.application);
		ActivatedAttribute attribute = new ActivatedAttribute();
		boolean activated = RandomValues.trueOrFalse();

		view.setActivated(!activated);

		attribute.updateView(view, activated);

		assertThat(view.isActivated(), equalTo(activated));
	}
}
