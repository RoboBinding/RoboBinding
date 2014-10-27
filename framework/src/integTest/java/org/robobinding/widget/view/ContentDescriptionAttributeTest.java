package org.robobinding.widget.view;

import static org.junit.Assert.assertThat;
import static org.robobinding.widget.textview.CharSequenceMatcher.sameAs;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import android.view.View;

/**
 * @since 8.9
 * @version $Revision: 8.9 $
 * @author Nathan Hunston
 */
@Config(manifest = Config.NONE)
@RunWith(RobolectricTestRunner.class)
public class ContentDescriptionAttributeTest {
	@Test
	public void whenUpdateView_thenViewShouldReflectChanges() {
		View view = new View(Robolectric.application);
		ContentDescriptionAttribute attribute = new ContentDescriptionAttribute();
		String newValue = RandomStringUtils.random(5);

		attribute.updateView(view, newValue);

		assertThat(view.getContentDescription(), sameAs(newValue));
	}
}
