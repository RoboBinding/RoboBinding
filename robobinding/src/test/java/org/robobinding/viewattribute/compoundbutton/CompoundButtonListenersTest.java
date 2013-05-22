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
package org.robobinding.viewattribute.compoundbutton;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;

import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.xtremelabs.robolectric.RobolectricTestRunner;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@RunWith(RobolectricTestRunner.class)
public class CompoundButtonListenersTest {
    @Test
    public void shouldSupportMultipleOnCheckedChangeListeners() {
	CheckBox view = new CheckBox(null);
	CompoundButtonListeners viewListeners = new CompoundButtonListeners(view);

	MockOnCheckedChangeListener listener1 = new MockOnCheckedChangeListener();
	MockOnCheckedChangeListener listener2 = new MockOnCheckedChangeListener();

	viewListeners.addOnCheckedChangeListener(listener1);
	viewListeners.addOnCheckedChangeListener(listener2);

	view.setChecked(!view.isChecked());

	assertTrue(listener1.checkedChangeEventFired);
	assertTrue(listener2.checkedChangeEventFired);
    }

    private static class MockOnCheckedChangeListener implements OnCheckedChangeListener {
	private boolean checkedChangeEventFired;

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
	    checkedChangeEventFired = true;
	}
    }
}
