package org.robobinding.codegen.typemirror;

import java.util.Comparator;

import org.robobinding.codegen.DataSetPropertyInfo;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class DataSetPropertyInfoComparator implements Comparator<DataSetPropertyInfo> {
	@Override
	public int compare(DataSetPropertyInfo o1, DataSetPropertyInfo o2) {
		return o1.name().compareTo(o2.name());
	}
}
