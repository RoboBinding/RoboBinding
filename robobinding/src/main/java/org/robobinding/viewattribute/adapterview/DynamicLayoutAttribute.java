package org.robobinding.viewattribute.adapterview;

import org.robobinding.viewattribute.AbstractReadOnlyPropertyViewAttribute;
import org.robobinding.viewattribute.PropertyViewAttributeConfig;

import android.widget.AdapterView;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
class DynamicLayoutAttribute extends AbstractReadOnlyPropertyViewAttribute<AdapterView<?>, Integer> {
    private final RowLayoutUpdater rowLayoutUpdater;
    private final DataSetAdapterUpdater dataSetAdapterUpdater;

    public DynamicLayoutAttribute(PropertyViewAttributeConfig<AdapterView<?>> config, RowLayoutUpdater rowLayoutUpdater, 
	    DataSetAdapterUpdater dataSetAdapterUpdater) {
	super.initialize(config);
	this.rowLayoutUpdater = rowLayoutUpdater;
	this.dataSetAdapterUpdater = dataSetAdapterUpdater;
    }

    @Override
    protected void valueModelUpdated(Integer newItemLayoutId) {
	rowLayoutUpdater.updateRowLayout(newItemLayoutId);
	dataSetAdapterUpdater.update();
    }
}