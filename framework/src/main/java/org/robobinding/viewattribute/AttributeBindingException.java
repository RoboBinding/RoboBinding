package org.robobinding.viewattribute;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@SuppressWarnings("serial")
public class AttributeBindingException extends RuntimeException {
	private String attributeName;

	public AttributeBindingException(String attributeName, Throwable cause) {
		super(cause.getMessage(), cause);
		this.attributeName = attributeName;
	}

	public String getAttributeName() {
		return attributeName;
	}

	@Override
	public String toString() {
		return attributeName + ": " + getMessage();
	}
}
