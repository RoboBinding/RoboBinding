package robobinding.presentationmodel;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;

public class DataSetAdapter<T> extends BaseAdapter
{
	private AbstractDataSetValueModel<T> valueModel;
	private int layoutId;
	private int dropDownLayoutId;

	@Override
	public int getCount()
	{
		return valueModel.size();
	}

	@Override
	public Object getItem(int position)
	{
		return valueModel.getBean(position);
	}

	@Override
	public long getItemId(int position)
	{
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		return createViewFromResource(position, convertView, parent, layoutId);
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent)
	{
		return createViewFromResource(position, convertView, parent, dropDownLayoutId);
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
		RowPresentationModel<T> rowPresentationModel = binder.getRowPresentationModel(view);
		valueModel.updateRowPresentationModel(rowPresentationModel, position);
	}

	private View newView(int position, ViewGroup parent, int layoutId)
	{
		RowPresentationModel<T> rowPresentationModel = null;
		View view = binder.inflateAndBindView(layoutId, parent, false, rowPresentationModel);
		return view;
	}
}