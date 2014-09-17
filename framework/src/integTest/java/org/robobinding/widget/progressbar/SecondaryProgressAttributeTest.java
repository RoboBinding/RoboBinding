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
@Config(manifest = Config.NONE)
public class SecondaryProgressAttributeTest extends AbstractProgressBarAttributeTest {

	@Test
	public void whenUpdateView_thenSetSecondaryProgressOnProgressBar() {
		SecondaryProgressAttribute attribute = new SecondaryProgressAttribute();
		int newProgress = RandomValues.anyInteger();

		attribute.updateView(view, newProgress);

		assertThat(view.getSecondaryProgress(), is(newProgress));
	}

}
