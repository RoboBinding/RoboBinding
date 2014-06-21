package org.robobinding.widget.edittext;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robobinding.viewattribute.event.AbstractEventViewAttributeTest;

import android.widget.EditText;

import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import com.xtremelabs.robolectric.shadows.ShadowTextView;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@RunWith(RobolectricTestRunner.class)
public class OnTextChangedAttributeTest extends AbstractEventViewAttributeTest<EditText, OnTextChangedAttribute> {
    @Test
    public void givenBoundAttribute_whenChangeText_thenEventReceived() {
	bindAttribute();

	changeText();

	assertEventReceived();
    }

    private void changeText() {
	ShadowTextView shadowTextView = Robolectric.shadowOf(view);
	shadowTextView.setText(RandomStringUtils.random(5));
    }

    private void assertEventReceived() {
	assertEventReceived(TextChangedEvent.class);
    }
}
