package org.robobinding.widget.view;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.robobinding.viewattribute.RandomValues;
import org.robobinding.viewattribute.property.AbstractPropertyViewAttributeTest;

import android.view.View;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class FocusableAttributeTest extends AbstractPropertyViewAttributeTest<View, FocusableAttribute> {
    @Test
    public void whenUpdateView_thenViewShouldReflectChanges() {
	boolean focusable = RandomValues.trueOrFalse();

	attribute.updateView(view, focusable);

	assertThat(view.isFocusable(), equalTo(focusable));
    }

}
