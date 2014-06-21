package org.robobinding.widget.progressbar;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.robobinding.viewattribute.RandomValues;
import org.robobinding.viewattribute.property.AbstractPropertyViewAttributeTest;

import android.widget.ProgressBar;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class SecondaryProgressAttributeTest extends AbstractPropertyViewAttributeTest<ProgressBar, SecondaryProgressAttribute> {
    @Test
    public void whenUpdateView_thenSetSecondaryProgressOnProgressBar() {
	int newProgress = RandomValues.anyInteger();

	attribute.updateView(view, newProgress);

	assertThat(view.getSecondaryProgress(), is(newProgress));
    }

}
