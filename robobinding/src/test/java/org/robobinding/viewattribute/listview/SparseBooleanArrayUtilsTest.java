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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.util.SparseBooleanArray;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.xtremelabs.robolectric.RobolectricTestRunner;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@RunWith(RobolectricTestRunner.class)
public class SparseBooleanArrayUtilsTest {
    private SparseBooleanArray array;

    @Before
    public void setUp() {
	array = new SparseBooleanArray();
	array.put(1, true);
	array.put(2, false);
	array.put(5, false);
	array.put(3, true);
    }

    @Test
    public void whenToSet_thenGetExpectedResult() {
	Set<Integer> trueSet = SparseBooleanArrayUtils.toSet(array);
	assertThat(trueSet, equalTo((Set<Integer>) Sets.newHashSet(1, 3)));
    }

    @Test
    public void whenToMap_thenGetExpectedResult() {
	Map<Integer, Boolean> map = SparseBooleanArrayUtils.toMap(array);

	Map<Integer, Boolean> expectedMap = Maps.newHashMap();
	expectedMap.put(1, true);
	expectedMap.put(2, false);
	expectedMap.put(5, false);
	expectedMap.put(3, true);
	assertThat(map, equalTo(expectedMap));
    }
}
