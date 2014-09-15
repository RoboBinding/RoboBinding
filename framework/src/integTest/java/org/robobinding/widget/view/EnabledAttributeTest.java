package org.robobinding.widget.view;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.robobinding.util.RandomValues;
import org.robobinding.widget.AbstractPropertyViewAttributeTest;
import org.robolectric.annotation.Config;

import android.view.View;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
@Config(manifest=Config.NONE)
public class EnabledAttributeTest extends
		AbstractPropertyViewAttributeTest<View, EnabledAttribute> {
	@Test
	public void whenUpdateView_thenViewShouldReflectChanges() {
		boolean enabled = RandomValues.trueOrFalse();

		attribute.updateView(view, enabled);

		assertThat(view.isEnabled(), equalTo(enabled));
	}

}
