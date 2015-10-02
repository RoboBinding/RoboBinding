package org.robobinding.widget.view;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robobinding.util.RandomValues;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Aurélien Catinon
 */
@Config(manifest = Config.NONE)
@RunWith(RobolectricTestRunner.class)
public class LayoutMarginAttributeTest {

	@Test
	public void whenUpdateView_thenViewShouldReflectChanges() {
		View view = new View(RuntimeEnvironment.application);
		LayoutMarginAttribute attribute = new LayoutMarginAttribute();
		int margin = RandomValues.anyIntegerGreaterThanZero();
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

		view.setLayoutParams(params);

		attribute.updateView(view, margin);

		assertThat(params.topMargin, equalTo(margin));
		assertThat(params.bottomMargin, equalTo(margin));
		assertThat(params.leftMargin, equalTo(margin));
		assertThat(params.rightMargin, equalTo(margin));
	}
}
