package org.robobinding.widget.menuitem;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robobinding.util.RandomValues;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import android.view.MenuItem;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@Config(manifest = Config.NONE)
@RunWith(RobolectricTestRunner.class)
public class EnabledAttributeTest {
	@Ignore
	// TODO: cannot test at the moment, will try to test it when Robolectric is
	// upgraded.
	@Test
	public void whenUpdateView_thenViewShouldReflectChanges() {
		MenuItem view = null;
		EnabledAttribute attribute = new EnabledAttribute();
		boolean enabled = RandomValues.trueOrFalse();

		attribute.updateView(view, enabled);

		assertThat(view.isEnabled(), equalTo(enabled));
	}

}
