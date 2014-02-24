/**
 * Copyright 2013 Cheng Wei, Robert Taylor
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions
 * and limitations under the License.
 */
package org.robobinding.viewattribute;

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
public class ResolvedGroupAttributesFactoryTest {
    private static final String ATTRIBUTE_NAME = "attributeName";
    @Test
    public void givenAllCorrectParameters_thenReturnResolvedGroupAttributes() {
	PendingGroupAttributes pendingGroupAttributes = newPendingGroupAttributes();

	ResolvedGroupAttributesFactory factory = new ResolvedGroupAttributesFactory();
	ResolvedGroupAttributes resolvedGroupAttributes = factory.create(pendingGroupAttributes, new ChildAttributesResolverForTest());

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
