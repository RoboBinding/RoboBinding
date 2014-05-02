package org.robobinding.viewattribute.adapterview;

import android.widget.AdapterView;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class DropdownLayoutAttributeFactory extends RowLayoutAttributeFactory {
    public DropdownLayoutAttributeFactory(AdapterView<?> adapterView, DataSetAdapterBuilder dataSetAdapterBuilder) {
	super(adapterView, dataSetAdapterBuilder);
    }

    @Override
    protected RowLayoutUpdater createRowLayoutUpdater(DataSetAdapterBuilder dataSetAdapterBuilder) {
	return new DropdownLayoutUpdater(dataSetAdapterBuilder);
    }
}
