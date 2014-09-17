package org.robobinding;

import java.util.Map;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public interface PendingAttributesForView {
	Object getView();

	void resolveAttributeIfExists(String attribute, AttributeResolver attributeResolver);

	void resolveAttributeGroupIfExists(String[] attributeGroup, AttributeGroupResolver attributeGroupResolver);

	boolean isEmpty();

	ViewResolutionErrors getResolutionErrors();

	public interface AttributeResolver {
		void resolve(Object view, String attribute, String attributeValue);
	}

	public interface AttributeGroupResolver {
		void resolve(Object view, String[] attributeGroup, Map<String, String> presentAttributeMappings);
	}
}
