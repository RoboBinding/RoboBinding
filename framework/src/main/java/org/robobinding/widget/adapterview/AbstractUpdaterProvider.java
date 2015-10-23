package org.robobinding.widget.adapterview;

import org.robobinding.widget.adapterview.RowLayoutAttributeFactory.UpdaterProvider;

import android.widget.AdapterView;

/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public abstract class AbstractUpdaterProvider implements UpdaterProvider {
	private final AdapterView<?> adapterView;
	protected final DataSetAdapterBuilder dataSetAdapterBuilder;
	
	public AbstractUpdaterProvider(AdapterView<?> adapterView, DataSetAdapterBuilder dataSetAdapterBuilder) {
		this.adapterView = adapterView;
		this.dataSetAdapterBuilder = dataSetAdapterBuilder;
	}
	
	@Override
	public DataSetAdapterUpdater createDataSetAdapterUpdater() {
		return new DataSetAdapterUpdater() {
			@SuppressWarnings({ "unchecked", "rawtypes" })
			public void update() {
				DataSetAdapter dataSetAdapter = dataSetAdapterBuilder.build();
				((AdapterView) adapterView).setAdapter(dataSetAdapter);
			}
		};
	}

}
