package org.robobinding;

import org.robobinding.BindingContext.ItemBinderFactory;
import org.robobinding.property.DataSetValueModel;


/**
 * @since 1.0
 * @author Cheng Wei
 *
 */
public class ItemBindingContext {
	private final ItemBinderFactory factory;
	private final DataSetValueModel valueModel;
	private final boolean preInitializeViews;
	
	ItemBindingContext(ItemBinderFactory factory, DataSetValueModel valueModel, 
			boolean preInitializeViews) {
		this.factory = factory;
		this.valueModel = valueModel;
		this.preInitializeViews = preInitializeViews;
	}
	
	/**
	 * For framework internal use only.
	 */
	public ItemBinder createItemBinder() {
		return factory.createItemBinder();
	}

	public boolean shouldPreInitializeViews() {
		return preInitializeViews;
	}
	
	public DataSetValueModel valueModel() {
		return valueModel;
	}
}
