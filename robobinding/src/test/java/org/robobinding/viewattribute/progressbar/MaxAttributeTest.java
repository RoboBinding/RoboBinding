package org.robobinding.viewattribute.progressbar;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.robobinding.viewattribute.AbstractPropertyViewAttributeTest;
import org.robobinding.viewattribute.RandomValues;

import android.widget.ProgressBar;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class MaxAttributeTest extends AbstractPropertyViewAttributeTest<ProgressBar, MaxAttribute> {
    @Test
    public void whenUpdatingValueModel_thenSetMaxOnProgressBar() {
	int newMaxValue = RandomValues.anyInteger();

	attribute.valueModelUpdated(newMaxValue);

	assertThat(view.getMax(), is(newMaxValue));
    }

}
