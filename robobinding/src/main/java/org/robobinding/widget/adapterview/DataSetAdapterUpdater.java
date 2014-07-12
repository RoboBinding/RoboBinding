package org.robobinding.widget.adapterview;

import android.widget.AdapterView;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class DataSetAdapterUpdater {
    private final DataSetAdapterBuilder dataSetAdapterBuilder;
    private final AdapterView<?> adapterView;
    
    public DataSetAdapterUpdater(DataSetAdapterBuilder dataSetAdapterBuilder, AdapterView<?> adapterView) {
	this.dataSetAdapterBuilder = dataSetAdapterBuilder;
	this.adapterView = adapterView;
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void update() {
	DataSetAdapter<?> dataSetAdapter = dataSetAdapterBuilder.build();
	((AdapterView) adapterView).setAdapter(dataSetAdapter);
    }
}
