package org.robobinding.property;

import static org.junit.Assert.assertSame;
import static org.robobinding.property.MockPropertyAccessorBuilder.aPropertyAccessor;

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
		DataSetProperty dataSetProperty = new DataSetProperty(aPropertyAccessor().withValue(new Object()).build());

		Object dataSetFirstTime = dataSetProperty.getDataSet();

		Object dataSetSecondTime = dataSetProperty.getDataSet();

		assertSame(dataSetSecondTime, dataSetFirstTime);
	}

	@Test
	public void whenUpdateDataSet_thenDataSetPropertyReflectsChanges() {
		MockPropertyAccessorBuilder propertyAccessorBuilder = aPropertyAccessor().withValue(new Object());
		DataSetProperty dataSetProperty = new DataSetProperty(propertyAccessorBuilder.build());

		Object newValue = new Object();
		propertyAccessorBuilder.withValue(newValue);
		dataSetProperty.propertyChanged();

		assertSame(newValue, dataSetProperty.getDataSet());
	}

	static class DataSetProperty extends AbstractDataSetProperty {
		public DataSetProperty(PropertyAccessor propertyAccessor) {
			super(null, propertyAccessor, null);
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
}
