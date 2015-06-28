package org.robobinding.widget.edittext;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class CharSequenceMatcher extends TypeSafeMatcher<CharSequence> {
	private final CharSequence value;

	public CharSequenceMatcher(CharSequence value) {
		this.value = value;
	}

	@Override
	public boolean matchesSafely(CharSequence item) {
		return value.toString().equals(item.toString());
	}

	@Override
	public void describeTo(Description description) {
		description.appendText("equivalent text value to '").appendText(value.toString()).appendText("'");
	}

	@Factory
	public static Matcher<CharSequence> sameAs(CharSequence value) {
		return new CharSequenceMatcher(value);
	}
}