package org.robobinding.viewattribute.grouped;

import org.robobinding.attribute.ChildAttributeResolverMappings;
import org.robobinding.attribute.ResolvedGroupAttributes;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public interface ChildAttributesResolver {
	String[] getCompulsoryAttributes();

	void mapChildAttributeResolvers(ChildAttributeResolverMappings resolverMappings);

	void validateResolvedChildAttributes(ResolvedGroupAttributes groupAttributes);
}
