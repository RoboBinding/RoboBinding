package org.robobinding.viewattribute.grouped;

import static com.google.common.collect.Maps.newHashMap;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.Test;
import org.robobinding.attribute.ChildAttributeResolverMappings;
import org.robobinding.attribute.ChildAttributeResolvers;
import org.robobinding.attribute.PendingGroupAttributes;
import org.robobinding.attribute.ResolvedGroupAttributes;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class GroupAttributesResolverTest {
	private static final String ATTRIBUTE_NAME = "attributeName";

	@Test
	public void givenAllCorrectParameters_thenReturnResolvedGroupAttributes() {
		PendingGroupAttributes pendingGroupAttributes = newPendingGroupAttributes();

		GroupAttributesResolver factory = new GroupAttributesResolver();
		ResolvedGroupAttributes resolvedGroupAttributes = factory.resolve(pendingGroupAttributes, new ChildAttributesResolverForTest());

		assertTrue(resolvedGroupAttributes.hasAttribute(ATTRIBUTE_NAME));
	}

	private PendingGroupAttributes newPendingGroupAttributes() {
		Map<String, String> attributeMappings = newHashMap();
		attributeMappings.put(ATTRIBUTE_NAME, "${propertyName}");
		return new PendingGroupAttributes(attributeMappings);
	}

	private class ChildAttributesResolverForTest implements ChildAttributesResolver {
		@Override
		public String[] getCompulsoryAttributes() {
			return new String[0];
		}

		@Override
		public void mapChildAttributeResolvers(ChildAttributeResolverMappings resolverMappings) {
			resolverMappings.map(ChildAttributeResolvers.valueModelAttributeResolver(), ATTRIBUTE_NAME);

		}

		@Override
		public void validateResolvedChildAttributes(ResolvedGroupAttributes groupAttributes) {
		}
	}
}
