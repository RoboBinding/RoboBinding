package org.robobinding.widget.expandablelistview;

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
public class GroupSourceAttribute implements ChildViewAttributeWithAttribute<ValueModelAttribute> {
	private final DataSetExpandableListAdapterBuilder dataSetExpandableListAdapterBuilder;
	private ValueModelAttribute attribute;

	public GroupSourceAttribute(final DataSetExpandableListAdapterBuilder dataSetExpandableListAdapterBuilder) {
		this.dataSetExpandableListAdapterBuilder = dataSetExpandableListAdapterBuilder;
	}

	@Override
	public void setAttribute(ValueModelAttribute attribute) {
		this.attribute = attribute;
	}

	@Override
	public void bindTo(BindingContext bindingContext) {
		DataSetValueModel<?> valueModel = bindingContext.getDataSetPropertyValueModel(attribute.getPropertyName());
        dataSetExpandableListAdapterBuilder.setGroupValueModel(valueModel);
	}
}