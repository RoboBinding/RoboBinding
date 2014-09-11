package org.robobinding.widget.adapterview;

import android.widget.AdapterView;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class ItemLayoutAttributeFactory extends RowLayoutAttributeFactory {
    public ItemLayoutAttributeFactory(AdapterView<?> adapterView, DataSetAdapterBuilder dataSetAdapterBuilder) {
	super(adapterView, dataSetAdapterBuilder);
    }

    @Override
    protected RowLayoutUpdater createRowLayoutUpdater(DataSetAdapterBuilder dataSetAdapterBuilder) {
	return new ItemLayoutUpdater(dataSetAdapterBuilder);
    }
}
