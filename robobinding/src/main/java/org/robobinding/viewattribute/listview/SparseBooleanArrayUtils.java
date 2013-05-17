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
package org.robobinding.viewattribute.listview;

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
