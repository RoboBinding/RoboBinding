package org.robobinding.viewattribute.adapterview;

import org.robobinding.attribute.AbstractPropertyAttribute;
import org.robobinding.viewattribute.PropertyViewAttributeConfig;
import org.robobinding.viewattribute.ViewAttribute;

import android.widget.AdapterView;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
public abstract class RowLayoutAttributeFactory {
    private final AdapterView<?> adapterView;
    private final DataSetAdapterBuilder dataSetAdapterBuilder;

    protected RowLayoutAttributeFactory(AdapterView<?> adapterView, DataSetAdapterBuilder dataSetAdapterBuilder) {
	this.adapterView = adapterView;
	this.dataSetAdapterBuilder = dataSetAdapterBuilder;
    }

    public ViewAttribute createRowLayoutAttribute(AbstractPropertyAttribute attribute) {
	RowLayoutUpdater rowLayoutUpdater = createRowLayoutUpdater(dataSetAdapterBuilder);
	if (attribute.isStaticResource()) {
	    return createStaticLayoutAttribute(attribute, rowLayoutUpdater);
	} else {
	    return createDynamicLayoutAttribute(attribute, rowLayoutUpdater);
	}
    }

    private ViewAttribute createStaticLayoutAttribute(AbstractPropertyAttribute attribute, RowLayoutUpdater rowLayoutUpdater) {
	return new StaticLayoutAttribute(attribute.asStaticResourceAttribute(), rowLayoutUpdater);
    }

    private ViewAttribute createDynamicLayoutAttribute(AbstractPropertyAttribute attribute, RowLayoutUpdater rowLayoutUpdater) {
	PropertyViewAttributeConfig<AdapterView<?>> attributeConfig = new PropertyViewAttributeConfig<AdapterView<?>>(adapterView,
		attribute.asValueModelAttribute());
	DataSetAdapterUpdater dataSetAdapterUpdater = new DataSetAdapterUpdater(dataSetAdapterBuilder, adapterView);
	return new DynamicLayoutAttribute(attributeConfig, rowLayoutUpdater, dataSetAdapterUpdater);
    }

    protected abstract RowLayoutUpdater createRowLayoutUpdater(DataSetAdapterBuilder dataSetAdapterBuilder);
}
