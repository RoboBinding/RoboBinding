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
 * @author Robert Taylor
 */
public class EnabledAttributeTest extends AbstractPropertyViewAttributeTest<View, EnabledAttribute> {
    @Test
    public void whenUpdateView_thenViewShouldReflectChanges() {
	boolean enabled = RandomValues.trueOrFalse();

	attribute.updateView(view, enabled);

	assertThat(view.isEnabled(), equalTo(enabled));
    }

}
