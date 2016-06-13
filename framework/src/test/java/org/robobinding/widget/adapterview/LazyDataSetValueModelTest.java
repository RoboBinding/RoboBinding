package org.robobinding.widget.adapterview;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.robobinding.itempresentationmodel.DataSetChangeListeners;
import org.robobinding.itempresentationmodel.RefreshableItemPresentationModel;
import org.robobinding.itempresentationmodel.ViewTypeSelectionContext;
import org.robobinding.property.DataSetPropertyChangeListener;
import org.robobinding.property.DataSetValueModel;
import org.robobinding.util.RandomValues;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class LazyDataSetValueModelTest {
	@Test
	public void sizeShouldBeZeroAfterInitialization() {
		DataSetValueModelUnderTest delegateValueModel = new DataSetValueModelUnderTest(RandomValues.anyInteger());
		LazyDataSetValueModel valueModel = new LazyDataSetValueModel(delegateValueModel);
		assertThat(valueModel.size(), is(0));
	}

	@Test
	public void onValueModelChange_sizeShouldEqualToThatOfDelegateValueModel() {
		int size = RandomValues.anyInteger();
		DataSetValueModelUnderTest delegateValueModel = new DataSetValueModelUnderTest(size);
		LazyDataSetValueModel valueModel = new LazyDataSetValueModel(delegateValueModel);
		delegateValueModel.fireChange();

		assertThat(valueModel.size(), is(size));
	}
	
	private static class DataSetValueModelUnderTest implements DataSetValueModel {
		private final int size;
		private DataSetChangeListeners listeners;
		
		public DataSetValueModelUnderTest(int size) {
			this.size = size;
			
			listeners = new DataSetChangeListeners();
		}

		@Override
		public int size() {
			return size;
		}

		@Override
		public void addPropertyChangeListener(DataSetPropertyChangeListener listener) {
			listeners.add(listener);
		}

		@Override
		public void removePropertyChangeListener(DataSetPropertyChangeListener listener) {
			listeners.remove(listener);
		}

		public void fireChange() {
			listeners.notifyChanged(null);
		}

		@Override
		public int selectViewType(ViewTypeSelectionContext<Object> context) {
			return 0;
		}

		@Override
		public Object get(int index) {
			return null;
		}

		@Override
		public RefreshableItemPresentationModel newRefreshableItemPresentationModel(int itemViewType) {
			return null;
		}
		
		@Override
		public boolean preInitializingViewsWithDefault(boolean defaultValue) {
			return false;
		}
		
	}
}
