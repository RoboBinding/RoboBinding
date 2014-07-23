package org.robobinding.widget.abslistview;

import static android.widget.AbsListView.CHOICE_MODE_MULTIPLE;
import static android.widget.AbsListView.CHOICE_MODE_MULTIPLE_MODAL;
import static android.widget.AbsListView.CHOICE_MODE_NONE;
import static android.widget.AbsListView.CHOICE_MODE_SINGLE;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.robobinding.viewattribute.property.AbstractPropertyViewAttributeTest;

import android.widget.ListView;
import org.robobinding.widget.abslistview.ChoiceModeAttribute;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class ChoiceModeAttributeTest extends AbstractPropertyViewAttributeTest<ListView, ChoiceModeAttribute> {
    @Test
    public void whenUpdateView_thenSetChoiceMode() {
	int[] choiceModes = {CHOICE_MODE_NONE, CHOICE_MODE_SINGLE, CHOICE_MODE_MULTIPLE, CHOICE_MODE_MULTIPLE_MODAL};
	for (int choiceMode : choiceModes) {
	    attribute.updateView(view, choiceMode);

	    assertThat(view.getChoiceMode(), is(choiceMode));
	}
    }

}
