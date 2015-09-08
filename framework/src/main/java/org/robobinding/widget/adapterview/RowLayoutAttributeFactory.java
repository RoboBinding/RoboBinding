package org.robobinding.widget.adapterview;

import org.robobinding.attribute.AbstractPropertyAttribute;
import org.robobinding.attribute.PropertyAttributeVisitor;
import org.robobinding.attribute.StaticResourceAttribute;
import org.robobinding.attribute.StaticResourcesAttribute;
import org.robobinding.attribute.ValueModelAttribute;
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
public abstract class RowLayoutAttributeFactory implements PropertyAttributeVisitor<ChildViewAttribute> {
	private final AdapterView<?> adapterView;
	private final DataSetAdapterBuilder dataSetAdapterBuilder;

	protected RowLayoutAttributeFactory(AdapterView<?> adapterView, DataSetAdapterBuilder dataSetAdapterBuilder) {
		this.adapterView = adapterView;
		this.dataSetAdapterBuilder = dataSetAdapterBuilder;
	}

	public ChildViewAttribute createRowLayoutAttribute(AbstractPropertyAttribute attribute) {
		return attribute.accept(this);
	}
	
	@Override
	public ChildViewAttribute visitValueModel(ValueModelAttribute attribute) {
		DataSetAdapterUpdater dataSetAdapterUpdater = new DataSetAdapterUpdater(dataSetAdapterBuilder, adapterView);
		DynamicLayoutAttribute layoutAttribute = new DynamicLayoutAttribute(createRowLayoutUpdater(dataSetAdapterBuilder), 
				dataSetAdapterUpdater);
		return new ChildViewAttributeAdapter(adapterView, layoutAttribute, attribute);
	}
	
	@Override
	public ChildViewAttribute visitStaticResource(StaticResourceAttribute attribute) {
		return new StaticLayoutAttribute(createRowLayoutUpdater(dataSetAdapterBuilder), attribute);
	}
	
	@Override
	public ChildViewAttribute visitStaticResources(StaticResourcesAttribute attribute) {
		return new StaticLayoutsAttribute(createRowLayoutsUpdater(dataSetAdapterBuilder), attribute);
	}

	protected abstract RowLayoutUpdater createRowLayoutUpdater(DataSetAdapterBuilder dataSetAdapterBuilder);
	protected abstract RowLayoutsUpdater createRowLayoutsUpdater(DataSetAdapterBuilder dataSetAdapterBuilder);
}
