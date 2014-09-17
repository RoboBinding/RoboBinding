package org.robobinding.viewattribute.grouped;

import org.robobinding.attribute.AbstractAttribute;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class ChildViewAttributeInitializer {
	@SuppressWarnings("unchecked")
	public void initializeChildViewAttribute(ChildViewAttribute childAttribute, AbstractAttribute attribute) {
		if (childAttribute instanceof ChildViewAttributeWithAttribute<?>) {
			((ChildViewAttributeWithAttribute<AbstractAttribute>) childAttribute).setAttribute(attribute);
		}
	}

}