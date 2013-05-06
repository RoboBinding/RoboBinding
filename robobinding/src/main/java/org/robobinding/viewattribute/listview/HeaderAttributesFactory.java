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
package org.robobinding.viewattribute.listview;

import org.robobinding.viewattribute.ViewAttributeFactory;
import org.robobinding.viewattribute.adapterview.SubViewAttributes;

import android.widget.ListView;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class HeaderAttributesFactory implements ViewAttributeFactory<SubViewAttributes<ListView>>
{
	@Override
	public SubViewAttributes<ListView> create()
	{
		return new SubViewAttributes<ListView>(new HeaderAttributes());
	}
}
