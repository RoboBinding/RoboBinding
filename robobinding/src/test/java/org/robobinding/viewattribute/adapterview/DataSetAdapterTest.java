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
package org.robobinding.viewattribute.adapterview;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyCollection;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robobinding.BindingContext;
import org.robobinding.ItemBinder;
import org.robobinding.R;
import org.robobinding.presentationmodel.AbstractPresentationModel;
import org.robobinding.presentationmodel.ItemPresentationModel;
import org.robobinding.property.DataSetValueModel;
import org.robobinding.property.ValueModelUtils;
import org.robobinding.viewattribute.RandomValues;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import com.google.common.collect.Lists;
import com.xtremelabs.robolectric.RobolectricTestRunner;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
@RunWith(RobolectricTestRunner.class)
@SuppressWarnings(value = { "rawtypes", "unchecked" })
public class DataSetAdapterTest {
    @Mock
    BindingContext bindingContext;
    @Mock
    ItemBinder itemBinder;
    @Mock
    ItemBinder dropDownBinder;
    private View view;
    private DataSetAdapter dataSetAdapter;
    private MockPresentationModel presentationModel;
    private DataSetValueModel<Object> dataSetProperty;
    private Context context = new Activity();

    @Before
    public void setUp() {
	initMocks(this);
	presentationModel = new MockPresentationModel();
	dataSetProperty = ValueModelUtils.createDataSetValueModel(presentationModel, "list");
	when(bindingContext.createItemBinder()).thenReturn(itemBinder, dropDownBinder);
	dataSetAdapter = spy(new DataSetAdapter(bindingContext));
	dataSetAdapter.setValueModel(dataSetProperty);
	view = new View(context);
    }

    @Test
    public void whenUpdatingTheValueModel_thenNotifyDataSetChanged() {
	dataSetAdapter.observeChangesOnTheValueModel();

	presentationModel.update();

	verify(dataSetAdapter).notifyDataSetChanged();
    }

    @Test
    public void givenItemLayoutId_whenGeneratingItemView_thenInflateTheCorrectView() {
	when(itemBinder.inflateAndBind(eq(R.layout.sample_item_layout), any(ItemPresentationModelDummy.class), anyCollection())).thenReturn(view);
	dataSetAdapter.setItemLayoutId(R.layout.sample_item_layout);

	View result = dataSetAdapter.getView(0, null, null);

	assertThat(result.getTag(), instanceOf(ItemPresentationModelDummy.class));
    }

    @Test
    public void givenDropdownLayoutId_whenGeneratingDropdownView_thenInflateTheCorrectView() {
	when(dropDownBinder.inflateAndBind(eq(R.layout.sample_dropdown_layout), any(ItemPresentationModelDummy.class), anyCollection())).thenReturn(view);
	dataSetAdapter.setDropDownLayoutId(R.layout.sample_dropdown_layout);

	View result = dataSetAdapter.getDropDownView(0, null, null);

	assertThat(result.getTag(), instanceOf(ItemPresentationModelDummy.class));
    }

    @Test
    public void givenPreInitializeViewsIsTrue_whenInitializing_thenDataSetAdapterCountShouldReflectPresentationModel() {
	dataSetAdapterWithViewsPreInitialized();

	assertThat(dataSetAdapter.getCount(), is(presentationModel.list.size()));
    }

    @Test
    public void givenPreInitializeViewsIsFalse_whenValueModelHasNotBeenUpdated_thenDataSetAdapterCountShouldBeZero() {
	dataSetAdapterNotPreInitializingViews();

	assertThat(dataSetAdapter.getCount(), is(0));
    }

    @Test
    public void givenPreInitializeViewsIsFalse_whenUpdatingValueModel_thenDataSetAdapterCountShouldReflectPresentationModel() {
	dataSetAdapterNotPreInitializingViews();

	dataSetAdapter.observeChangesOnTheValueModel();
	presentationModel.update();

	assertThat(dataSetAdapter.getCount(), is(presentationModel.list.size()));
    }

    @Test
    public void givenADataSetAdapterWithoutAValueModel_thenCountShouldBeZero() {
	DataSetAdapter dataSetAdapter = new DataSetAdapter(bindingContext);

	assertThat(dataSetAdapter.getCount(), is(0));
    }

    private void dataSetAdapterWithViewsPreInitialized() {
	when(bindingContext.shouldPreInitializeViews()).thenReturn(true);
	dataSetAdapter = spy(new DataSetAdapter(bindingContext));
	dataSetAdapter.setValueModel(dataSetProperty);
    }

    private void dataSetAdapterNotPreInitializingViews() {
	when(bindingContext.shouldPreInitializeViews()).thenReturn(false);
	dataSetAdapter = spy(new DataSetAdapter(bindingContext));
	dataSetAdapter.setValueModel(dataSetProperty);
    }

    public static class MockPresentationModel extends AbstractPresentationModel {
	List<Object> list;

	public MockPresentationModel() {
	    list = Lists.newArrayList();

	    for (int i = 0; i < RandomValues.anyIntegerGreaterThanZero(); i++) {
		list.add(new Object());
	    }
	}

	void update() {
	    presentationModelChangeSupport.fireChangeAll();
	}

	@ItemPresentationModel(value = ItemPresentationModelDummy.class)
	public List<Object> getList() {
	    return list;
	}
    }

    public static class ItemPresentationModelDummy implements org.robobinding.itempresentationmodel.ItemPresentationModel<Object> {
	public ItemPresentationModelDummy() {
	}

	@Override
	public void updateData(int index, Object bean) {
	}
    }
}