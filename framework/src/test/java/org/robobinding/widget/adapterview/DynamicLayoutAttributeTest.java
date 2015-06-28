package org.robobinding.widget.adapterview;

import static org.mockito.Mockito.verify;
import static org.robobinding.util.RandomValues.anyInteger;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import android.widget.AdapterView;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
@RunWith(MockitoJUnitRunner.class)
public class DynamicLayoutAttributeTest {
	@Mock
	private RowLayoutUpdater rowLayoutUpdater;
	@Mock
	private DataSetAdapterUpdater dataSetAdapterUpdater;
	@Mock
	private AdapterView<?> view;

	@Test
	public void whenUpdateView_thenUpdateItemLayoutOnRowLayoutUpdater() {
		int newItemLayoutId = anyInteger();

		DynamicLayoutAttribute dynamicLayoutAttribute = new DynamicLayoutAttribute(rowLayoutUpdater, dataSetAdapterUpdater);
		dynamicLayoutAttribute.updateView(view, newItemLayoutId);

		verify(rowLayoutUpdater).updateRowLayout(newItemLayoutId);
	}

	@Test
	public void whenUpdateView_thenExecuteUpdateOnDataSetAdapterUpdater() {
		int newItemLayoutId = anyInteger();

		DynamicLayoutAttribute dynamicLayoutAttribute = new DynamicLayoutAttribute(rowLayoutUpdater, dataSetAdapterUpdater);
		dynamicLayoutAttribute.updateView(view, newItemLayoutId);

		verify(dataSetAdapterUpdater).update();
	}
}
