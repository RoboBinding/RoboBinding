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
package org.robobinding.presentationmodel;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robobinding.ItemPresentationModel;
import org.robobinding.R;
import org.robobinding.internal.com_google_common.collect.Lists;
import org.robobinding.property.DataSetProperty;
import org.robobinding.property.ValueModelUtils;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.xtremelabs.robolectric.RobolectricTestRunner;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
@RunWith(RobolectricTestRunner.class)
public class DataSetAdapterTest
{
	private Context context = new Activity();
	private DataSetAdapterSpy dataSetAdapterSpy;
	private MockPresentationModel mockPresentationModel;
	
	@Before
	public void setUp()
	{
		dataSetAdapterSpy = new DataSetAdapterSpy(context);
		mockPresentationModel = new MockPresentationModel();
		DataSetProperty<Object> dataSetProperty = ValueModelUtils.createDataSetValueModel(mockPresentationModel, "list");
		dataSetAdapterSpy.setValueModel(dataSetProperty);
	}
	
	@Test
	public void whenUpdatingTheValueModel_thenNotifyDataSetChanged()
	{
		dataSetAdapterSpy.observeChangesOnTheValueModel();
		
		mockPresentationModel.update();
		
		assertTrue(dataSetAdapterSpy.hasNotifiedDataSetChanged);
	}
	
	@Test
	public void givenItemLayoutId_whenGeneratingItemView_thenInflateTheCorrectView()
	{
		dataSetAdapterSpy.setItemLayoutId(R.layout.sample_item_layout);
		
		View view = dataSetAdapterSpy.getView(0, null, null);
		
		assertNotNull(view);
		assertThat(view.getId(), equalTo(R.id.sample_item_layout));
	}
	
	@Test
	public void givenDropdownLayoutId_whenGeneratingDropdownView_thenInflateTheCorrectView()
	{
		dataSetAdapterSpy.setDropdownLayoutId(R.layout.sample_dropdown_layout);
		
		View view = dataSetAdapterSpy.getDropDownView(0, null, null);
		
		assertNotNull(view);
		assertThat(view.getId(), equalTo(R.id.sample_dropdown_layout));
	}
	
	static class DataSetAdapterSpy extends DataSetAdapter<Object>
	{
		private boolean hasNotifiedDataSetChanged;

		public DataSetAdapterSpy(Context context)
		{
			super(context);
		}

		@Override
		public void notifyDataSetChanged()
		{
			this.hasNotifiedDataSetChanged = true;
		}
	}

	public static class MockPresentationModel extends AbstractPresentationModel
	{
		void update()
		{
			presentationModelChangeSupport.fireChangeAll();
		}
		
		@ItemPresentationModel(value = ItemPresentationModelDummy.class)
		public List<Object> getList()
		{
			return Lists.newArrayList(new Object(), new Object());
		}
	}
	
	public static class ItemPresentationModelDummy implements org.robobinding.itempresentationmodel.ItemPresentationModel<Object>
	{
		public ItemPresentationModelDummy(){}
		
		@Override
		public void updateData(int index, Object bean)
		{
		}
	}
}
