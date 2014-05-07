package org.robobinding;

import java.util.Map;

import android.view.View;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public interface PendingAttributesForView {
    View getView();

    void resolveAttributeIfExists(String attribute, AttributeResolver attributeResolver);

    void resolveAttributeGroupIfExists(String[] attributeGroup, AttributeGroupResolver attributeGroupResolver);

    boolean isEmpty();

    ViewResolutionErrors getResolutionErrors();

    public interface AttributeResolver {
	void resolve(View view, String attribute, String attributeValue);
    }

    public interface AttributeGroupResolver {
	void resolve(View view, String[] attributeGroup, Map<String, String> presentAttributeMappings);
    }
}
