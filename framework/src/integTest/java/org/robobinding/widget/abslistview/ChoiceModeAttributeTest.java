package org.robobinding.widget.abslistview;

import static android.widget.AbsListView.CHOICE_MODE_MULTIPLE;
import static android.widget.AbsListView.CHOICE_MODE_MULTIPLE_MODAL;
import static android.widget.AbsListView.CHOICE_MODE_NONE;
import static android.widget.AbsListView.CHOICE_MODE_SINGLE;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import android.widget.ListView;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
@Config(manifest = Config.NONE)
@RunWith(RobolectricTestRunner.class)
public class ChoiceModeAttributeTest {
	@Test
	public void whenUpdateView_thenSetChoiceMode() {
		ListView view = new ListView(Robolectric.application);
		ChoiceModeAttribute attribute = new ChoiceModeAttribute();
		int[] choiceModes = { CHOICE_MODE_NONE, CHOICE_MODE_SINGLE, CHOICE_MODE_MULTIPLE, CHOICE_MODE_MULTIPLE_MODAL };

		for (int choiceMode : choiceModes) {
			attribute.updateView(view, choiceMode);

			assertThat(view.getChoiceMode(), is(choiceMode));
		}
	}

}
