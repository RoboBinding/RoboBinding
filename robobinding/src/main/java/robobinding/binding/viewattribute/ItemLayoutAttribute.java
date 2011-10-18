package robobinding.binding.viewattribute;

import java.util.List;

import robobinding.presentationmodel.DataSetAdapter;
import robobinding.value.ValueModel;

public class ItemLayoutAttribute<T> extends AbstractPropertyViewAttribute<Integer>
{
	private DataSetAdapter<List<T>, T> dataSetAdapter;

	public ItemLayoutAttribute(String attributeValue)
	{
		super(attributeValue);
	}

	@Override
	protected void initializeView(ValueModel<Integer> valueModel)
	{
		dataSetAdapter.setItemLayoutId(valueModel.getValue());
	}

	@Override
	protected void observeChangesOnTheValueModel(ValueModel<Integer> valueModel)
	{
		dataSetAdapter.setItemLayoutId(valueModel.getValue());
		dataSetAdapter.notifyDataSetChanged();
	}

	@Override
	protected void observeChangesOnTheView(ValueModel<Integer> valueModel)
	{
		throw new RuntimeException();
	}

	void setDataSetAdapter(DataSetAdapter<List<T>, T> dataSetAdapter)
	{
		this.dataSetAdapter = dataSetAdapter;
	}

}