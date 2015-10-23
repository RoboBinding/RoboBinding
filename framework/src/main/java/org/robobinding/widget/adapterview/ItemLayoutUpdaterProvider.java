package org.robobinding.widget.adapterview;

import android.widget.AdapterView;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class ItemLayoutUpdaterProvider extends AbstractUpdaterProvider {
	public ItemLayoutUpdaterProvider(AdapterView<?> adapterView, DataSetAdapterBuilder dataSetAdapterBuilder) {
		super(adapterView, dataSetAdapterBuilder);
	}

	@Override
	public RowLayoutUpdater createRowLayoutUpdater() {
		return new ItemLayoutUpdater(dataSetAdapterBuilder);
	}
	
	@Override
	public RowLayoutsUpdater createRowLayoutsUpdater() {
		return new ItemLayoutsUpdater(dataSetAdapterBuilder);
	}
}
