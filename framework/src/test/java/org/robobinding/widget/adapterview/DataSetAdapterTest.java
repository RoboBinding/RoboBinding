package org.robobinding.widget.adapterview;

import static com.google.common.collect.Lists.newArrayList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.notNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.robobinding.BindableView;
import org.robobinding.itempresentationmodel.DataSetChangeListeners;
import org.robobinding.itempresentationmodel.RefreshableItemPresentationModel;
import org.robobinding.itempresentationmodel.ViewTypeSelectionContext;
import org.robobinding.presentationmodel.AbstractItemPresentationModelObject;
import org.robobinding.property.DataSetPropertyChangeListener;
import org.robobinding.property.DataSetValueModel;
import org.robobinding.util.RandomValues;
import org.robobinding.viewattribute.ViewTag;
import org.robobinding.viewattribute.ViewTags;

import android.view.View;
import android.view.ViewGroup;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
@RunWith(MockitoJUnitRunner.class)
public class DataSetAdapterTest {
	private MockDataSetValueModel valueModel;

	@Before
	public void setUp() {
		valueModel = new MockDataSetValueModel();
	}

	@Test
	public void whenGenerateItemView_thenInflateTheCorrectViewWithItemPresentationModelAttached() {
		View view = mock(View.class);
		ItemLayoutBinder itemLayoutBinder = aItemLayoutBinder().inflateAndReturnRootView(view);
		ViewTag<RefreshableItemPresentationModel> viewTag = mockViewTag();
		ViewTags<RefreshableItemPresentationModel> viewTags = aViewTags().tagForViewAndReturn(view, viewTag);

		DataSetAdapter dataSetAdapter = aDataSetAdapter()
				.withItemLayoutBinder(itemLayoutBinder)
				.withViewTags(viewTags)
				.build();
		View result = dataSetAdapter.getView(0, null, null);

		assertThat(result, sameInstance(view));
		verify(viewTag).set(notNull(RefreshableItemPresentationModel.class));
	}
	
	private ObjectDataSetAdapterBuilder aDataSetAdapter() {
		return new ObjectDataSetAdapterBuilder(valueModel);
	}

	@SuppressWarnings("unchecked")
	private ViewTag<RefreshableItemPresentationModel> mockViewTag() {
		return mock(ViewTag.class);
	}
	
	private static ItemLayoutBinderBuilder aItemLayoutBinder() {
		return new ItemLayoutBinderBuilder();
	}

	private static class ItemLayoutBinderBuilder {
		private ItemLayoutBinder itemLayoutBinder;
		
		public ItemLayoutBinderBuilder() {
			itemLayoutBinder = mock(ItemLayoutBinder.class);
		}
		
		public ItemLayoutBinder inflateAndReturnRootView(View rootView) {
			BindableView bindableView = mock(BindableView.class);
			when(bindableView.getRootView()).thenReturn(rootView);
			when(itemLayoutBinder.inflate(any(ViewGroup.class), anyInt())).thenReturn(bindableView);
			
			return itemLayoutBinder;
		}
	}
	
	private static ViewTagsBuilder aViewTags() {
		return new ViewTagsBuilder();
	}
	
	private static class ViewTagsBuilder {
		private ViewTags<RefreshableItemPresentationModel> viewTags;
		
		@SuppressWarnings("unchecked")
		public ViewTagsBuilder() {
			viewTags = mock(ViewTags.class);
		}

		public ViewTags<RefreshableItemPresentationModel> tagForViewAndReturn(View view, ViewTag<RefreshableItemPresentationModel> viewTag) {
			when(viewTags.tagFor(view)).thenReturn(viewTag);
			return viewTags;
		}
	}
	
	@Test
	public void whenGenerateDropdownView_thenInflateTheCorrectViewWithItemPresentationModelAttached() {
		View view = mock(View.class);
		ItemLayoutBinder dropdownLayoutBinder = aItemLayoutBinder().inflateAndReturnRootView(view);
		ViewTag<RefreshableItemPresentationModel> viewTag = mockViewTag();
		ViewTags<RefreshableItemPresentationModel> viewTags = aViewTags().tagForViewAndReturn(view, viewTag);

		DataSetAdapter dataSetAdapter = aDataSetAdapter()
				.withDropdownLayoutBinder(dropdownLayoutBinder)
				.withViewTags(viewTags)
				.build();

		View result = dataSetAdapter.getDropDownView(0, null, null);

		assertThat(result, sameInstance(view));
		verify(viewTag).set(notNull(RefreshableItemPresentationModel.class));
	}

	@Test
	public void dataSetAdapterCountShouldReflectValueModel() {
		DataSetAdapter dataSetAdapter = aDataSetAdapter()
				.build();

		assertThat(dataSetAdapter.getCount(), is(valueModel.size()));
	}

	public static class MockDataSetValueModel implements DataSetValueModel {
		private DataSetChangeListeners listeners;
		private List<Object> items;

		public MockDataSetValueModel() {
			listeners = new DataSetChangeListeners();
			initializeItems();
		}

		private void initializeItems() {
			items = newArrayList();
			for (int i = 0; i < RandomValues.anyIntegerGreaterThanZero(); i++) {
				items.add(new Object());
			}
		}

		@Override
		public void addPropertyChangeListener(DataSetPropertyChangeListener listener) {
			listeners.add(listener);
		}

		@Override
		public void removePropertyChangeListener(DataSetPropertyChangeListener listener) {
		}

		@Override
		public int size() {
			return items.size();
		}

		@Override
		public Object get(int position) {
			return null;
		}

		@Override
		public RefreshableItemPresentationModel newRefreshableItemPresentationModel(int itemViewType) {
			return mock(AbstractItemPresentationModelObject.class);
		}

		@Override
		public int selectViewType(ViewTypeSelectionContext<Object> context) {
			return 0;
		}

		@Override
		public boolean preInitializingViewsWithDefault(boolean defaultValue) {
			return false;
		}
	}
	
	private static class ObjectDataSetAdapterBuilder {
		private DataSetValueModel valueModel;
		private ItemLayoutBinder itemLayoutBinder;
		private ItemLayoutBinder dropdownLayoutBinder;
		private ItemLayoutSelector layoutSelector;
		private int dropdownLayoutId;
		private ViewTags<RefreshableItemPresentationModel> viewTags;
		private boolean preInitializeViews;
		
		public ObjectDataSetAdapterBuilder(DataSetValueModel valueModel) {
			this.valueModel = valueModel;
			itemLayoutBinder = null;
			dropdownLayoutBinder = null;
			layoutSelector = new SingleItemLayoutSelector(1);
			dropdownLayoutId = 1;
			viewTags = null;
			preInitializeViews = false;
		}

		public ObjectDataSetAdapterBuilder withViewTags(ViewTags<RefreshableItemPresentationModel> newValue) {
			this.viewTags = newValue;
			return this;
		}

		public ObjectDataSetAdapterBuilder withItemLayoutBinder(ItemLayoutBinder newValue) {
			this.itemLayoutBinder = newValue;
			return this;
		}
		
		public ObjectDataSetAdapterBuilder withDropdownLayoutBinder(ItemLayoutBinder newValue) {
			this.dropdownLayoutBinder = newValue;
			return this;
		}

		public DataSetAdapter build() {
			return new DataSetAdapter(valueModel, itemLayoutBinder, 
					dropdownLayoutBinder, layoutSelector, dropdownLayoutId,
					viewTags, preInitializeViews);
		}
	}
}
