package org.robobinding.viewattribute.grouped;

import org.robobinding.attribute.AbstractAttribute;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
public interface ChildViewAttributeWithAttribute<T extends AbstractAttribute> extends ChildViewAttribute {
	void setAttribute(T attribute);
}
