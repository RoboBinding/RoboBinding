package org.robobinding.property;

import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.robobinding.itempresentationmodel.DataSetChangeListener;
import org.robobinding.itempresentationmodel.DataSetObservable;
import org.robobinding.property.AbstractDataSetTest.DataSet;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class ObservableDataSetTest {
	@Test
	public void whenInitializeDataSet_thenListenerRegistered() {
		DataSetObservableUnderTest observable = new DataSetObservableUnderTest();
		GetSet getSet = new GetSet(observable);
		new ObservableDataSet(null, getSet);

		assertThat(observable.listener, notNullValue());
	}
	
	@Test
	public void whenChangeToObservable2_thenObservable1ListenerRemovedAndObservable2ListenerRegistered() {
		DataSetObservableUnderTest observable1 = new DataSetObservableUnderTest();
		GetSet getSet = new GetSet(observable1);
		ObservableDataSet dataSet = new ObservableDataSet(null, getSet);

		DataSetObservableUnderTest observable2 = new DataSetObservableUnderTest();
		getSet.setValue(observable2);
		dataSet.propertyChanged();

		assertThat(observable1.listener, nullValue());
		assertThat(observable2.listener, notNullValue());
	}
	
	

	@Test
	public void whenUpdateDataSet_thenDataSetPropertyReflectsChanges() {
		GetSet getSet = new GetSet();
		DataSet dataSetProperty = new DataSet(getSet);

		DataSetObservable newValue = new DataSetObservableUnderTest();
		getSet.setValue(newValue);
		dataSetProperty.propertyChanged();

		assertSame(newValue, dataSetProperty.getDataSet());
	}
	
	static class GetSet extends AbstractGetSet<DataSetObservable> {
		public DataSetObservable value;
		public GetSet() {
			super(null);
		}
		
		public GetSet(DataSetObservable value) {
			super(null);
			this.value = value;
		}
		
		@Override
		public DataSetObservable getValue() {
			return value;
		}
		
		@Override
		public void setValue(DataSetObservable newValue) {
			this.value = newValue;
		}
	}
	
	static class DataSetObservableUnderTest implements DataSetObservable {
		public DataSetChangeListener listener;

		@Override
		public int size() {
			return 0;
		}

		@Override
		public Object get(int index) {
			return null;
		}

		@Override
		public void addListener(DataSetChangeListener listener) {
			this.listener = listener; 
		}

		@Override
		public void removeListener(DataSetChangeListener listener) {
			if(this.listener == listener) {
				this.listener = null;
			}
		}
		
	}
}
