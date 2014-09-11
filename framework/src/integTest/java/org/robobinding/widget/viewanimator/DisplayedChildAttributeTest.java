package org.robobinding.widget.viewanimator;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.robobinding.viewattribute.RandomValues;
import org.robobinding.widget.AbstractPropertyViewAttributeTest;
import org.robobinding.widget.viewanimator.DisplayedChildAttribute;

import android.widget.TextView;
import android.widget.ViewAnimator;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Aurélien Catinon
 */
public class DisplayedChildAttributeTest extends AbstractPropertyViewAttributeTest<ViewAnimator, DisplayedChildAttribute> {

    @Test
    public void whenUpdateView_thenViewShouldReflectChanges() {
	Integer newInteger = RandomValues.nextInt(5);

	for (int i = 0; i < 5; i++) {
	    view.addView(new TextView(view.getContext()));
	}

	attribute.updateView(view, newInteger);

	assertThat(view.getDisplayedChild(), equalTo(newInteger));
    }
}
