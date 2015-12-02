package org.robobinding.supportwidget.recyclerview;

import org.robobinding.BindableView;
import org.robobinding.itempresentationmodel.ItemContext;
import org.robobinding.itempresentationmodel.RefreshableItemPresentationModel;
import org.robobinding.presentationmodel.AbstractPresentationModelObject;
import org.robobinding.property.DataSetValueModel;
import org.robobinding.viewattribute.ViewTag;
import org.robobinding.viewattribute.ViewTags;
import org.robobinding.widget.adapterview.ItemLayoutBinder;
import org.robobinding.widget.adapterview.ItemLayoutSelector;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * 
 * @since 1.0
 * @author Cheng Wei
 */
public class DataSetAdapter extends RecyclerView.Adapter<DataSetAdapter.ViewHolder> {
	private final DataSetValueModel dataSetValueModel;

	private final ItemLayoutBinder itemLayoutBinder;
	private final ItemLayoutSelector layoutSelector;
	private final ViewTags<RefreshableItemPresentationModel> viewTags;

	private final boolean preInitializeViews;

	public DataSetAdapter(DataSetValueModel dataSetValueModel, ItemLayoutBinder itemLayoutBinder, 
			ItemLayoutSelector layoutSelector, ViewTags<RefreshableItemPresentationModel> viewTags, 
			boolean preInitializeViews) {
		this.preInitializeViews = preInitializeViews;
		
		this.dataSetValueModel = dataSetValueModel;
		this.itemLayoutBinder = itemLayoutBinder;
		this.layoutSelector = layoutSelector;
		this.viewTags = viewTags;
	}

	@Override
	public int getItemCount() {
		return dataSetValueModel.size();
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		BindableView bindableView;
		int layoutId = layoutSelector.selectLayout(viewType);
		bindableView = itemLayoutBinder.inflate(parent, layoutId);
		
		View view = bindableView.getRootView();
		RefreshableItemPresentationModel itemPresentationModel = 
				dataSetValueModel.newRefreshableItemPresentationModel(viewType);

		ViewTag<RefreshableItemPresentationModel> viewTag = viewTags.tagFor(view);
		viewTag.set(itemPresentationModel);
		return new ViewHolder(view, bindableView, (AbstractPresentationModelObject)itemPresentationModel);
	}

	@Override
	public void onBindViewHolder(ViewHolder viewHolder, int position) {
		View view = viewHolder.itemView;
		ViewTag<RefreshableItemPresentationModel> viewTag = viewTags.tagFor(view);
		RefreshableItemPresentationModel itemPresentationModel = viewTag.get();
		itemPresentationModel.updateData(getItem(position), new ItemContext(view, position));

		if(viewHolder.isNewView()) {
			viewHolder.bindView();
		} else {
			refreshIfRequired(itemPresentationModel);
		}
	}
	
	private void refreshIfRequired(RefreshableItemPresentationModel itemPresentationModel) {
		if(preInitializeViews) {
			itemPresentationModel.refresh();
		}
	}
	
	private Object getItem(int position) {
		return dataSetValueModel.get(position);
	}

	@Override
	public int getItemViewType(int position) {
		return layoutSelector.getItemViewType(getItem(position), position);
	}
	
	public static class ViewHolder extends RecyclerView.ViewHolder {
		private final BindableView bindableView;
		private final AbstractPresentationModelObject itemPresentationModel;
		private boolean notBound;
		public ViewHolder(View view, BindableView bindableView, AbstractPresentationModelObject itemPresentationModel) {
			super(view);
			
			this.bindableView = bindableView;
			this.itemPresentationModel = itemPresentationModel;
			this.notBound = true;
		}
		
		private boolean isNewView() {
			return notBound;
		}
		
		private void bindView() {
			if(notBound) {
				bindableView.bindTo(itemPresentationModel);
				notBound = false;
			}
		}
	}
}