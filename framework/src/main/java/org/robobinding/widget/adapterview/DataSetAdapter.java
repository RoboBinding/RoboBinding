package org.robobinding.widget.adapterview;

import org.robobinding.BindableView;
import org.robobinding.itempresentationmodel.ItemContext;
import org.robobinding.itempresentationmodel.RefreshableItemPresentationModel;
import org.robobinding.presentationmodel.AbstractPresentationModelObject;
import org.robobinding.property.DataSetValueModel;
import org.robobinding.property.DataSetValueModelWrapper;
import org.robobinding.property.PropertyChangeListener;
import org.robobinding.viewattribute.ViewTag;
import org.robobinding.viewattribute.ViewTags;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * 
 * @since 1.0
 * @author Cheng Wei
 * @author Robert Taylor
 */
public class DataSetAdapter<T> extends BaseAdapter {
	private enum ViewType {
		ITEM_LAYOUT, DROPDOWN_LAYOUT
	}

	private final DataSetValueModel<T> dataSetValueModel;

	private final ItemLayoutBinder itemLayoutBinder;
	private final ItemLayoutBinder dropdownLayoutBinder;
	private final ViewTags<RefreshableItemPresentationModel> viewTags;

	private final boolean preInitializeViews;

	private boolean propertyChangeEventOccurred;

	public DataSetAdapter(DataSetValueModel<T> dataSetValueModel, ItemLayoutBinder itemLayoutBinder, 
			ItemLayoutBinder dropdownLayoutBinder, ViewTags<RefreshableItemPresentationModel> viewTags, 
			boolean preInitializeViews) {
		this.preInitializeViews = preInitializeViews;
		
		this.dataSetValueModel = createValueModelFrom(dataSetValueModel);
		this.itemLayoutBinder = itemLayoutBinder;
		this.dropdownLayoutBinder = dropdownLayoutBinder;
		this.viewTags = viewTags;

		propertyChangeEventOccurred = false;
	}

	private DataSetValueModel<T> createValueModelFrom(DataSetValueModel<T> valueModel) {
		if (!preInitializeViews) {
			return wrapAsZeroSizeDataSetUntilPropertyChangeEvent(valueModel);
		} else {
			return valueModel;
		}
	}

	public void observeChangesOnTheValueModel() {
		dataSetValueModel.addPropertyChangeListener(new PropertyChangeListener() {
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

	@Override
	public int getCount() {
		if (dataSetValueModel == null)
			return 0;

		return dataSetValueModel.size();
	}

	@Override
	public T getItem(int position) {
		return dataSetValueModel.getItem(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public int getViewTypeCount() {
		return dataSetValueModel.getItemViewTypeCount();
	}

	@Override
	public int getItemViewType(int position) {
		T item = getItem(position);
		return dataSetValueModel.getItemViewType(position, item);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return createViewFromResource(position, convertView, parent, ViewType.ITEM_LAYOUT);
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		return createViewFromResource(position, convertView, parent, ViewType.DROPDOWN_LAYOUT);
	}

	private View createViewFromResource(int position, View convertView, ViewGroup parent, ViewType viewType) {
		if (convertView == null) {
			return newView(position, parent, viewType);
		} else {
			updateItemPresentationModel(convertView, position);
			return convertView;
		}
	}

	private View newView(int position, ViewGroup parent, ViewType viewType) {
		T item = getItem(position);
		RefreshableItemPresentationModel itemPresentationModel = dataSetValueModel.newRefreshableItemPresentationModel(item);

		BindableView bindableView;
		if (viewType == ViewType.ITEM_LAYOUT) {
			int layoutId = dataSetValueModel.getItemLayoutId(position, item);
			bindableView = itemLayoutBinder.inflate(parent, layoutId);
		} else {
			bindableView = dropdownLayoutBinder.inflate(parent);
		}
		
		View view = bindableView.getRootView();
		itemPresentationModel.updateData(getItem(position), new ItemContext(view, position));
		bindableView.bindTo((AbstractPresentationModelObject)itemPresentationModel);

		ViewTag<RefreshableItemPresentationModel> viewTag = viewTags.tagFor(view);
		viewTag.set(itemPresentationModel);
		return view;
	}

	private void updateItemPresentationModel(View view, int position) {
		ViewTag<RefreshableItemPresentationModel> viewTag = viewTags.tagFor(view);
		RefreshableItemPresentationModel itemPresentationModel = viewTag.get();
		itemPresentationModel.updateData(getItem(position), new ItemContext(view, position));
		refreshIfRequired(itemPresentationModel);
	}
	
	private void refreshIfRequired(RefreshableItemPresentationModel itemPresentationModel) {
		if(preInitializeViews) {
			itemPresentationModel.refresh();
		}
	}
}