package org.robobinding.widget.view;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

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
public class LayoutMarginAttributeTest extends
		AbstractPropertyViewAttributeTest<View, LayoutMarginAttribute> {

	@Test
	public void whenUpdateView_thenViewShouldReflectChanges() {
		int margin = RandomValues.anyIntegerGreaterThanZero();
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT);

		view.setLayoutParams(params);

		attribute.updateView(view, margin);

		assertThat(params.topMargin, equalTo(margin));
		assertThat(params.bottomMargin, equalTo(margin));
		assertThat(params.leftMargin, equalTo(margin));
		assertThat(params.rightMargin, equalTo(margin));
	}
}
