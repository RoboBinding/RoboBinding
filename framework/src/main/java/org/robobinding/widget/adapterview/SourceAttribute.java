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
	private final RequiresDataSetValueModel receiver;
	private ValueModelAttribute attribute;

	public SourceAttribute(final RequiresDataSetValueModel receiver) {
		this.receiver = receiver;
	}

	@Override
	public void setAttribute(ValueModelAttribute attribute) {
		this.attribute = attribute;
	}

	@Override
	public void bindTo(BindingContext bindingContext) {
		DataSetValueModel valueModel = bindingContext.getDataSetPropertyValueModel(attribute.getPropertyName());
		receiver.setValueModel(valueModel);
	}
	
	public static interface RequiresDataSetValueModel {
		void setValueModel(DataSetValueModel valueModel);
	}
}