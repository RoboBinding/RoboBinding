/**
 * Copyright 2011 Cheng Wei, Robert Taylor
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
import org.robobinding.viewattribute.view.AbstractCommandViewAttributeWithViewListenersAwareTest;

import android.widget.CheckBox;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class OnCheckedChangeAttributeTest extends
	AbstractCommandViewAttributeWithViewListenersAwareTest<CheckBox, OnCheckedChangeAttribute, MockCompoundButtonListeners> {
    @Test
    public void givenBoundAttribute_whenChangeChecked_thenEventReceived() {
	bindAttribute();

	changeCheckedState();

	assertEventReceived();
    }

    @Test
    public void whenBinding_thenRegisterWithViewListeners() {
	bindAttribute();

	assertTrue(viewListeners.addOnCheckedChangeListenerInvoked);
    }

    private void changeCheckedState() {
	view.setChecked(!view.isChecked());
    }

    private void assertEventReceived() {
	assertEventReceived(CheckedChangeEvent.class);
    }
}
