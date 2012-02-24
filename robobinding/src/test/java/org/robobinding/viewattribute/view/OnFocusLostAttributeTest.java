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

import android.view.View;

import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import com.xtremelabs.robolectric.shadows.ShadowView;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@RunWith(RobolectricTestRunner.class)
public class OnFocusLostAttributeTest extends AbstractCommandViewAttributeWithViewListenersAwareTest<View, OnFocusChangeAttribute, MockViewListeners>
{
	@Test
	public void givenBoundAttribute_whenClearFocus_thenEventReceived()
	{
		bindAttribute();

		clearViewFocus();

		assertEventReceived();
	}

	@Test
	public void whenBinding_thenRegisterWithViewListeners()
	{
		bindAttribute();
		
		assertTrue(viewListeners.addOnFocusChangeListenerInvoked);
	}
	
	private void clearViewFocus()
	{
		ShadowView shadowView = Robolectric.shadowOf(view);
		shadowView.setViewFocus(false);
	}

	private void assertEventReceived()
	{
		assertEventReceived(AbstractViewEvent.class);
	}
}