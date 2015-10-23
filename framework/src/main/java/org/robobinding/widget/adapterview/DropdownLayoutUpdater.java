package org.robobinding.widget.adapterview;


/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
class DropdownLayoutUpdater implements RowLayoutUpdater {
	private final DataSetAdapterBuilder dataSetAdapterBuilder;

	public DropdownLayoutUpdater(DataSetAdapterBuilder dataSetAdapterBuilder) {
		this.dataSetAdapterBuilder = dataSetAdapterBuilder;
	}

	@Override
	public void updateRowLayout(int dropdownLayoutId) {
		dataSetAdapterBuilder.setDropdownLayoutId(dropdownLayoutId);
	}
}
