package org.robobinding.widget.adapterview;

import java.util.Collection;

import org.robobinding.BindingContext;
import org.robobinding.ItemBinder;
import org.robobinding.PredefinedPendingAttributesForView;
import org.robobinding.property.DataSetValueModel;

import com.google.common.collect.Lists;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class DataSetAdapterBuilder {
	private final BindingContext bindingContext;

	private int itemLayoutId;
	private int dropDownLayoutId;
	private Collection<PredefinedPendingAttributesForView> itemPredefinedPendingAttributesForViewGroup;
	private Collection<PredefinedPendingAttributesForView> dropdownPredefinedPendingAttributesForViewGroup;
	private DataSetValueModel<?> valueModel;

	public DataSetAdapterBuilder(BindingContext bindingContext) {
		this.bindingContext = bindingContext;
		this.itemPredefinedPendingAttributesForViewGroup = Lists.newArrayList();
		this.dropdownPredefinedPendingAttributesForViewGroup = Lists.newArrayList();
	}

	public void setItemLayoutId(int itemLayoutId) {
		this.itemLayoutId = itemLayoutId;
	}

	public void setDropDownLayoutId(int dropDownLayoutId) {
		this.dropDownLayoutId = dropDownLayoutId;
	}

	public void setItemPredefinedPendingAttributesForViewGroup(Collection<PredefinedPendingAttributesForView> itemPredefinedPendingAttributesForViewGroup) {
		if (itemPredefinedPendingAttributesForViewGroup != null) {
			this.itemPredefinedPendingAttributesForViewGroup = itemPredefinedPendingAttributesForViewGroup;
		}
	}

	public void setDropdownPredefinedPendingAttributesForViewGroup(
			Collection<PredefinedPendingAttributesForView> dropdownPredefinedPendingAttributesForViewGroup) {
		if (dropdownPredefinedPendingAttributesForViewGroup != null) {
			this.dropdownPredefinedPendingAttributesForViewGroup = dropdownPredefinedPendingAttributesForViewGroup;
		}
	}

	public void setValueModel(DataSetValueModel<?> valueModel) {
		this.valueModel = valueModel;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public DataSetAdapter<?> build() {
		ItemBinder itemBinder = bindingContext.createItemBinder();
		ItemLayoutBinder itemLayoutBinder = new ItemLayoutBinder(itemBinder, itemLayoutId, itemPredefinedPendingAttributesForViewGroup);
		ItemLayoutBinder dropdownLayoutBinder = new ItemLayoutBinder(itemBinder, dropDownLayoutId, dropdownPredefinedPendingAttributesForViewGroup);
		DataSetAdapter<?> dataSetAdapter = new DataSetAdapter(valueModel, itemLayoutBinder, dropdownLayoutBinder, 
				bindingContext.shouldPreInitializeViews());

		dataSetAdapter.observeChangesOnTheValueModel();
		return dataSetAdapter;
	}
}
