package org.robobinding.viewattribute.textview;

import static org.junit.Assert.assertThat;
import static org.robobinding.viewattribute.textview.CharSequenceMatcher.sameAs;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.robobinding.viewattribute.AbstractPropertyViewAttributeTest;

import android.widget.TextView;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class TextAttributeTest extends AbstractPropertyViewAttributeTest<TextView, TextAttribute> {
    @Test
    public void givenValueModelIsStringType_whenValueModelUpdated_thenViewShouldReflectChanges() {
	String newText = RandomStringUtils.random(5);

	attribute.valueModelUpdated(newText);

	assertThat(view.getText(), sameAs(newText));
    }

}
