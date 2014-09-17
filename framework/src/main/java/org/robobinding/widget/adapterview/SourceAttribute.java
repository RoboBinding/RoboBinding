package org.robobinding.widget.adapterview;

import org.robobinding.BindingContext;
import org.robobinding.attribute.ValueModelAttribute;
import org.robobinding.property.DataSetValueModel;
import org.robobinding.viewattribute.grouped.ChildViewAttributeWithAttribute;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class SourceAttribute implements ChildViewAttributeWithAttribute<ValueModelAttribute> {
	private final DataSetAdapterBuilder dataSetAdapterBuilder;
	private ValueModelAttribute attribute;

	public SourceAttribute(final DataSetAdapterBuilder dataSetAdapterBuilder) {
		this.dataSetAdapterBuilder = dataSetAdapterBuilder;
	}

	@Override
	public void setAttribute(ValueModelAttribute attribute) {
		this.attribute = attribute;
	}

	@Override
	public void bindTo(BindingContext bindingContext) {
		DataSetValueModel<?> valueModel = bindingContext.getDataSetPropertyValueModel(attribute.getPropertyName());
		dataSetAdapterBuilder.setValueModel(valueModel);
	}
}