package org.robobinding.codegen.typemirror;

import java.util.Comparator;

import org.robobinding.codegen.PropertyInfo;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class PropertyInfoComparator implements Comparator<PropertyInfo> {
	@Override
	public int compare(PropertyInfo o1, PropertyInfo o2) {
		return o1.name().compareTo(o2.name());
	}
}
