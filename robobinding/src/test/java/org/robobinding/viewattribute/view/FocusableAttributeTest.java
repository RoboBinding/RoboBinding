package org.robobinding.viewattribute.view;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.robobinding.viewattribute.AbstractPropertyViewAttributeTest;
import org.robobinding.viewattribute.RandomValues;

import android.view.View;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class FocusableAttributeTest extends AbstractPropertyViewAttributeTest<View, FocusableAttribute> {
    @Test
    public void whenValueModelUpdated_thenViewShouldReflectChanges() {
	boolean focusable = RandomValues.trueOrFalse();

	attribute.valueModelUpdated(focusable);

	assertThat(view.isFocusable(), equalTo(focusable));
    }

}
