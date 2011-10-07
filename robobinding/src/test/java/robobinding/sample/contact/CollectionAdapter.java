package robobinding.sample.contact;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class CollectionAdapter<R extends RowObservableBean<B>, B> extends BaseAdapter
{
	private CollectionValueModel<R, B> valueModel;
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

		bindView(view, position);

		return view;
	}

	private void bindView(View view, int position)
	{
		R rowPresentationModel = getAttachedPresentationModel(view);
		B bean = valueModel.getBean(position);
		rowPresentationModel.setData(bean);
	}

	private View newView(int position, ViewGroup parent, int layoutId)
	{
		View view = inflateView(layoutId, parent, false);
		R rowPresentationModel = valueModel.newRowPresentationModel();
		attachPresentationModel(view, rowPresentationModel);
		return view;
	}

	private View inflateView(int layoutId, ViewGroup parent, boolean b)
	{
		// TODO Auto-generated method stub
		return null;
	}

	private R getAttachedPresentationModel(View view)
	{
		// TODO Auto-generated method stub
		return null;
	}

	private void attachPresentationModel(View view, R rowPresentationModel)
	{
		// TODO Auto-generated method stub
		
	}
}