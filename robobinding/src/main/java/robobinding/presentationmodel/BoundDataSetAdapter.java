package robobinding.presentationmodel;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import robobinding.binding.Binder;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class BoundDataSetAdapter<ItemType> extends BaseAdapter
{
	private AbstractDataSetValueModel<?, ItemType> dataSetValueModel;
	private int itemLayoutId;
	private int dropdownLayoutId;
	private final Context context;
	private Binder binder;

	public BoundDataSetAdapter(AbstractDataSetValueModel<?, ItemType> dataSetValueModel, int itemLayoutId, Context context)
	{
		this.dataSetValueModel = dataSetValueModel;
		this.itemLayoutId = itemLayoutId;
		this.context = context;
		
		observeChangesOnTheValueModel();
		
		binder = new Binder();
	}
	
	private void observeChangesOnTheValueModel()
	{
		dataSetValueModel.addValueChangeListener(new PropertyChangeListener() {
			
			@Override
			public void propertyChange(PropertyChangeEvent evt)
			{
				notifyDataSetChanged();
			}
		});
	}

	public void setValueModel(AbstractDataSetValueModel<?, ItemType> valueModel)
	{
		this.dataSetValueModel = valueModel;
	}
	
	public void setItemLayoutId(int itemLayoutId)
	{
		this.itemLayoutId = itemLayoutId;
	}
	
	public void setDropdownLayoutId(int dropdownLayoutId)
	{
		this.dropdownLayoutId = dropdownLayoutId;
	}
	
	@Override
	public int getCount()
	{
		return dataSetValueModel.size();
	}

	@Override
	public ItemType getItem(int position)
	{
		return dataSetValueModel.getItem(position);
	}

	@Override
	public long getItemId(int position)
	{
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		return createViewFromResource(position, convertView, parent, itemLayoutId);
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent)
	{
		return createViewFromResource(position, convertView, parent, dropdownLayoutId);
	}

	private View createViewFromResource(int position, View convertView, ViewGroup parent, int layoutId)
	{
		View view;

		if (convertView == null)
		{
			view = newView(position, parent, layoutId);
		} 
		else
		{
			view = convertView;
		}

		updateItemPresentationModel(view, position);

		return view;
	}

	private void updateItemPresentationModel(View view, int position)
	{
		@SuppressWarnings("unchecked")
		ItemPresentationModel<ItemType> itemPresentationModel = (ItemPresentationModel<ItemType>)view.getTag();
		dataSetValueModel.updateItemPresentationModel(itemPresentationModel, position);
	}

	private View newView(int position, ViewGroup parent, int layoutId)
	{
		ItemPresentationModel<ItemType> itemPresentationModel = dataSetValueModel.newItemPresentationModel();
		View view = binder.inflateAndBindView(context, layoutId, itemPresentationModel);
		view.setTag(itemPresentationModel);
		return view;
	}
}