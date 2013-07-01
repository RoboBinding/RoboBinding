/**
 * Copyright 2013 Cheng Wei, Robert Taylor
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
package org.robobinding.viewattribute.adapterview;

import org.robobinding.attribute.AbstractPropertyAttribute;
import org.robobinding.viewattribute.ChildViewAttribute;

import android.widget.AdapterView;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class ItemLayoutAttributeFactory extends RowLayoutAttributeFactory
{
	private ItemLayoutAttributeFactory(AdapterView<?> adapterView, DataSetAdapter<?> dataSetAdapter)
	{
		super(adapterView, dataSetAdapter);
	}

	@Override
	protected RowLayoutUpdater createRowLayoutUpdater(DataSetAdapter<?> dataSetAdapter) 
	{
		return new ItemLayoutUpdater(dataSetAdapter);
	}
	
	public static ChildViewAttribute<AbstractPropertyAttribute> create(AdapterView<?> adapterView, DataSetAdapter<?> dataSetAdapter)
	{
		return new RowLayoutAttributeAdapter(new ItemLayoutAttributeFactory(adapterView, dataSetAdapter));
	}
}
