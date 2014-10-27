package org.robobinding.codegen.typemirror;

import java.util.Comparator;

import org.robobinding.codegen.PropertyDependencyInfo;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class PropertyDependencyInfoComparator implements Comparator<PropertyDependencyInfo> {
	@Override
	public int compare(PropertyDependencyInfo o1, PropertyDependencyInfo o2) {
		return o1.property().compareTo(o2.property());
	}
}
