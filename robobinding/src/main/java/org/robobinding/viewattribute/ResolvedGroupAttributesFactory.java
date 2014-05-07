package org.robobinding.viewattribute;

import org.robobinding.attribute.ChildAttributeResolverMappings;
import org.robobinding.attribute.PendingGroupAttributes;
import org.robobinding.attribute.ResolvedGroupAttributes;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class ResolvedGroupAttributesFactory {

    public ResolvedGroupAttributes create(PendingGroupAttributes pendingGroupAttributes, ChildAttributesResolver childAttributesResolver) {
	pendingGroupAttributes.assertAttributesArePresent(childAttributesResolver.getCompulsoryAttributes());
	ChildAttributeResolverMappings resolverMappings = createResolverMappings(childAttributesResolver);
	ResolvedGroupAttributes resolvedGroupAttributes = new ResolvedGroupAttributes(pendingGroupAttributes, resolverMappings);

	childAttributesResolver.validateResolvedChildAttributes(resolvedGroupAttributes);

	return resolvedGroupAttributes;
    }

    private ChildAttributeResolverMappings createResolverMappings(ChildAttributesResolver childViewAttributesResolver) {
	ChildAttributeResolverMappings resolverMappings = new ChildAttributeResolverMappings();
	childViewAttributesResolver.mapChildAttributeResolvers(resolverMappings);
	return resolverMappings;
    }

}
