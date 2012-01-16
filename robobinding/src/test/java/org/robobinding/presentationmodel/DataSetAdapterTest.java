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
import static org.hamcrest.CoreMatchers.is;
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
import org.robobinding.viewattribute.RandomValues;

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
	private DataSetAdapterForTest dataSetAdapter;
	private MockPresentationModel mockPresentationModel;
	private DataSetProperty<Object> dataSetProperty;
	private Context context = new Activity();
	
	@Before
	public void setUp()
	{
		mockPresentationModel = new MockPresentationModel();
		dataSetProperty = ValueModelUtils.createDataSetValueModel(mockPresentationModel, "list");
	}

	@Test
	public void whenUpdatingTheValueModel_thenNotifyDataSetChanged()
	{
		dataSetAdapterWithViewsPreInitialized();
		dataSetAdapter.observeChangesOnTheValueModel();
		
		mockPresentationModel.update();
		
		assertTrue(dataSetAdapter.hasNotifiedDataSetChanged);
	}
	
	@Test
	public void givenItemLayoutId_whenGeneratingItemView_thenInflateTheCorrectView()
	{
		dataSetAdapterWithViewsPreInitialized();
		dataSetAdapter.setItemLayoutId(R.layout.sample_item_layout);
		
		View view = dataSetAdapter.getView(0, null, null);
		
		assertNotNull(view);
		assertThat(view.getId(), equalTo(R.id.sample_item_layout));
	}
	
	@Test
	public void givenDropdownLayoutId_whenGeneratingDropdownView_thenInflateTheCorrectView()
	{
		dataSetAdapterWithViewsPreInitialized();
		dataSetAdapter.setDropdownLayoutId(R.layout.sample_dropdown_layout);
		
		View view = dataSetAdapter.getDropDownView(0, null, null);
		
		assertNotNull(view);
		assertThat(view.getId(), equalTo(R.id.sample_dropdown_layout));
	}
	
	@Test
	public void givenPreInitializeViewsIsFalse_whenValueModelHasNotBeenUpdated_thenShouldNotHaveAccessedPresentationModel()
	{
		dataSetAdapterNotPreInitializingViews();
		
		assertThat(mockPresentationModel.listAccessCount, is(0));
		assertThat(dataSetAdapter.getCount(), is(0));
	}

	@Test
	public void givenPreInitializeViewsIsFalse_whenUpdatingValueModel_thenShouldHaveAccessedPresentationModel()
	{
		dataSetAdapterNotPreInitializingViews();
		dataSetAdapter.observeChangesOnTheValueModel();
		
		mockPresentationModel.update();
		
		assertThat(mockPresentationModel.listAccessCount, is(1));
		assertThat(dataSetAdapter.getCount(), is(mockPresentationModel.list.size()));
	}
	
	private void dataSetAdapterWithViewsPreInitialized()
	{
		dataSetAdapter = new DataSetAdapterForTest(context, true);
		dataSetAdapter.setValueModel(dataSetProperty);
	}
	
	private DataSetAdapterForTest dataSetAdapterNotPreInitializingViews()
	{
		dataSetAdapter = new DataSetAdapterForTest(context, false);
		dataSetAdapter.setValueModel(dataSetProperty);
		return dataSetAdapter;
	}
	
	static class DataSetAdapterForTest extends DataSetAdapter<Object>
	{
		private boolean hasNotifiedDataSetChanged;

		public DataSetAdapterForTest(Context context, boolean preInitializeViews)
		{
			super(context, preInitializeViews);
		}

		@Override
		public void notifyDataSetChanged()
		{
			this.hasNotifiedDataSetChanged = true;
		}
	}

	public static class MockPresentationModel extends AbstractPresentationModel
	{
		List<Object> list;
		int listAccessCount = 0; 
		
		public MockPresentationModel()
		{
			list = Lists.newArrayList();
			
			for (int i = 0; i < RandomValues.anyInteger(); i++)
				list.add(new Object());
		}
		
		void update()
		{
			presentationModelChangeSupport.fireChangeAll();
		}
		
		@ItemPresentationModel(value = ItemPresentationModelDummy.class)
		public List<Object> getList()
		{
			listAccessCount++;
			return list;
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
