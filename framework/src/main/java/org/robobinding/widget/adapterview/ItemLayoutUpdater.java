package org.robobinding.widget.adapterview;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
class ItemLayoutUpdater implements RowLayoutUpdater {
    private final DataSetAdapterBuilder dataSetAdapterBuilder;

    public ItemLayoutUpdater(DataSetAdapterBuilder dataSetAdapterBuilder) {
	this.dataSetAdapterBuilder = dataSetAdapterBuilder;
    }

    @Override
    public void updateRowLayout(int itemLayoutId) {
	dataSetAdapterBuilder.setItemLayoutId(itemLayoutId);
    }
}
