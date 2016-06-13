package org.robobinding.viewattribute.grouped;

import static org.robobinding.util.CollectionUtils.isNotEmpty;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.robobinding.util.Lists;
import org.robobinding.viewattribute.AttributeBindingException;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@SuppressWarnings("serial")
public class AttributeGroupBindingException extends RuntimeException {
	private List<AttributeBindingException> childAttributeErrors;

	public AttributeGroupBindingException() {
		childAttributeErrors = Lists.newArrayList();
	}

	void addChildAttributeError(String attributeName, Throwable cause) {
		childAttributeErrors.add(new AttributeBindingException(attributeName, cause));
	}

	void assertNoErrors() {
		if (hasErrors()) {
			throw this;
		}
	}

	private boolean hasErrors() {
		return isNotEmpty(childAttributeErrors);
	}

	public Collection<AttributeBindingException> getChildAttributeErrors() {
		return Collections.unmodifiableCollection(childAttributeErrors);
	}
}
