package org.robobinding.widget.adapterview;

import android.widget.AdapterView;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class DropdownLayoutUpdaterProvider extends AbstractUpdaterProvider {
	public DropdownLayoutUpdaterProvider(AdapterView<?> adapterView, DataSetAdapterBuilder dataSetAdapterBuilder) {
		super(adapterView, dataSetAdapterBuilder);
	}

	@Override
	public RowLayoutUpdater createRowLayoutUpdater() {
		return new DropdownLayoutUpdater(dataSetAdapterBuilder);
	}
	
	@Override
	public RowLayoutsUpdater createRowLayoutsUpdater() {
		throw new UnsupportedOperationException("multiple dropdown layouts is not supported yet");
	}
}
