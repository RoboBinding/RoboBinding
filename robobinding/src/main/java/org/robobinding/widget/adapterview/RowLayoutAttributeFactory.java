package org.robobinding.widget.adapterview;

import org.robobinding.attribute.AbstractPropertyAttribute;
import org.robobinding.viewattribute.grouped.ChildViewAttribute;
import org.robobinding.viewattribute.grouped.ChildViewAttributeAdapter;

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

    public ChildViewAttribute createRowLayoutAttribute(AbstractPropertyAttribute attribute) {
	RowLayoutUpdater rowLayoutUpdater = createRowLayoutUpdater(dataSetAdapterBuilder);
	if (attribute.isStaticResource()) {
	    return createStaticLayoutAttribute(attribute, rowLayoutUpdater);
	} else {
	    return createDynamicLayoutAttribute(attribute, rowLayoutUpdater);
	}
    }

    private ChildViewAttribute createStaticLayoutAttribute(AbstractPropertyAttribute attribute, RowLayoutUpdater rowLayoutUpdater) {
	return new StaticLayoutAttribute(rowLayoutUpdater, attribute.asStaticResourceAttribute());
    }

    private ChildViewAttribute createDynamicLayoutAttribute(AbstractPropertyAttribute attribute, RowLayoutUpdater rowLayoutUpdater) {
	DataSetAdapterUpdater dataSetAdapterUpdater = new DataSetAdapterUpdater(dataSetAdapterBuilder, adapterView);
	return new ChildViewAttributeAdapter<AdapterView<?>>(adapterView,
		new DynamicLayoutAttribute(rowLayoutUpdater, dataSetAdapterUpdater),
		attribute.asValueModelAttribute());
    }

    protected abstract RowLayoutUpdater createRowLayoutUpdater(DataSetAdapterBuilder dataSetAdapterBuilder);
}
