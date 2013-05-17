/**
 * Copyright 2012 Cheng Wei, Robert Taylor
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
package org.robobinding.attribute;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
public class PendingGroupAttributes {
    private final Map<String, String> presentAttributeMappings;

    public PendingGroupAttributes(Map<String, String> presentAttributeMappings) {
	this.presentAttributeMappings = Maps.newHashMap(presentAttributeMappings);
    }

    Iterable<Map.Entry<String, String>> presentAttributes() {
	return presentAttributeMappings.entrySet();
    }

    public void assertAttributesArePresent(String... attributeNames) {
	if (!hasAttributes(attributeNames))
	    throw new MissingRequiredAttributesException(findAbsentAttributes(attributeNames));
    }

    private boolean hasAttributes(String... attributes) {
	for (String attribute : attributes) {
	    if (!presentAttributeMappings.containsKey(attribute)) {
		return false;
	    }
	}
	return true;
    }

    private Collection<String> findAbsentAttributes(String... attributeNames) {
	List<String> absentAttributes = Lists.newArrayList();
	for (String attributeName : attributeNames) {
	    if (!presentAttributeMappings.containsKey(attributeName)) {
		absentAttributes.add(attributeName);
	    }
	}
	return absentAttributes;
    }

    @Override
    public boolean equals(Object other) {
	if (this == other)
	    return true;
	if (!(other instanceof PendingGroupAttributes))
	    return false;

	final PendingGroupAttributes that = (PendingGroupAttributes) other;
	return Objects.equal(presentAttributeMappings, that.presentAttributeMappings);
    }

    @Override
    public int hashCode() {
	return Objects.hashCode(presentAttributeMappings);
    }
}
