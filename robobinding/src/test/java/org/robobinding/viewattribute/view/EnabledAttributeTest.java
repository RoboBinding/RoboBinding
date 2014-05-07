package org.robobinding.viewattribute.view;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.robobinding.viewattribute.AbstractPropertyViewAttributeTest;
import org.robobinding.viewattribute.RandomValues;

import android.view.View;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class EnabledAttributeTest extends AbstractPropertyViewAttributeTest<View, EnabledAttribute> {
    @Test
    public void whenValueModelUpdated_thenViewShouldReflectChanges() {
	boolean enabled = RandomValues.trueOrFalse();

	attribute.valueModelUpdated(enabled);

	assertThat(view.isEnabled(), equalTo(enabled));
    }

}
