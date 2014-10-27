package org.robobinding;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@SuppressWarnings("serial")
public class UnrecognizedAttributeException extends AttributeResolutionException {
	public UnrecognizedAttributeException(String attributeName) {
		super(attributeName);
	}

	@Override
	public String getMessage() {
		return "Unrecognized attribute '" + getAttributeName() + "'";
	}
}
