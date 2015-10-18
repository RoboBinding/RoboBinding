package org.robobinding.supportwidget.recyclerView;

import org.robobinding.widget.adapterview.DataSetAdapterUpdater;
import org.robobinding.widget.adapterview.ItemLayoutUpdater;
import org.robobinding.widget.adapterview.ItemLayoutsUpdater;
import org.robobinding.widget.adapterview.RowLayoutAttributeFactory.UpdaterProvider;
import org.robobinding.widget.adapterview.RowLayoutUpdater;
import org.robobinding.widget.adapterview.RowLayoutsUpdater;

import android.support.v7.widget.RecyclerView;

/**
 * @since 1.0
 * @author Cheng Wei
 * 
 */
public class ItemLayoutUpdaterProvider implements UpdaterProvider {
	private final RecyclerView view;
	private final DataSetAdapterBuilder dataSetAdapterBuilder;

	public ItemLayoutUpdaterProvider(RecyclerView view, DataSetAdapterBuilder dataSetAdapterBuilder) {
		this.view = view;
		this.dataSetAdapterBuilder = dataSetAdapterBuilder;
	}

	@Override
	public RowLayoutUpdater createRowLayoutUpdater() {
		return new ItemLayoutUpdater(dataSetAdapterBuilder);
	}

	@Override
	public RowLayoutsUpdater createRowLayoutsUpdater() {
		return new ItemLayoutsUpdater(dataSetAdapterBuilder);
	}

	@Override
	public DataSetAdapterUpdater createDataSetAdapterUpdater() {
		return new DataSetAdapterUpdater() {
			@Override
			public void update() {
				DataSetAdapter dataSetAdapter = dataSetAdapterBuilder.build();
				view.setAdapter(dataSetAdapter);
			}
		};
	}

}
