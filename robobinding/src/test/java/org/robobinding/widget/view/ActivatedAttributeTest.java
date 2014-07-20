package org.robobinding.widget.view;

import android.view.View;
import org.junit.Test;
import org.robobinding.viewattribute.RandomValues;
import org.robobinding.viewattribute.property.AbstractPropertyViewAttributeTest;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Aurélien Catinon
 */
public class ActivatedAttributeTest extends AbstractPropertyViewAttributeTest<View, ActivatedAttribute> {
    @Test
    public void whenUpdateView_thenViewShouldReflectChanges() {
        boolean activated = RandomValues.trueOrFalse();

        view.setActivated(!activated);

        attribute.updateView(view, activated);

        assertThat(view.isActivated(), equalTo(activated));
    }
}
