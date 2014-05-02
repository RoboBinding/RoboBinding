package org.robobinding.viewattribute.view;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robobinding.viewattribute.AbstractPropertyViewAttributeTest;
import org.robobinding.viewattribute.RandomValues;

import android.view.View;

import com.xtremelabs.robolectric.RobolectricTestRunner;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@RunWith(RobolectricTestRunner.class)
public class SelectedAttributeTest extends AbstractPropertyViewAttributeTest<View, SelectedAttribute> {
    @Test
    public void whenValueModelUpdated_thenViewShouldReflectChanges() {
	boolean selected = RandomValues.trueOrFalse();

	attribute.valueModelUpdated(selected);

	assertThat(view.isSelected(), equalTo(selected));
    }

}