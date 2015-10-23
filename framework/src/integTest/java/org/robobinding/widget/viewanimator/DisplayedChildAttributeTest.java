package org.robobinding.widget.viewanimator;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robobinding.util.RandomValues;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import android.widget.TextView;
import android.widget.ViewAnimator;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Aurélien Catinon
 */
@Config(manifest = Config.NONE)
@RunWith(RobolectricTestRunner.class)
public class DisplayedChildAttributeTest {

	@Test
	public void whenUpdateView_thenViewShouldReflectChanges() {
		ViewAnimator view = new ViewAnimator(RuntimeEnvironment.application);
		DisplayedChildAttribute attribute = new DisplayedChildAttribute();
		int numChilds = 5;
		int displayedChild = RandomValues.nextInt(numChilds);

		for (int i = 0; i < numChilds; i++) {
			view.addView(new TextView(view.getContext()));
		}

		attribute.updateView(view, displayedChild);

		assertThat(view.getDisplayedChild(), equalTo(displayedChild));
	}
}
