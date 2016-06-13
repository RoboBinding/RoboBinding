package org.robobinding;

import java.util.Collections;
import java.util.List;

import org.robobinding.util.Lists;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
@SuppressWarnings("serial")
public class GroupedAttributeResolutionException extends RuntimeException {
	private final List<AttributeResolutionException> resolutionExceptions = Lists.newArrayList();

	public void add(AttributeResolutionException e) {
		resolutionExceptions.add(e);
	}

	public void assertNoErrors() {
		if (!resolutionExceptions.isEmpty()) {
			throw this;
		}
	}

	public List<AttributeResolutionException> getAttributeResolutionExceptions() {
		return Collections.unmodifiableList(resolutionExceptions);
	}

}
