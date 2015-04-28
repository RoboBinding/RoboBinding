package org.robobinding.widget.expandablelistview;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import org.robobinding.BindableView;
import org.robobinding.groupeditempresentationmodel.GroupedItemContext;
import org.robobinding.groupeditempresentationmodel.RefreshableGroupedItemPresentationModel;
import org.robobinding.itempresentationmodel.ItemContext;
import org.robobinding.itempresentationmodel.RefreshableItemPresentationModel;
import org.robobinding.presentationmodel.AbstractPresentationModelObject;
import org.robobinding.property.*;
import org.robobinding.widget.adapterview.ItemLayoutBinder;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Jihun Lee
 */
public class DataSetExpandableListAdapter<T, E> extends BaseExpandableListAdapter {
	private final DataSetValueModel<T> groupDataSetValueModel;
    private final GroupedDataSetValueModel<E> childGroupedDataSetValueModel;

	private final ItemLayoutBinder groupLayoutBinder;
	private final ItemLayoutBinder childLayoutBinder;

	private final boolean preInitializeViews;

	private boolean propertyChangeEventOccurred;

	public DataSetExpandableListAdapter(
            DataSetValueModel<T> groupDataSetValueModel,
            GroupedDataSetValueModel<E> childGroupedDataSetValueModel,
            ItemLayoutBinder groupLayoutBinder,
										ItemLayoutBinder childLayoutBinder, boolean preInitializeViews) {
		this.preInitializeViews = preInitializeViews;
		
		this.groupDataSetValueModel = createValueModelFrom(groupDataSetValueModel);
        this.childGroupedDataSetValueModel = createGroupedDataSetValueModel(childGroupedDataSetValueModel);
		this.groupLayoutBinder = groupLayoutBinder;
		this.childLayoutBinder = childLayoutBinder;

		propertyChangeEventOccurred = false;
	}

	private DataSetValueModel<T> createValueModelFrom(DataSetValueModel<T> valueModel) {
		if (!preInitializeViews) {
			return wrapAsZeroSizeDataSetUntilPropertyChangeEvent(valueModel);
		} else {
			return valueModel;
		}
	}

    private GroupedDataSetValueModel<E> createGroupedDataSetValueModel(GroupedDataSetValueModel<E> valueModel){
        if(!preInitializeViews){
            return wrapAsZeroSizeGroupedDataSetUntilPropertyChangeEvent(valueModel);
        } else {
            return valueModel;
        }
    }

	public void observeChangesOnTheValueModel() {
		groupDataSetValueModel.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChanged() {
                propertyChangeEventOccurred = true;
                notifyDataSetChanged();
            }
        });
	}

	private DataSetValueModel<T> wrapAsZeroSizeDataSetUntilPropertyChangeEvent(final DataSetValueModel<T> valueModel) {
		return new DataSetValueModelWrapper<T>(valueModel) {
			@Override
			public int size() {
				if (propertyChangeEventOccurred)
					return valueModel.size();

				return 0;
			}
		};
	}

    private GroupedDataSetValueModel<E> wrapAsZeroSizeGroupedDataSetUntilPropertyChangeEvent(
            final GroupedDataSetValueModel<E> valueModel) {
        return new GroupedDataSetValueModelWrapper<E>(valueModel){
            @Override
            public int size() {
                if(propertyChangeEventOccurred)
                    return valueModel.size();

                return 0;
            }

            @Override
            public int sizeOfGroup(int groupPosition) {
                if(propertyChangeEventOccurred)
                    return valueModel.sizeOfGroup(groupPosition);

                return 0;
            }
        };
    }

	@Override
	public int getGroupCount() {
        if (groupDataSetValueModel == null)
            return 0;

        return groupDataSetValueModel.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		if(childGroupedDataSetValueModel == null)
            return 0;

        return childGroupedDataSetValueModel.sizeOfGroup(groupPosition);
	}

	@Override
	public T getGroup(int groupPosition) {
		return groupDataSetValueModel.getItem(groupPosition);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return childGroupedDataSetValueModel.getChild(groupPosition, childPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
        return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if(convertView == null) {
            return newGroupView(groupPosition, isExpanded, parent);
        } else {
            updateGroupPresentationModel(convertView, groupPosition, isExpanded);
            return convertView;
        }
	}

	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
		if(convertView == null) {
            return newChildView(groupPosition, childPosition, isLastChild, parent);
        } else {
            updateChildPresentationModel(convertView, groupPosition, childPosition, isLastChild);
            return convertView;
        }
	}

    @Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return false;
	}

    private View newGroupView(int groupPosition, boolean isExpanded, ViewGroup parent) {
        RefreshableItemPresentationModel groupPresentationModel = groupDataSetValueModel.newRefreshableItemPresentationModel();

        BindableView bindableView = groupLayoutBinder.inflate(parent);

        View view = bindableView.getRootView();
        groupPresentationModel.updateData(getGroup(groupPosition), new ItemContext(view, groupPosition));
        bindableView.bindTo((AbstractPresentationModelObject) groupPresentationModel);

        view.setTag(groupPresentationModel);
        return view;
    }

    private View newChildView(int groupPosition, int childPosition, boolean isLastChild, ViewGroup parent) {
        RefreshableGroupedItemPresentationModel childPresentationModel = childGroupedDataSetValueModel.newRefreshableGroupedItemPresentationModel();

        BindableView bindableView = childLayoutBinder.inflate(parent);

        View view = bindableView.getRootView();
        childPresentationModel.updateData(getChild(groupPosition, childPosition), new GroupedItemContext(view, groupPosition, childPosition));
        bindableView.bindTo((AbstractPresentationModelObject) childPresentationModel);

        view.setTag(childPresentationModel);
        return view;
    }
    
    private void updateGroupPresentationModel(View convertView, int groupPosition, boolean isExpanded) {
        RefreshableItemPresentationModel groupPresentationModel = (RefreshableItemPresentationModel) convertView.getTag();
        groupPresentationModel.updateData(getGroup(groupPosition), new ItemContext(convertView, groupPosition));
        refreshGroupIfRequired(groupPresentationModel);
    }

    private void updateChildPresentationModel(View convertView, int groupPosition, int childPosition, boolean isLastChild) {
        RefreshableGroupedItemPresentationModel childPresentationModel = (RefreshableGroupedItemPresentationModel) convertView.getTag();
        childPresentationModel.updateData(getChild(groupPosition, childPosition), new GroupedItemContext(convertView, groupPosition, childPosition));
        refreshChildIfRequired(childPresentationModel);
    }


    private void refreshGroupIfRequired(RefreshableItemPresentationModel groupPresentationModel) {
        if(preInitializeViews) {
            groupPresentationModel.refresh();
        }
    }

    private void refreshChildIfRequired(RefreshableGroupedItemPresentationModel childPresentationModel) {
        if(preInitializeViews) {
            childPresentationModel.refresh();
        }
    }
}