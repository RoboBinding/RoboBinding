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
package robobinding.binding.viewattribute;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;

import robobinding.beans.PresentationModelAdapterImpl;
import robobinding.binding.viewattribute.OnItemClickAttribute;
import robobinding.presentationmodel.ItemClickEvent;
import android.R;
import android.app.Activity;
import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.common.collect.Lists;
import com.xtremelabs.robolectric.Robolectric;
import com.xtremelabs.robolectric.RobolectricTestRunner;
import com.xtremelabs.robolectric.shadows.ShadowListView;

/**
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 *
 */
@RunWith(RobolectricTestRunner.class)
public class OnItemClickAttributeTest
{
	@Test
	public void whenClickingOnItemInTheList_ShouldInvokeCommand()
	{
		ListView adapterView = new ListView(null);
		String commandName = "someCommand";
		MockPresentationModel mockPresentationModel = new MockPresentationModel();
		
		ArrayAdapter<String> mockArrayAdapter = new MockArrayAdapter(new Activity(), R.layout.simple_list_item_1, Lists.newArrayList("0", "1", "2", "3", "4", "5"));
		adapterView.setAdapter(mockArrayAdapter);
		
		OnItemClickAttribute onItemClickAttribute = new OnItemClickAttribute(adapterView);
		onItemClickAttribute.bind(new PresentationModelAdapterImpl(mockPresentationModel), commandName);
		
		ShadowListView shadowListView = Robolectric.shadowOf(adapterView);
		shadowListView.performItemClick(5);
		
		assertTrue(mockPresentationModel.commandInvoked);
	}
	
	private static class MockArrayAdapter extends ArrayAdapter<String>
	{
		public MockArrayAdapter(Context context, int textViewResourceId, List<String> data)
		{
			super(context, textViewResourceId, data);
		}
	}
	
	private static class MockPresentationModel
	{
		private boolean commandInvoked;
		private ItemClickEvent itemClickEvent;
		
		@SuppressWarnings("unused")
		public void someCommand(ItemClickEvent itemClickEvent)
		{
			commandInvoked = true;
			this.itemClickEvent = itemClickEvent;
		}
	}
}
