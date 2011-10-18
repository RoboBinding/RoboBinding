package robobinding.presentationmodel;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;

public class DataSetAdapter<DataSetType, ItemType> extends BaseAdapter
{
	private AbstractDataSetValueModel<DataSetType, ItemType> valueModel;
	private int itemLayoutId;
	private int dropdownLayoutId;

	public DataSetAdapter(AbstractDataSetValueModel<DataSetType, ItemType> listValueModel)
	{
		listValueModel.addValueChangeListener(new PropertyChangeListener() {
			
			@Override
			public void propertyChange(PropertyChangeEvent evt)
			{
				notifyDataSetChanged();
			}
		});
	}

	public DataSetAdapter()
	{
	}

	public void setValueModel(AbstractDataSetValueModel<DataSetType, ItemType> valueModel)
	{
		this.valueModel = valueModel;
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
		return valueModel.size();
	}

	@Override
	public Object getItem(int position)
	{
		return valueModel.getItem(position);
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
		valueModel.updateItemPresentationModel(itemPresentationModel, position);
	}

	private View newView(int position, ViewGroup parent, int layoutId)
	{
		ItemPresentationModel<ItemType> rowPresentationModel = null;
		View view = binder.inflateAndBindView(layoutId, parent, false, rowPresentationModel);
		return view;
	}

	

	
}