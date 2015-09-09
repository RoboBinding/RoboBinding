package org.robobinding.widget.adapterview;

import static com.google.common.collect.Lists.newArrayList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.notNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Matchers.anyInt;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.robobinding.BindableView;
import org.robobinding.itempresentationmodel.RefreshableItemPresentationModel;
import org.robobinding.presentationmodel.AbstractItemPresentationModelObject;
import org.robobinding.property.DataSetValueModel;
import org.robobinding.property.PropertyChangeListener;
import org.robobinding.property.PropertyChangeListeners;
import org.robobinding.util.RandomValues;
import org.robobinding.viewattribute.ViewTag;
import org.robobinding.viewattribute.ViewTags;

import android.database.DataSetObserver;
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
	public void whenUpdateTheValueModel_thenNotifyDataSetChanged() {
		DataSetAdapter<Object> dataSetAdapter = new DataSetAdapter<Object>(valueModel, null, null, null, true);
		dataSetAdapter.observeChangesOnTheValueModel();

		DataSetObserver dataSetObserver = mock(DataSetObserver.class);
		dataSetAdapter.registerDataSetObserver(dataSetObserver);

		valueModel.update();

		verify(dataSetObserver).onChanged();
	}

	@Test
	public void whenGenerateItemView_thenInflateTheCorrectViewWithItemPresentationModelAttached() {
		View view = mock(View.class);
		ItemLayoutBinder itemLayoutBinder = aItemLayoutBinder().inflateAndReturnRootView(view);
		ViewTag<RefreshableItemPresentationModel> viewTag = mockViewTag();
		ViewTags<RefreshableItemPresentationModel> viewTags = aViewTags().tagForViewAndReturn(view, viewTag);

		DataSetAdapter<Object> dataSetAdapter = new DataSetAdapter<Object>(valueModel, itemLayoutBinder, null, viewTags, true);
		View result = dataSetAdapter.getView(0, null, null);

		assertThat(result, sameInstance(view));
		verify(viewTag).set(notNull(RefreshableItemPresentationModel.class));
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
			when(itemLayoutBinder.inflate(any(ViewGroup.class))).thenReturn(bindableView);
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

		DataSetAdapter<Object> dataSetAdapter = new DataSetAdapter<Object>(valueModel, null, dropdownLayoutBinder, viewTags, true);

		View result = dataSetAdapter.getDropDownView(0, null, null);

		assertThat(result, sameInstance(view));
		verify(viewTag).set(notNull(RefreshableItemPresentationModel.class));
	}

	@Test
	public void givenPreInitializeViewsIsTrue_whenInitialize_thenDataSetAdapterCountShouldReflectValueModel() {
		DataSetAdapter<Object> dataSetAdapter = new DataSetAdapter<Object>(valueModel, null, null, null, true);

		assertThat(dataSetAdapter.getCount(), is(valueModel.size()));
	}

	@Test
	public void givenPreInitializeViewsIsFalse_whenValueModelHasNotBeenUpdated_thenDataSetAdapterCountShouldBeZero() {
		DataSetAdapter<Object> dataSetAdapter = new DataSetAdapter<Object>(valueModel, null, null, null, false);

		assertThat(dataSetAdapter.getCount(), is(0));
	}

	@Test
	public void givenPreInitializeViewsIsFalse_whenValueModelFireChange_thenDataSetAdapterCountShouldReflectValueModel() {
		DataSetAdapter<Object> dataSetAdapter = new DataSetAdapter<Object>(valueModel, null, null, null, false);
		dataSetAdapter.observeChangesOnTheValueModel();

		valueModel.update();

		assertThat(dataSetAdapter.getCount(), is(valueModel.size()));
	}

	@Test
	public void givenValueModelIsNull_thenCountShouldBeZero() {
		DataSetAdapter<Object> dataSetAdapter = new DataSetAdapter<Object>(null, null, null, null, false);

		assertThat(dataSetAdapter.getCount(), is(0));
	}

	public static class MockDataSetValueModel implements DataSetValueModel<Object> {
		private PropertyChangeListeners presentationModelPropertyChangeListeners;
		private List<Object> items;

		public MockDataSetValueModel() {
			presentationModelPropertyChangeListeners = new PropertyChangeListeners();
			initializeItems();
		}

		private void initializeItems() {
			items = newArrayList();
			for (int i = 0; i < RandomValues.anyIntegerGreaterThanZero(); i++) {
				items.add(new Object());
			}
		}

		@Override
		public void addPropertyChangeListener(PropertyChangeListener listener) {
			presentationModelPropertyChangeListeners.add(listener);
		}

		public void update() {
			presentationModelPropertyChangeListeners.firePropertyChange();
		}

		@Override
		public void removePropertyChangeListener(PropertyChangeListener listener) {
		}

		@Override
		public int size() {
			return items.size();
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public RefreshableItemPresentationModel newRefreshableItemPresentationModel(Object item) {
			return mock(AbstractItemPresentationModelObject.class);
		}

		@Override
		public int getItemViewTypeCount() {
			return 1;
		}

		@Override
		public int getItemViewType(int position, Object item) {
			return 0;
		}

		@Override
		public int getItemLayoutId(int position, Object item) {
			return 0;
		}
	}
}
