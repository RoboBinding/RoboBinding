package org.robobinding.widget.view;

import android.view.View;
import org.junit.Test;
import org.robobinding.viewattribute.RandomValues;
import org.robobinding.viewattribute.property.AbstractPropertyViewAttributeTest;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created by aurel on 31/08/14.
 */
public class PaddingAttributeTest extends AbstractPropertyViewAttributeTest<View, PaddingAttribute> {
    @Test
    public void whenUpdateView_thenViewShouldReflectChanges() {
        int padding = RandomValues.anyIntegerGreaterThanZero();

        attribute.updateView(view, padding);

        assertThat(view.getPaddingTop(), equalTo(padding));
        assertThat(view.getPaddingBottom(), equalTo(padding));
        assertThat(view.getPaddingLeft(), equalTo(padding));
        assertThat(view.getPaddingRight(), equalTo(padding));
    }
}
