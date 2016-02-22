package org.robobinding.widget.adapterview;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.robobinding.BindingContext;
import org.robobinding.ItemBinder;
import org.robobinding.PredefinedPendingAttributesForView;
import org.robobinding.itempresentationmodel.RefreshableItemPresentationModel;
import org.robobinding.property.DataSetPropertyChangeListener;
import org.robobinding.property.DataSetValueModel;
import org.robobinding.property.PropertyChangeListener;
import org.robobinding.property.PropertyChangeListenerAdapters;
import org.robobinding.viewattribute.ViewTags;
import org.robobinding.widget.adapterview.ItemLayoutUpdater.RequiresItemLayoutId;
import org.robobinding.widget.adapterview.ItemLayoutsUpdater.RequiresItemLayoutIds;
import org.robobinding.widget.adapterview.ItemMappingUpdater.RequiresItemPredefinedMappings;
import org.robobinding.widget.adapterview.SourceAttribute.RequiresDataSetValueModel;

import com.google.common.collect.Lists;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class DataSetAdapterBuilder implements RequiresItemLayoutId, RequiresItemLayoutIds, 
	RequiresDataSetValueModel, RequiresItemPredefinedMappings {
	private static final int ITEM_PRESENTATION_MODEL_KEY = ViewTags.USED_KEY1;
	
	private final BindingContext bindingContext;

	private List<Integer> itemLayoutIds;
	private int  dropdownLayoutId;
	private Collection<PredefinedPendingAttributesForView> itemPredefinedMappings;
	private Collection<PredefinedPendingAttributesForView> dropdownPredefinedMappings;
	private DataSetValueModel valueModel;
	
	private DataSetPropertyChangeListener oldListener;

	public DataSetAdapterBuilder(BindingContext bindingContext) {
		this.bindingContext = bindingContext;
		this.itemLayoutIds = Collections.emptyList();
		this.itemPredefinedMappings = Collections.emptyList();
		this.dropdownPredefinedMappings = Collections.emptyList();
	}

	@Override
	public void setItemLayoutId(int itemLayoutId) {
		setItemLayoutIds(Lists.newArrayList(itemLayoutId));
	}

	@Override
	public void setItemLayoutIds(List<Integer> itemLayoutIds) {
		this.itemLayoutIds = itemLayoutIds;
	}

	public void setDropdownLayoutId(int dropdownLayoutId) {
		this.dropdownLayoutId = dropdownLayoutId;
	}

	@Override
	public void setItemPredefinedMappings(Collection<PredefinedPendingAttributesForView> itemPredefinedMappings) {
		if (itemPredefinedMappings != null) {
			this.itemPredefinedMappings = itemPredefinedMappings;
		}
	}

	public void setDropdownPredefinedMappings(
			Collection<PredefinedPendingAttributesForView> dropdownPredefinedMappings) {
		if (dropdownPredefinedMappings != null) {
			this.dropdownPredefinedMappings = dropdownPredefinedMappings;
		}
	}
	
	@Override
	public void setValueModel(DataSetValueModel valueModel) {
		this.valueModel = valueModel;
	}

	public DataSetAdapter build() {
		ItemBinder itemBinder = bindingContext.createItemBinder();
		ItemLayoutBinder itemLayoutBinder = new ItemLayoutBinder(itemBinder, itemPredefinedMappings);
		ItemLayoutBinder dropdownLayoutBinder = new ItemLayoutBinder(itemBinder, dropdownPredefinedMappings);
		ItemLayoutSelector itemLayoutSelector = buildItemLayoutSelector();
		DataSetAdapter dataSetAdapter = new DataSetAdapter(
				valueModelWithPreInitializeViews(valueModel, bindingContext.shouldPreInitializeViews()),
				itemLayoutBinder, dropdownLayoutBinder, itemLayoutSelector, dropdownLayoutId,
				new ViewTags<RefreshableItemPresentationModel>(ITEM_PRESENTATION_MODEL_KEY)
		);

		registerPropertyChangeListener(dataSetAdapter);
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
	
	private void registerPropertyChangeListener(DataSetAdapter dataSetAdapter) {
		valueModel.removePropertyChangeListener(oldListener);
		DataSetPropertyChangeListener listener = createDataSetPropertyChangeListenerFor(dataSetAdapter);
		valueModel.addPropertyChangeListener(listener);
		oldListener = listener;
	}
	
	private DataSetPropertyChangeListener createDataSetPropertyChangeListenerFor(final DataSetAdapter dataSetAdapter) {
		return PropertyChangeListenerAdapters.adapt(new PropertyChangeListener() {
			
			@Override
			public void propertyChanged() {
				dataSetAdapter.notifyDataSetChanged();
			}
		});
	}
}
