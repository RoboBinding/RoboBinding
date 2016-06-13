package org.robobinding.viewattribute.grouped;

import java.util.Map;

import org.robobinding.AttributeResolutionException;
import org.robobinding.GroupedAttributeResolutionException;
import org.robobinding.attribute.AbstractAttribute;
import org.robobinding.attribute.ChildAttributeResolver;
import org.robobinding.attribute.ChildAttributeResolverMappings;
import org.robobinding.attribute.PendingGroupAttributes;
import org.robobinding.attribute.ResolvedGroupAttributes;
import org.robobinding.util.Maps;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class GroupAttributesResolver {

	public ResolvedGroupAttributes resolve(PendingGroupAttributes pendingGroupAttributes, ChildAttributesResolver childAttributesResolver) {
		pendingGroupAttributes.assertAttributesArePresent(childAttributesResolver.getCompulsoryAttributes());
		ChildAttributeResolverMappings resolverMappings = createResolverMappings(childAttributesResolver);
		Map<String, AbstractAttribute> resolvedChildAttributes = resolveChildAttributes(pendingGroupAttributes, resolverMappings);

		ResolvedGroupAttributes resolvedGroupAttributes = new ResolvedGroupAttributes(resolvedChildAttributes);
		childAttributesResolver.validateResolvedChildAttributes(resolvedGroupAttributes);

		return resolvedGroupAttributes;
	}

	private ChildAttributeResolverMappings createResolverMappings(ChildAttributesResolver childViewAttributesResolver) {
		ChildAttributeResolverMappings resolverMappings = new ChildAttributeResolverMappings();
		childViewAttributesResolver.mapChildAttributeResolvers(resolverMappings);
		return resolverMappings;
	}

	private Map<String, AbstractAttribute> resolveChildAttributes(PendingGroupAttributes pendingGroupAttributes, ChildAttributeResolverMappings resolverMappings) {
		GroupedAttributeResolutionException groupResolutionErrors = new GroupedAttributeResolutionException();
		Map<String, AbstractAttribute> resolvedChildAttributes = Maps.newHashMap();
		for (Map.Entry<String, String> attributeEntry : pendingGroupAttributes.presentAttributes()) {
			String attribute = attributeEntry.getKey();
			ChildAttributeResolver resolver = resolverMappings.resolverFor(attribute);
			try {
				AbstractAttribute childAttribute = resolver.resolveChildAttribute(attribute, attributeEntry.getValue());
				resolvedChildAttributes.put(attribute, childAttribute);
			} catch (AttributeResolutionException e) {
				groupResolutionErrors.add(e);
			}
		}

		groupResolutionErrors.assertNoErrors();
		return resolvedChildAttributes;
	}

}
