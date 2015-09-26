package org.robobinding.widget.adapterview;

import java.util.List;

/**
 * 
 * @since 1.0
 * @author Cheng Wei
 */
class ItemLayoutsUpdater implements RowLayoutsUpdater {
	private final DataSetAdapterBuilder dataSetAdapterBuilder;

	public ItemLayoutsUpdater(DataSetAdapterBuilder dataSetAdapterBuilder) {
		this.dataSetAdapterBuilder = dataSetAdapterBuilder;
	}

	@Override
	public void updateRowLayouts(List<Integer> itemLayoutIds) {
		dataSetAdapterBuilder.setItemLayoutIds(itemLayoutIds);
	}
}
