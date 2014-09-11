package org.robobinding.widget.menuitem;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Ignore;
import org.junit.Test;
import org.robobinding.viewattribute.RandomValues;
import org.robobinding.widget.AbstractPropertyViewAttributeTest;

import android.view.MenuItem;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class EnabledAttributeTest extends AbstractPropertyViewAttributeTest<MenuItem, EnabledAttribute> {
    @Ignore//TODO: cannot test at the moment, will try to test it when Robolectric is upgraded.
    @Test
    public void whenUpdateView_thenViewShouldReflectChanges() {
	boolean enabled = RandomValues.trueOrFalse();

	attribute.updateView(view, enabled);

	assertThat(view.isEnabled(), equalTo(enabled));
    }

}
