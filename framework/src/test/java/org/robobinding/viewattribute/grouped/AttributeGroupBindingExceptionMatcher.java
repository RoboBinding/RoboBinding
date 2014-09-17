package org.robobinding.viewattribute.grouped;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.robobinding.viewattribute.AttributeBindingException;

/**
 * @author Cheng Wei
 * 
 */
class AttributeGroupBindingExceptionMatcher extends TypeSafeMatcher<AttributeGroupBindingException> {

	private final String attributeName;

	public AttributeGroupBindingExceptionMatcher(String attributeName) {
		this.attributeName = attributeName;
	}

	@Override
	protected boolean matchesSafely(final AttributeGroupBindingException exception) {
		for (AttributeBindingException e : exception.getChildAttributeErrors()) {
			if (e.getAttributeName().equals(attributeName))
				return true;
		}

		return false;
	}

	@Override
	public void describeTo(Description description) {
		description.appendText("Error for attribute '").appendValue(attributeName).appendText("' was not thrown.");
	}
}