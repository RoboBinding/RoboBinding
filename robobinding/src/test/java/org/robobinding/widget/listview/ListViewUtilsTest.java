package org.robobinding.widget.listview;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.Collections;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robobinding.viewattribute.RandomValues;
import org.robobinding.widget.adapterview.MockArrayAdapter;

import android.app.Activity;
import android.widget.ListView;

import com.google.common.collect.Sets;
import com.xtremelabs.robolectric.RobolectricTestRunner;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@RunWith(RobolectricTestRunner.class)
public class ListViewUtilsTest {
    private ListView listView;

    @Before
    public void setUp() {
	listView = new ListView(new Activity());
	listView.setAdapter(new MockArrayAdapter());
	listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
    }

    @Test
    public void shouldSelectionsCleared() {
	setItemsChecked(anyCheckedItemPositions());

	ListViewUtils.clearSelections(listView);

	assertThat(SparseBooleanArrayUtils.toSet(listView.getCheckedItemPositions()), equalTo(Collections.<Integer> emptySet()));
    }

    private Set<Integer> anyCheckedItemPositions() {
	Set<Integer> result = Sets.newHashSet();
	for (int i = 0; i < listView.getCount(); i++) {
	    if (RandomValues.trueOrFalse()) {
		result.add(i);
	    }
	}
	return result;
    }

    private void setItemsChecked(Set<Integer> checkedItemPositions) {
	for (Integer checkedItemPosition : checkedItemPositions) {
	    listView.setItemChecked(checkedItemPosition, true);
	}
    }
}
