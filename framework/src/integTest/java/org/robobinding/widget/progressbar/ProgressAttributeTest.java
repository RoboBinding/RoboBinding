package org.robobinding.widget.progressbar;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.robobinding.util.RandomValues;
import org.robolectric.annotation.Config;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
@Config(manifest=Config.NONE)
public class ProgressAttributeTest extends AbstractProgressBarAttributeTest {
	@Test
	public void whenUpdateView_thenSetProgressOnProgressBar() {
		ProgressAttribute attribute = new ProgressAttribute();
		int newProgress = RandomValues.anyInteger();

		attribute.updateView(view, newProgress);

		assertThat(view.getProgress(), is(newProgress));
	}
}
