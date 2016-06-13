package org.robobinding.attribute;

import java.util.Map;

import org.robobinding.util.Maps;
import org.robobinding.util.Preconditions;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class ChildAttributeResolverMappings {
	private Map<String, ChildAttributeResolver> childAttributeResolvers;

	public ChildAttributeResolverMappings() {
		childAttributeResolvers = Maps.newHashMap();
	}

	public void map(ChildAttributeResolver resolver, String attribute) {
		Preconditions.checkNotBlank(attribute, "Attribute cannot be empty");
		Preconditions.checkNotNull(resolver, "Resolver cannot be null");

		childAttributeResolvers.put(attribute, resolver);
	}

	public ChildAttributeResolver resolverFor(String attribute) {
		if (childAttributeResolvers.containsKey(attribute)) {
			return childAttributeResolvers.get(attribute);
		} else {
			throw new RuntimeException("A ChildAttributeResolver for '" + attribute + "' is not specified");
		}
	}
}
