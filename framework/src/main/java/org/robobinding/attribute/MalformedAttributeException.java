package org.robobinding.attribute;

import org.robobinding.AttributeResolutionException;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
@SuppressWarnings("serial")
public class MalformedAttributeException extends AttributeResolutionException {
	public MalformedAttributeException(String attributeName, String message) {
		super(attributeName, message);
	}
}
