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
package org.robobinding.viewattribute.view;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import com.xtremelabs.robolectric.shadows.ShadowView;

import android.view.View;
import android.view.View.OnFocusChangeListener;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
@RunWith(RobolectricTestRunner.class)
public class ViewListenersTest {
    @Test
    public void shouldSupportMultipleOnFocusChangeListeners() {
	View view = new View(null);
	ViewListeners viewListeners = new ViewListeners(view);

	MockOnFocusChangeListener listener1 = new MockOnFocusChangeListener();
	MockOnFocusChangeListener listener2 = new MockOnFocusChangeListener();

	viewListeners.addOnFocusChangeListener(listener1);
	viewListeners.addOnFocusChangeListener(listener2);

	ShadowView shadowView = (ShadowView) Robolectric.shadowOf_(view);
	shadowView.setViewFocus(!view.isFocused());

	assertTrue(listener1.focusChangeEventFired);
	assertTrue(listener2.focusChangeEventFired);
    }

    private static class MockOnFocusChangeListener implements OnFocusChangeListener {
	private boolean focusChangeEventFired;

	@Override
	public void onFocusChange(View v, boolean hasFocus) {
	    focusChangeEventFired = true;
	}
    }
}
