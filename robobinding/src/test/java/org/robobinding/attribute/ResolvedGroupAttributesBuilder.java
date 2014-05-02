package org.robobinding.attribute;

import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class ResolvedGroupAttributesBuilder {
    private List<AbstractAttribute> childAttributeResolutions;

    public static ResolvedGroupAttributesBuilder aGroupAttributes() {
	return new ResolvedGroupAttributesBuilder();
    }

    private ResolvedGroupAttributesBuilder() {
	childAttributeResolutions = Lists.newArrayList();
    }

    public ResolvedGroupAttributesBuilder withChildAttributeResolution(AbstractAttribute childAttribute) {
	childAttributeResolutions.add(childAttribute);
	return this;
    }

    public ResolvedGroupAttributes build() {
	return new ResolvedGroupAttributes(createPendingGroupAttributes(), createResolverMappings());
    }

    private ChildAttributeResolverMappings createResolverMappings() {
	ChildAttributeResolverMappings childAttributeResolverMappings = mock(ChildAttributeResolverMappings.class);
	ChildAttributeResolver childAttributeResolver = mock(ChildAttributeResolver.class);

	for (AbstractAttribute attribute : childAttributeResolutions) {
	    when(childAttributeResolver.resolveChildAttribute(eq(attribute.getName()), anyString())).thenReturn(attribute);
	    when(childAttributeResolverMappings.resolverFor(attribute.getName())).thenReturn(childAttributeResolver);
	}

	return childAttributeResolverMappings;
    }

    private PendingGroupAttributes createPendingGroupAttributes() {
	Map<String, String> presentAttributes = Maps.newHashMap();
	for (AbstractAttribute childAttribute : childAttributeResolutions) {
	    presentAttributes.put(childAttribute.getName(), null);
	}

	return new PendingGroupAttributes(presentAttributes);
    }
}