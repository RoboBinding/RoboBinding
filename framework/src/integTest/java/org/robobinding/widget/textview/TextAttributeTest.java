package org.robobinding.widget.textview;

import static org.junit.Assert.assertThat;
import static org.robobinding.widget.textview.CharSequenceMatcher.sameAs;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import android.widget.TextView;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
@Config(manifest = Config.NONE)
@RunWith(RobolectricTestRunner.class)
public class TextAttributeTest {
	@Test
	public void whenUpdateView_thenViewShouldReflectChanges() {
		TextView view = new TextView(RuntimeEnvironment.application);
		TextAttribute attribute = new TextAttribute();
		String newText = RandomStringUtils.random(5);

		attribute.updateView(view, newText);

		assertThat(view.getText(), sameAs(newText));
	}

}