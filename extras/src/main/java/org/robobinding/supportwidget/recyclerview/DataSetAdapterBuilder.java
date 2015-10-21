package org.robobinding.supportwidget.recyclerview;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.robobinding.BindingContext;
import org.robobinding.ItemBinder;
import org.robobinding.PredefinedPendingAttributesForView;
import org.robobinding.itempresentationmodel.RefreshableItemPresentationModel;
import org.robobinding.property.DataSetValueModel;
import org.robobinding.viewattribute.ViewTags;
import org.robobinding.widget.adapterview.ItemLayoutBinder;
import org.robobinding.widget.adapterview.ItemLayoutSelector;
import org.robobinding.widget.adapterview.ItemLayoutUpdater.RequiresItemLayoutId;
import org.robobinding.widget.adapterview.ItemLayoutsUpdater.RequiresItemLayoutIds;
import org.robobinding.widget.adapterview.ItemMappingUpdater.RequiresItemPredefinedMappings;
import org.robobinding.widget.adapterview.LazyDataSetValueModel;
import org.robobinding.widget.adapterview.MultiItemLayoutSelector;
import org.robobinding.widget.adapterview.SingleItemLayoutSelector;
import org.robobinding.widget.adapterview.SourceAttribute.RequiresDataSetValueModel;

import com.google.common.collect.Lists;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class DataSetAdapterBuilder implements RequiresDataSetValueModel, RequiresItemLayoutId, 
	RequiresItemLayoutIds, RequiresItemPredefinedMappings {
	private static final int ITEM_PRESENTATION_MODEL_KEY = ViewTags.USED_KEY1;
	
	private final BindingContext bindingContext;

	private List<Integer> itemLayoutIds;
	private Collection<PredefinedPendingAttributesForView> itemPredefinedMappings;
	private DataSetValueModel valueModel;

	public DataSetAdapterBuilder(BindingContext bindingContext) {
		this.bindingContext = bindingContext;
		this.itemLayoutIds = Collections.emptyList();
		this.itemPredefinedMappings = Collections.emptyList();
	}

	@Override
	public void setItemLayoutId(int itemLayoutId) {
		setItemLayoutIds(Lists.newArrayList(itemLayoutId));
	}

	@Override
	public void setItemLayoutIds(List<Integer> itemLayoutIds) {
		this.itemLayoutIds = itemLayoutIds;
	}

	@Override
	public void setItemPredefinedMappings(Collection<PredefinedPendingAttributesForView> itemPredefinedMappings) {
		if (itemPredefinedMappings != null) {
			this.itemPredefinedMappings = itemPredefinedMappings;
		}
	}
	
	@Override
	public void setValueModel(DataSetValueModel valueModel) {
		this.valueModel = valueModel;
	}

	public DataSetAdapter build() {
		ItemBinder itemBinder = bindingContext.createItemBinder();
		ItemLayoutBinder itemLayoutBinder = new ItemLayoutBinder(itemBinder,itemPredefinedMappings);
		ItemLayoutSelector itemLayoutSelector = buildItemLayoutSelector();
		DataSetAdapter dataSetAdapter = new DataSetAdapter(
				valueModelWithPreInitializeViews(valueModel, bindingContext.shouldPreInitializeViews()), 
				itemLayoutBinder, itemLayoutSelector, 
				new ViewTags<RefreshableItemPresentationModel>(ITEM_PRESENTATION_MODEL_KEY), 
				bindingContext.shouldPreInitializeViews());

		valueModel.addPropertyChangeListener(new DataSetPropertyChangeListenerAdapter(dataSetAdapter));
		return dataSetAdapter;
	}
	
	private ItemLayoutSelector buildItemLayoutSelector() {
		if (isSingleItemLayout()) {
			return new SingleItemLayoutSelector(itemLayoutIds.get(0));
		} else {
			return new MultiItemLayoutSelector(itemLayoutIds, valueModel);
		}
	}
	
	private boolean isSingleItemLayout() {
		return itemLayoutIds.size() == 1;
	}

	private DataSetValueModel valueModelWithPreInitializeViews(DataSetValueModel valueModel, boolean preInitializeViews) {
		if (preInitializeViews) {
			return valueModel;
		} else {
			return new LazyDataSetValueModel(valueModel);
		}
	}
}
