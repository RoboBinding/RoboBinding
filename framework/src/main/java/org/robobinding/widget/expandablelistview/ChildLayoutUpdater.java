package org.robobinding.widget.expandablelistview;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Jihun Lee
 */
class ChildLayoutUpdater implements ExpandableRowLayoutUpdater {
	private final DataSetExpandableListAdapterBuilder dataSetExpandableListAdapterBuilder;

	public ChildLayoutUpdater(DataSetExpandableListAdapterBuilder dataSetAdapterBuilder) {
		this.dataSetExpandableListAdapterBuilder = dataSetAdapterBuilder;
	}

	@Override
	public void updateRowLayout(int itemLayoutId) {
        dataSetExpandableListAdapterBuilder.setChildLayoutId(itemLayoutId);
	}
}
