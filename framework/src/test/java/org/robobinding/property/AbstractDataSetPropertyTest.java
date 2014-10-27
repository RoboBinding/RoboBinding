package org.robobinding.property;

import static org.junit.Assert.assertSame;

import org.junit.Test;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class AbstractDataSetPropertyTest {
	@Test
	public void givenGetDataSet_whenGetDataSetAgain_thenReturnSameInstance() {
		GetSet getSet = new GetSet(new Object());
		DataSet dataSetProperty = new DataSet(getSet);

		Object dataSetFirstTime = dataSetProperty.getDataSet();

		Object dataSetSecondTime = dataSetProperty.getDataSet();

		assertSame(dataSetSecondTime, dataSetFirstTime);
	}

	@Test
	public void whenUpdateDataSet_thenDataSetPropertyReflectsChanges() {
		GetSet getSet = new GetSet(new Object());
		DataSet dataSetProperty = new DataSet(getSet);

		Object newValue = new Object();
		getSet.value = newValue;
		dataSetProperty.propertyChanged();

		assertSame(newValue, dataSetProperty.getDataSet());
	}

	static class DataSet extends AbstractDataSet {
		public DataSet(AbstractGetSet<?> getSet) {
			super(null, getSet);
		}

		@Override
		public int size() {
			return 0;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}
	}
	
	static class GetSet extends AbstractGetSet<Object> {
		public Object value;
		public GetSet(Object value) {
			super(null);
			this.value = value;
		}
		
		@Override
		public Object getValue() {
			return value;
		}
	}
}
