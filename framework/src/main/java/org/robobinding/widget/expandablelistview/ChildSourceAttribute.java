package org.robobinding.widget.expandablelistview;

import org.robobinding.BindingContext;
import org.robobinding.attribute.ValueModelAttribute;
import org.robobinding.property.DataSetValueModel;
import org.robobinding.property.GroupedDataSetValueModel;
import org.robobinding.viewattribute.grouped.ChildViewAttributeWithAttribute;
import org.robobinding.widget.adapterview.DataSetAdapterBuilder;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Jihun Lee
 */
public class ChildSourceAttribute implements ChildViewAttributeWithAttribute<ValueModelAttribute> {
	private final DataSetExpandableListAdapterBuilder dataSetExpandableListAdapterBuilder;
	private ValueModelAttribute attribute;

	public ChildSourceAttribute(final DataSetExpandableListAdapterBuilder dataSetExpandableListAdapterBuilder) {
		this.dataSetExpandableListAdapterBuilder = dataSetExpandableListAdapterBuilder;
	}

	@Override
	public void setAttribute(ValueModelAttribute attribute) {
		this.attribute = attribute;
	}

	@Override
	public void bindTo(BindingContext bindingContext) {
		GroupedDataSetValueModel<?> valueModel = bindingContext.getGroupedDataSetValueModel(attribute.getPropertyName());
        dataSetExpandableListAdapterBuilder.setChildGroupedValueModel(valueModel);
	}
}