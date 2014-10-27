package org.robobinding.attribute;

import java.util.Collection;
import java.util.Collections;

import com.google.common.base.Joiner;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
@SuppressWarnings("serial")
public class MissingRequiredAttributesException extends RuntimeException {
	private final Collection<String> missingAttributes;

	public MissingRequiredAttributesException(Collection<String> missingAttributes) {
		this.missingAttributes = missingAttributes;
	}

	public Collection<String> getMissingAttributes() {
		return Collections.unmodifiableCollection(missingAttributes);
	}

	@Override
	public String getMessage() {
		return "Missing attributes: " + Joiner.on(", ").join(missingAttributes);
	}

	@Override
	public String toString() {
		return getMessage();
	}
}
