package org.robobinding;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@SuppressWarnings("serial")
public class AttributeResolutionException extends RuntimeException {
	private String attributeName;

	public AttributeResolutionException(String attributeName) {
		this.attributeName = attributeName;
	}

	public AttributeResolutionException(String attributeName, String message) {
		super(message);
		this.attributeName = attributeName;
	}

	public AttributeResolutionException(String attributeName, String message, Throwable cause) {
		super(message, cause);
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
