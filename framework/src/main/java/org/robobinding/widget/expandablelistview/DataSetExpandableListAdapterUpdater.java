package org.robobinding.widget.expandablelistview;

import android.widget.ExpandableListView;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Jihun Lee
 */
public class DataSetExpandableListAdapterUpdater {
	private final DataSetExpandableListAdapterBuilder dataSetExpandableListAdapterBuilder;
	private final ExpandableListView expandableListView;

	public DataSetExpandableListAdapterUpdater(DataSetExpandableListAdapterBuilder dataSetExpandableListAdapterBuilder, ExpandableListView expandableListView) {
		this.dataSetExpandableListAdapterBuilder = dataSetExpandableListAdapterBuilder;
		this.expandableListView = expandableListView;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void update() {
		DataSetExpandableListAdapter<?, ?> dataSetExpandableListAdapter = dataSetExpandableListAdapterBuilder.build();
        expandableListView.setAdapter(dataSetExpandableListAdapter);
	}
}
