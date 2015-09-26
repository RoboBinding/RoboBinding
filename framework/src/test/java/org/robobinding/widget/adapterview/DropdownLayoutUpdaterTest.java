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
public class DropdownLayoutUpdaterTest {
	@Mock
	private DataSetAdapterBuilder dataSetAdapterBuilder;

	@Test
	public void whenUpdatingRowLayout_thenSetDropdownLayoutOnDataSetAdapterBuilder() {
		int layoutId = anyInteger();
		DropdownLayoutUpdater dropdownLayoutUpdater = new DropdownLayoutUpdater(dataSetAdapterBuilder);

		dropdownLayoutUpdater.updateRowLayout(layoutId);

		verify(dataSetAdapterBuilder).setDropdownLayoutId(layoutId);
	}
}
