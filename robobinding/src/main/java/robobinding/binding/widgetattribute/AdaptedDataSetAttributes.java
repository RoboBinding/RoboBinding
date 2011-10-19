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
package robobinding.binding.widgetattribute;

import robobinding.beans.PresentationModelAdapter;
import robobinding.binding.ViewAttribute;
import robobinding.presentationmodel.AbstractDataSetValueModel;
import robobinding.presentationmodel.BoundDataSetAdapter;
import android.content.Context;
import android.widget.ListView;

/**
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 *
 */
public class AdaptedDataSetAttributes implements ViewAttribute
{
	private final ListView listView;
	private final String sourceAttributeValue;
	private final String itemLayoutResourceName;
	
	public AdaptedDataSetAttributes(ListView listView, String sourceAttributeValue, String itemLayoutAttributeValue)
	{
		this.listView = listView;
		this.sourceAttributeValue = sourceAttributeValue;
		itemLayoutResourceName = itemLayoutAttributeValue.substring(1);
	}

	@SuppressWarnings({"rawtypes", "unchecked"})
	@Override
	public void bind(PresentationModelAdapter presentationModelAdapter, Context context)
	{
		int itemLayoutId = context.getResources().getIdentifier(itemLayoutResourceName, "layout", context.getPackageName());
		AbstractDataSetValueModel dataSetValueModel = presentationModelAdapter.getDataSetPropertyValueModel(sourceAttributeValue);
		BoundDataSetAdapter dataSetAdapter = new BoundDataSetAdapter(dataSetValueModel, itemLayoutId);
		listView.setAdapter(dataSetAdapter);
	}

}
