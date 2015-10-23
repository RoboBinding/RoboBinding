package org.robobinding.widget.edittext;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robobinding.widget.EventCommand;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import android.widget.EditText;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@Config(manifest = Config.NONE)
@RunWith(RobolectricTestRunner.class)
public class OnTextChangedAttributeTest {
	private EditText view;
	private EventCommand eventCommand;

	@Before
	public void setUp() {
		view = new EditText(RuntimeEnvironment.application);
		eventCommand = new EventCommand();
	}

	@Test
	public void givenBoundAttribute_whenChangeText_thenEventReceived() {
		OnTextChangedAttribute attribute = new OnTextChangedAttribute();
		attribute.bind(null, eventCommand, view);

		changeText();

		assertEventReceived();
	}

	private void changeText() {
		view.setText(RandomStringUtils.random(5));
	}

	private void assertEventReceived() {
		eventCommand.assertEventReceived(TextChangedEvent.class);
	}
}
