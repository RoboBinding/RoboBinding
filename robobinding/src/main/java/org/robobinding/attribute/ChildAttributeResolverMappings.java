package org.robobinding.attribute;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.robobinding.util.Preconditions.checkNotBlank;

import java.util.Map;

import com.google.common.collect.Maps;

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
	checkNotBlank(attribute, "Attribute cannot be empty");
	checkNotNull(resolver, "Resolver cannot be null");

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
