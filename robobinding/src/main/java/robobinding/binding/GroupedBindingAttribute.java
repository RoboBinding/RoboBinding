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
package robobinding.binding;

import java.util.List;

import robobinding.beans.PresentationModelAdapter;
import robobinding.binding.viewattribute.ListViewAttributes;
import android.content.Context;

/**
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 *
 */
public class GroupedBindingAttribute extends BindingAttribute
{
	private final ListViewAttributes listViewAttributes;

	public GroupedBindingAttribute(List<String> groupedAttributeNames, ListViewAttributes listViewAttributes)
	{
		super(groupedAttributeNames.get(0));
		this.listViewAttributes = listViewAttributes;
	}

	@Override
	public void bind(PresentationModelAdapter presentationModelAdapter, Context context)
	{
		listViewAttributes.bind(presentationModelAdapter, context);
	}
}
