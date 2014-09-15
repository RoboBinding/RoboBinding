package org.robobinding.widget.edittext;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robobinding.widget.AbstractEventViewAttributeTest;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import android.widget.EditText;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@Config(manifest=Config.NONE)
@RunWith(RobolectricTestRunner.class)
public class OnTextChangedAttributeTest extends
		AbstractEventViewAttributeTest<EditText, OnTextChangedAttribute> {
	@Test
	public void givenBoundAttribute_whenChangeText_thenEventReceived() {
		bindAttribute();

		changeText();

		assertEventReceived();
	}

	private void changeText() {
		view.setText(RandomStringUtils.random(5));
	}

	private void assertEventReceived() {
		assertEventReceived(TextChangedEvent.class);
	}
}
