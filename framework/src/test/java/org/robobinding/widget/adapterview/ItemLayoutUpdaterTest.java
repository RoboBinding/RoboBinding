package org.robobinding.widget.adapterview;

import static org.mockito.Mockito.verify;
import static org.robobinding.util.RandomValues.anyInteger;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
@RunWith(MockitoJUnitRunner.class)
public class ItemLayoutUpdaterTest {
	@Mock
	private DataSetAdapterBuilder dataSetAdapterBuilder;

	@Test
	public void whenUpdatingRowLayout_thenSetItemLayoutOnDataSetAdapterBuilder() {
		int layoutId = anyInteger();
		ItemLayoutUpdater itemLayoutUpdater = new ItemLayoutUpdater(dataSetAdapterBuilder);

		itemLayoutUpdater.updateRowLayout(layoutId);

		verify(dataSetAdapterBuilder).setItemLayoutId(layoutId);
	}
}
