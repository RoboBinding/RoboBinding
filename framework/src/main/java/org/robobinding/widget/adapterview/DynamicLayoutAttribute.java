package org.robobinding.widget.adapterview;

import org.robobinding.viewattribute.property.AlwaysPreInitializingView;
import org.robobinding.viewattribute.property.OneWayPropertyViewAttribute;

import android.widget.AdapterView;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
class DynamicLayoutAttribute implements OneWayPropertyViewAttribute<AdapterView<?>, Integer>, AlwaysPreInitializingView {
	private final RowLayoutUpdater rowLayoutUpdater;
	private final DataSetAdapterUpdater dataSetAdapterUpdater;

	public DynamicLayoutAttribute(RowLayoutUpdater rowLayoutUpdater, DataSetAdapterUpdater dataSetAdapterUpdater) {
		this.rowLayoutUpdater = rowLayoutUpdater;
		this.dataSetAdapterUpdater = dataSetAdapterUpdater;
	}

	@Override
	public void updateView(AdapterView<?> view, Integer newItemLayoutId) {
		rowLayoutUpdater.updateRowLayout(newItemLayoutId);
		dataSetAdapterUpdater.update();
	}
}