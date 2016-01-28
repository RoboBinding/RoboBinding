package org.robobinding.widget.adapterview;

import org.robobinding.BindableView;
import org.robobinding.annotation.DoNotPreinitialize;
import org.robobinding.itempresentationmodel.ItemContext;
import org.robobinding.itempresentationmodel.RefreshableItemPresentationModel;
import org.robobinding.presentationmodel.AbstractPresentationModelObject;
import org.robobinding.property.DataSetValueModel;
import org.robobinding.viewattribute.ViewTag;
import org.robobinding.viewattribute.ViewTags;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * @author Cheng Wei
 * @author Robert Taylor
 * @since 1.0
 */
public class DataSetAdapter extends BaseAdapter {
    private enum DisplayType {
        ITEM_LAYOUT, DROPDOWN_LAYOUT
    }

    private final DataSetValueModel dataSetValueModel;

    private final ItemLayoutBinder itemLayoutBinder;
    private final ItemLayoutBinder dropdownLayoutBinder;
    private final ItemLayoutSelector itemLayoutSelector;
    private final int dropdownLayoutId;
    private final ViewTags<RefreshableItemPresentationModel> viewTags;

    public DataSetAdapter(DataSetValueModel dataSetValueModel,
                          ItemLayoutBinder itemLayoutBinder, ItemLayoutBinder dropdownLayoutBinder,
                          ItemLayoutSelector itemLayoutSelector, int dropdownLayoutId,
                          ViewTags<RefreshableItemPresentationModel> viewTags) {

        this.dataSetValueModel = dataSetValueModel;
        this.itemLayoutBinder = itemLayoutBinder;
        this.dropdownLayoutBinder = dropdownLayoutBinder;
        this.itemLayoutSelector = itemLayoutSelector;
        this.dropdownLayoutId = dropdownLayoutId;
        this.viewTags = viewTags;
    }

    @Override
    public int getCount() {
        return dataSetValueModel.size();
    }

    @Override
    public Object getItem(int position) {
        return dataSetValueModel.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return createViewFromResource(position, convertView, parent, DisplayType.ITEM_LAYOUT);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return createViewFromResource(position, convertView, parent, DisplayType.DROPDOWN_LAYOUT);
    }

    private View createViewFromResource(int position, View convertView, ViewGroup parent, DisplayType displayType) {
        if (convertView == null) {
            return newView(position, parent, displayType);
        } else {
            updateItemPresentationModel(convertView, position);
            return convertView;
        }
    }

    private View newView(int position, ViewGroup parent, DisplayType displayType) {
        BindableView bindableView;
        Object item = getItem(position);
        if (displayType == DisplayType.ITEM_LAYOUT) {
            int layoutId = itemLayoutSelector.selectLayout(getItemViewType(position));
            bindableView = itemLayoutBinder.inflate(parent, layoutId);
        } else {
            bindableView = dropdownLayoutBinder.inflate(parent, dropdownLayoutId);
        }

        View view = bindableView.getRootView();
        RefreshableItemPresentationModel itemPresentationModel = dataSetValueModel.newRefreshableItemPresentationModel(
                getItemViewType(position));
        bindableView.bindTo((AbstractPresentationModelObject) itemPresentationModel);
        itemPresentationModel.updateData(item, new ItemContext(view, position));
        refreshIfRequired(itemPresentationModel);

        ViewTag<RefreshableItemPresentationModel> viewTag = viewTags.tagFor(view);
        viewTag.set(itemPresentationModel);
        return view;
    }

    private boolean isPreinitializable(RefreshableItemPresentationModel itemPresentationModel) {
        final Class<?> presentationModelClass = ((AbstractPresentationModelObject) itemPresentationModel).getPresentationModelClass();
        return !presentationModelClass.isAnnotationPresent(DoNotPreinitialize.class);
    }

    private void updateItemPresentationModel(View view, int position) {
        ViewTag<RefreshableItemPresentationModel> viewTag = viewTags.tagFor(view);
        RefreshableItemPresentationModel itemPresentationModel = viewTag.get();
        itemPresentationModel.updateData(getItem(position), new ItemContext(view, position));
        refreshIfRequired(itemPresentationModel);
    }

    private void refreshIfRequired(RefreshableItemPresentationModel itemPresentationModel) {
        if (isPreinitializable(itemPresentationModel)) {
            itemPresentationModel.refresh();
        }
    }

    @Override
    public int getViewTypeCount() {
        return itemLayoutSelector.getViewTypeCount();
    }

    @Override
    public int getItemViewType(int position) {
        return itemLayoutSelector.getItemViewType(getItem(position), position);
    }
}