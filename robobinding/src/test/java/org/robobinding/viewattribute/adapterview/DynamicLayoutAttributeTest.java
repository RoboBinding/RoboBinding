package org.robobinding.viewattribute.adapterview;

import static org.mockito.Mockito.verify;
import static org.robobinding.viewattribute.MockPropertyViewAttributeConfigBuilder.aPropertyViewAttributeConfig;
import static org.robobinding.viewattribute.RandomValues.anyInteger;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.robobinding.viewattribute.PropertyViewAttributeConfig;

import android.widget.AdapterView;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
@RunWith(MockitoJUnitRunner.class)
public class DynamicLayoutAttributeTest {
    private PropertyViewAttributeConfig<AdapterView<?>> config = aPropertyViewAttributeConfig(null);
    @Mock
    private RowLayoutUpdater rowLayoutUpdater;
    @Mock
    private DataSetAdapterUpdater dataSetAdapterUpdater;

    @Test
    public void givenBound_whenUpdatingValueModel_thenUpdateItemLayoutOnRowLayoutUpdater() {
	int newItemLayoutId = anyInteger();

	DynamicLayoutAttribute dynamicLayoutAttribute = new DynamicLayoutAttribute(config, rowLayoutUpdater, dataSetAdapterUpdater);
	dynamicLayoutAttribute.valueModelUpdated(newItemLayoutId);

	verify(rowLayoutUpdater).updateRowLayout(newItemLayoutId);
    }

    @Test
    public void givenBound_whenUpdatingValueModel_thenExecuteUpdateOnDataSetAdapterUpdater() {
	int newItemLayoutId = anyInteger();

	DynamicLayoutAttribute dynamicLayoutAttribute = new DynamicLayoutAttribute(config, rowLayoutUpdater, dataSetAdapterUpdater);
	dynamicLayoutAttribute.valueModelUpdated(newItemLayoutId);

	verify(dataSetAdapterUpdater).update();
    }
}
