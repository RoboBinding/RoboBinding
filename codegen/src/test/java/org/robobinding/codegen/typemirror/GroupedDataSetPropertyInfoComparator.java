package org.robobinding.codegen.typemirror;

import org.robobinding.codegen.GroupedDataSetPropertyInfo;

import java.util.Comparator;

/**
 * Created by jihunlee on 4/27/15.
 */
public class GroupedDataSetPropertyInfoComparator implements Comparator<GroupedDataSetPropertyInfo> {
    @Override
    public int compare(GroupedDataSetPropertyInfo o1, GroupedDataSetPropertyInfo o2) {
        return o1.name().compareTo(o2.name());
    }
}
