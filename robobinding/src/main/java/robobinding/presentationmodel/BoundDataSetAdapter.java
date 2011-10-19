package robobinding.presentationmodel;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;

public class BoundDataSetAdapter<ItemType> extends BaseAdapter
{
	private AbstractDataSetValueModel<?, ItemType> dataSetValueModel;
	private int itemLayoutId;
	private int dropdownLayoutId;

	public BoundDataSetAdapter(AbstractDataSetValueModel<?, ItemType> dataSetValueModel, int itemLayoutId)
	{
		this.dataSetValueModel = dataSetValueModel;
		this.itemLayoutId = itemLayoutId;
		
		observeChangesOnTheValueModel();
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
	public Object getItem(int position)
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
		} else
		{
			view = convertView;
		}

		updateRowPresentationModel(view, position);

		return view;
	}

	private void updateRowPresentationModel(View view, int position)
	{
		ItemPresentationModel<ItemType> itemPresentationModel = binder.getItemPresentationModel(view);
		dataSetValueModel.updateItemPresentationModel(itemPresentationModel, position);
	}

	private View newView(int position, ViewGroup parent, int layoutId)
	{
		ItemPresentationModel<ItemType> rowPresentationModel = null;
		View view = binder.inflateAndBindView(layoutId, parent, false, rowPresentationModel);
		return view;
	}

}