package org.robobinding.widget.expandablelistview;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Jihun Lee
 */
class GroupLayoutUpdater implements ExpandableRowLayoutUpdater {
	private final DataSetExpandableListAdapterBuilder dataSetExpandableListAdapterBuilder;

	public GroupLayoutUpdater(DataSetExpandableListAdapterBuilder adapterBuilder) {
		this.dataSetExpandableListAdapterBuilder = adapterBuilder;
	}

	@Override
	public void updateRowLayout(int itemLayoutId) {
        dataSetExpandableListAdapterBuilder.setGroupLayoutId(itemLayoutId);
    }
}
