package org.robobinding.attribute;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public interface ChildAttributeResolver {
	AbstractAttribute resolveChildAttribute(String attribute, String attributeValue);
}
