package org.robobinding.widget.textview;

import static org.junit.Assert.assertThat;
import static org.robobinding.widget.textview.CharSequenceMatcher.sameAs;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.robobinding.widget.AbstractPropertyViewAttributeTest;
import org.robolectric.annotation.Config;

import android.widget.TextView;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
@Config(manifest=Config.NONE)
public class TextAttributeTest extends
		AbstractPropertyViewAttributeTest<TextView, TextAttribute> {
	@Test
	public void whenUpdateView_thenViewShouldReflectChanges() {
		String newText = RandomStringUtils.random(5);

		attribute.updateView(view, newText);

		assertThat(view.getText(), sameAs(newText));
	}

}
