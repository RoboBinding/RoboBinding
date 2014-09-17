package org.robobinding;

import java.util.Collection;

import org.robobinding.attribute.MissingRequiredAttributesException;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public interface ViewResolutionErrors {
	Object getView();

	int numErrors();

	void assertNoErrors();

	boolean hasErrors();

	Collection<AttributeResolutionException> getAttributeErrors();

	Collection<MissingRequiredAttributesException> getMissingRequiredAttributeErrors();

	Collection<Exception> getErrors();
}
