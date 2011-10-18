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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import robobinding.presentationmodel.DataSetAdapter;
import robobinding.presentationmodel.ListValueModel;
import robobinding.value.ValueModel;
import android.widget.BaseAdapter;
import android.widget.ListView;

/**
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 *
 */
public class ListItemSourceAttribute extends AbstractPropertyViewAttribute<ListValueModel>
{
	
	
	private ListView listView;
	
	public ListItemSourceAttribute(ListView listView)
	{
		this.listView = listView;
	}
	
	@Override
	protected void initializeView(ListValueModel listValueModel)
	{
		DataSetAdapter<List<?>> dataSetAdapter = new DataSetAdapter<List<?>>(listValueModel);
		listView.setAdapter(dataSetAdapter);
	}

	@Override
	protected void observeChangesOnTheValueModel(ValueModel valueModel)
	{
		dataSetAdapter.listenTo(valueModel);
	}

	@Override
	protected void observeChangesOnTheView(ValueModel valueModel)
	{
		throw new RuntimeException();
	}

}
