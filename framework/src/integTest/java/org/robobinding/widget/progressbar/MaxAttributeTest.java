package org.robobinding.widget.progressbar;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robobinding.util.RandomValues;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import android.widget.ProgressBar;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
@Config(manifest = Config.NONE)
@RunWith(RobolectricTestRunner.class)
public class MaxAttributeTest {
	@Test
	public void whenUpdateView_thenSetMaxOnProgressBar() {
		ProgressBar view = new ProgressBar(Robolectric.application);
		MaxAttribute attribute = new MaxAttribute();
		int newMaxValue = RandomValues.anyInteger();

		attribute.updateView(view, newMaxValue);

		assertThat(view.getMax(), is(newMaxValue));
	}

}
