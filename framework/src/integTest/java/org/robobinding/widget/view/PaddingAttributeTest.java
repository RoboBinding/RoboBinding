package org.robobinding.widget.view;

import android.view.View;

import org.junit.Test;
import org.robobinding.util.RandomValues;
import org.robobinding.widget.AbstractPropertyViewAttributeTest;
import org.robolectric.annotation.Config;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Aurélien Catinon
 */
@Config(manifest=Config.NONE)
public class PaddingAttributeTest extends
		AbstractPropertyViewAttributeTest<View, PaddingAttribute> {
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
