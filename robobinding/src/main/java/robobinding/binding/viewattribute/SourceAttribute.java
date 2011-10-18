/**
 * Copyright 2011 Cheng Wei, Robert Taylor
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions
 * and limitations under the License.
 */
package robobinding.binding.viewattribute;

import java.util.List;

import robobinding.presentationmodel.DataSetAdapter;
import robobinding.presentationmodel.ListValueModel;
import robobinding.value.ValueModel;

/**
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * 
 */
public class SourceAttribute<T> extends AbstractPropertyViewAttribute<List<T>>
{
	private DataSetAdapter<List<T>, T> dataSetAdapter;

	public SourceAttribute(String attributeValue)
	{
		super(attributeValue);
	}

	protected void initializeView(ValueModel<List<T>> valueModel)
	{
		ListValueModel<T> listValueModel = (ListValueModel<T>) valueModel;
		dataSetAdapter.setValueModel(listValueModel);
	}

	@Override
	protected void observeChangesOnTheValueModel(ValueModel<List<T>> valueModel)
	{
		ListValueModel<T> listValueModel = (ListValueModel<T>) valueModel;
		dataSetAdapter.setValueModel(listValueModel);
		dataSetAdapter.notifyDataSetChanged();
	}

	@Override
	protected void observeChangesOnTheView(ValueModel<List<T>> valueModel)
	{
		throw new RuntimeException();
	}

	void setDataSetAdapter(DataSetAdapter<List<T>, T> dataSetAdapter)
	{
		this.dataSetAdapter = dataSetAdapter;
	}
}
