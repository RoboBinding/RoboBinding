package org.robobinding.attribute;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.robobinding.util.Lists;
import org.robobinding.util.Maps;
import org.robobinding.util.Objects;

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

	public Iterable<Map.Entry<String, String>> presentAttributes() {
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
