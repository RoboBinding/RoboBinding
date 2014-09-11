package org.robobinding.widget.listview;

import java.util.Map;
import java.util.Set;

import android.util.SparseBooleanArray;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class SparseBooleanArrayUtils {
    public static Set<Integer> toSet(SparseBooleanArray array) {
	Set<Integer> trueSet = Sets.newHashSet();
	for (int i = 0; i < array.size(); i++) {
	    if (array.valueAt(i)) {
		int position = array.keyAt(i);
		trueSet.add(position);
	    }
	}
	return trueSet;
    }

    public static Map<Integer, Boolean> toMap(SparseBooleanArray array) {
	Map<Integer, Boolean> map = Maps.newHashMap();
	for (int i = 0; i < array.size(); i++) {
	    map.put(array.keyAt(i), array.valueAt(i));
	}
	return map;
    }

    private SparseBooleanArrayUtils() {
    }
}
