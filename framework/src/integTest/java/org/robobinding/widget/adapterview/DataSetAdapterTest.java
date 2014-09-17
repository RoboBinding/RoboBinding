package org.robobinding.widget.adapterview;

import static com.google.common.collect.Lists.newArrayList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robobinding.itempresentationmodel.ItemPresentationModel;
import org.robobinding.property.DataSetValueModel;
import org.robobinding.property.PropertyChangeListener;
import org.robobinding.property.PropertyChangeListeners;
import org.robobinding.util.RandomValues;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import android.app.Activity;
import android.database.DataSetObserver;
import android.view.View;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
@Config(manifest = Config.NONE)
@RunWith(RobolectricTestRunner.class)
public class DataSetAdapterTest {
	@Mock
	private ItemLayoutBinder itemLayoutBinder;
	@Mock
	private ItemLayoutBinder dropdownLayoutBinder;
	private MockDataSetValueModel valueModel;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		valueModel = new MockDataSetValueModel();
	}

	@Test
	public void whenUpdateTheValueModel_thenNotifyDataSetChanged() {
		DataSetAdapter<Object> dataSetAdapter = new DataSetAdapter<Object>(valueModel, null, null, true);
		dataSetAdapter.observeChangesOnTheValueModel();

		DataSetObserver dataSetObserver = mock(DataSetObserver.class);
		dataSetAdapter.registerDataSetObserver(dataSetObserver);

		valueModel.update();

		verify(dataSetObserver).onChanged();
	}

	@Test
	public void whenGenerateItemView_thenInflateTheCorrectViewWithItemPresentationModelAttached() {
		View view = new View(new Activity());

		when(itemLayoutBinder.inflateAndBindTo(any(ItemPresentationModel.class))).thenReturn(view);

		DataSetAdapter<Object> dataSetAdapter = new DataSetAdapter<Object>(valueModel, itemLayoutBinder, null, true);

		View result = dataSetAdapter.getView(0, null, null);

		assertThat(result, sameInstance(view));
		assertThat(result.getTag(), instanceOf(ItemPresentationModel.class));
	}

	@Test
	public void whenGenerateDropdownView_thenInflateTheCorrectViewWithItemPresentationModelAttached() {
		View view = new View(new Activity());

		when(dropdownLayoutBinder.inflateAndBindTo(any(ItemPresentationModel.class))).thenReturn(view);

		DataSetAdapter<Object> dataSetAdapter = new DataSetAdapter<Object>(valueModel, null, dropdownLayoutBinder, true);

		View result = dataSetAdapter.getDropDownView(0, null, null);

		assertThat(result, sameInstance(view));
		assertThat(result.getTag(), instanceOf(ItemPresentationModel.class));
	}

	@Test
	public void givenPreInitializeViewsIsTrue_whenInitialize_thenDataSetAdapterCountShouldReflectValueModel() {
		DataSetAdapter<Object> dataSetAdapter = new DataSetAdapter<Object>(valueModel, null, null, true);

		assertThat(dataSetAdapter.getCount(), is(valueModel.size()));
	}

	@Test
	public void givenPreInitializeViewsIsFalse_whenValueModelHasNotBeenUpdated_thenDataSetAdapterCountShouldBeZero() {
		DataSetAdapter<Object> dataSetAdapter = new DataSetAdapter<Object>(valueModel, null, null, false);

		assertThat(dataSetAdapter.getCount(), is(0));
	}

	@Test
	public void givenPreInitializeViewsIsFalse_whenValueModelFireChange_thenDataSetAdapterCountShouldReflectValueModel() {
		DataSetAdapter<Object> dataSetAdapter = new DataSetAdapter<Object>(valueModel, null, null, false);
		dataSetAdapter.observeChangesOnTheValueModel();

		valueModel.update();

		assertThat(dataSetAdapter.getCount(), is(valueModel.size()));
	}

	@Test
	public void givenValueModelIsNull_thenCountShouldBeZero() {
		DataSetAdapter<Object> dataSetAdapter = new DataSetAdapter<Object>(null, null, null, false);

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

		@SuppressWarnings("unchecked")
		@Override
		public ItemPresentationModel<Object> newItemPresentationModel() {
			return mock(ItemPresentationModel.class);
		}
	}
}
