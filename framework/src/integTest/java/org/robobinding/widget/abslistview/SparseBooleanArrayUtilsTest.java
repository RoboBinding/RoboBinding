package org.robobinding.widget.abslistview;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robobinding.widget.abslistview.SparseBooleanArrayUtils;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import android.util.SparseBooleanArray;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@Config(manifest = Config.NONE)
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
