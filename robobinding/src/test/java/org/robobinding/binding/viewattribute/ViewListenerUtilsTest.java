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
package org.robobinding.binding.viewattribute;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ListView;

import com.xtremelabs.robolectric.RobolectricTestRunner;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
@RunWith(RobolectricTestRunner.class)
public class ViewListenerUtilsTest
{
	@Test
	public void shouldSupportMultipleOnItemSelectedListenersInAdapterViews()
	{
		ListView adapterView = new ListView(null);
		adapterView.setAdapter(new MockArrayAdapter());
		MockOnItemSelectedListener listener1 = new MockOnItemSelectedListener();
		MockOnItemSelectedListener listener2 = new MockOnItemSelectedListener();
		
		ViewListenerUtils.addOnItemSelectedListener(adapterView, listener1);
		ViewListenerUtils.addOnItemSelectedListener(adapterView, listener2);
		
		adapterView.setSelection(5);
		
		assertTrue(listener1.itemSelectionEventFired);
		assertTrue(listener2.itemSelectionEventFired);
	}
	
	private static class MockOnItemSelectedListener implements OnItemSelectedListener 
	{
		private boolean itemSelectionEventFired;

		@Override
		public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
		{
			itemSelectionEventFired = true;
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent)
		{
		}
	}
}
