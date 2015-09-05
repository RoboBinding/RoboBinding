package org.robobinding.widget.adapterview;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.robobinding.BindingContext;
import org.robobinding.ItemBinder;
import org.robobinding.PredefinedPendingAttributesForView;
import org.robobinding.attribute.StaticResourceAttribute;
import org.robobinding.itempresentationmodel.RefreshableItemPresentationModel;
import org.robobinding.property.DataSetValueModel;
import org.robobinding.viewattribute.ViewTags;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class DataSetAdapterBuilder {
	private static final int ITEM_PRESENTATION_MODEL_KEY = ViewTags.USED_KEY1;
	
	private final BindingContext bindingContext;

	private List<Integer> itemLayoutIds;
	private List<Integer>  dropdownLayoutIds;
	private Collection<PredefinedPendingAttributesForView> itemPredefinedPendingAttributesForViewGroup;
	private Collection<PredefinedPendingAttributesForView> dropdownPredefinedPendingAttributesForViewGroup;
	private DataSetValueModel valueModel;

	public DataSetAdapterBuilder(BindingContext bindingContext) {
		this.bindingContext = bindingContext;
		this.itemPredefinedPendingAttributesForViewGroup = Collections.emptyList();
		this.dropdownPredefinedPendingAttributesForViewGroup = Collections.emptyList();
		this.dropdownLayoutIds = Collections.emptyList();
	}

	public void setItemLayoutIds(List<Integer> itemLayoutIds) {
		this.itemLayoutIds = itemLayoutIds;
	}

	public void setDropdownLayoutIds(List<Integer> dropdownLayoutIds) {
		if(dropdownLayoutIds != null) {
			this.dropdownLayoutIds = dropdownLayoutIds;
		}
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

	public void setValueModel(DataSetValueModel valueModel) {
		this.valueModel = valueModel;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public DataSetAdapter<?> build() {
		ItemBinder itemBinder = bindingContext.createItemBinder();
		ItemLayoutBinder itemLayoutBinder = new ItemLayoutBinder(itemBinder, itemPredefinedPendingAttributesForViewGroup);
		ItemLayoutBinder dropdownLayoutBinder = new ItemLayoutBinder(itemBinder, dropdownPredefinedPendingAttributesForViewGroup);
		ItemLayoutSelector itemLayoutSelector = buildItemLayoutSelector();
		DataSetAdapter<?> dataSetAdapter = new DataSetAdapter(valueModel, itemLayoutBinder, dropdownLayoutBinder, 
				itemLayoutSelector, new ViewTags<RefreshableItemPresentationModel>(ITEM_PRESENTATION_MODEL_KEY), 
				bindingContext.shouldPreInitializeViews());

		dataSetAdapter.observeChangesOnTheValueModel();
		return dataSetAdapter;
	}
	
	private ItemLayoutSelector buildItemLayoutSelector() {
		if (isSingleItemLayout()) {
			return new SingleItemLayoutSelector(itemLayoutIds.get(0), 
					dropdownLayoutIds.isEmpty()? StaticResourceAttribute.RESOURCE_ID_NOT_EXIST : dropdownLayoutIds.get(0));
		} else {
			return new MultiItemLayoutSelector(itemLayoutIds, dropdownLayoutIds);
		}
	}
	
	private boolean isSingleItemLayout() {
		return (itemLayoutIds.size() == 1) && (dropdownLayoutIds.size() <= 1);
	}
}
