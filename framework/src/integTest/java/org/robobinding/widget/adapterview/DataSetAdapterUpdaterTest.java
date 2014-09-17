package org.robobinding.widget.adapterview;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import android.widget.Adapter;
import android.widget.AdapterView;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@RunWith(MockitoJUnitRunner.class)
public class DataSetAdapterUpdaterTest {
	@Mock
	private DataSetAdapterBuilder dataSetAdapterBuilder;
	@Mock
	private AdapterView<Adapter> adapterView;

	@SuppressWarnings("unchecked")
	@Test
	public void whenUpdate_thenSetNewDataSetAdapterOnAdapterView() {
		@SuppressWarnings("rawtypes")
		DataSetAdapter newDataSetAdapter = mock(DataSetAdapter.class);
		when(dataSetAdapterBuilder.build()).thenReturn(newDataSetAdapter);

		DataSetAdapterUpdater dataSetAdapterUpdater = new DataSetAdapterUpdater(dataSetAdapterBuilder, adapterView);

		dataSetAdapterUpdater.update();

		verify(adapterView).setAdapter(newDataSetAdapter);

	}
}
