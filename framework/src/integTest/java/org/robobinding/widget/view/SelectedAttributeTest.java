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
public class SelectedAttributeTest {
	@Test
	public void whenUpdateView_thenViewShouldReflectChanges() {
		View view = new View(Robolectric.application);
		SelectedAttribute attribute = new SelectedAttribute();
		boolean selected = RandomValues.trueOrFalse();

		attribute.updateView(view, selected);

		assertThat(view.isSelected(), equalTo(selected));
	}

}