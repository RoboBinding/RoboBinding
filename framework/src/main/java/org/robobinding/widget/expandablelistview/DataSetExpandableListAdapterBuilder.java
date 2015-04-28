package org.robobinding.widget.expandablelistview;

import com.google.common.collect.Lists;
import org.robobinding.BindingContext;
import org.robobinding.ItemBinder;
import org.robobinding.PredefinedPendingAttributesForView;
import org.robobinding.property.DataSetValueModel;
import org.robobinding.property.GroupedDataSetValueModel;
import org.robobinding.widget.adapterview.ItemLayoutBinder;

import java.util.Collection;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Jihun Lee
 */
public class DataSetExpandableListAdapterBuilder {
	private final BindingContext bindingContext;

	private int groupLayoutId;
	private int childLayoutId;
	private Collection<PredefinedPendingAttributesForView> groupPredefinedPendingAttributesForViewGroup;
	private Collection<PredefinedPendingAttributesForView> childPredefinedPendingAttributesForViewGroup;

    private DataSetValueModel<?> groupValueModel;
    private GroupedDataSetValueModel<?> childGroupedValueModel;

	public DataSetExpandableListAdapterBuilder(BindingContext bindingContext) {
		this.bindingContext = bindingContext;
		this.groupPredefinedPendingAttributesForViewGroup = Lists.newArrayList();
		this.childPredefinedPendingAttributesForViewGroup = Lists.newArrayList();
	}

	public void setGroupLayoutId(int groupLayoutId) {
		this.groupLayoutId = groupLayoutId;
	}

	public void setChildLayoutId(int childLayoutId) {
		this.childLayoutId = childLayoutId;
	}

	public void setGroupPredefinedPendingAttributesForViewGroup(Collection<PredefinedPendingAttributesForView> groupPredefinedPendingAttributesForViewGroup) {
		if (groupPredefinedPendingAttributesForViewGroup != null) {
			this.groupPredefinedPendingAttributesForViewGroup = groupPredefinedPendingAttributesForViewGroup;
		}
	}

	public void setChildPredefinedPendingAttributesForViewGroup(
            Collection<PredefinedPendingAttributesForView> childPredefinedPendingAttributesForViewGroup) {
		if (childPredefinedPendingAttributesForViewGroup != null) {
			this.childPredefinedPendingAttributesForViewGroup = childPredefinedPendingAttributesForViewGroup;
		}
	}

	public void setGroupValueModel(DataSetValueModel<?> groupValueModel) {
		this.groupValueModel = groupValueModel;
	}

    public void setChildGroupedValueModel(GroupedDataSetValueModel<?> groupedValueModel) {
        this.childGroupedValueModel = groupedValueModel;
    }

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public DataSetExpandableListAdapter<?, ?> build() {
		ItemBinder itemBinder = bindingContext.createItemBinder();

		ItemLayoutBinder groupLayoutBinder = new ItemLayoutBinder(itemBinder, groupLayoutId, groupPredefinedPendingAttributesForViewGroup);
		ItemLayoutBinder childLayoutBinder = new ItemLayoutBinder(itemBinder, childLayoutId, childPredefinedPendingAttributesForViewGroup);
		DataSetExpandableListAdapter<?, ?> dataSetExpandableListAdapter = new DataSetExpandableListAdapter(groupValueModel, childGroupedValueModel, groupLayoutBinder, childLayoutBinder,
				bindingContext.shouldPreInitializeViews());

		dataSetExpandableListAdapter.observeChangesOnTheValueModel();
		return dataSetExpandableListAdapter;
	}
}
