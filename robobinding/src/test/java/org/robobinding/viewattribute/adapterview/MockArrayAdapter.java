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
package org.robobinding.viewattribute.adapterview;

import org.robobinding.internal.com_google_common.collect.Lists;

import android.R;
import android.app.Activity;
import android.widget.ArrayAdapter;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class MockArrayAdapter extends ArrayAdapter<String>
{
	public MockArrayAdapter()
	{
		super(new Activity(), R.layout.simple_list_item_1, Lists.newArrayList("0", "1", "2", "3", "4", "5", "6", "7", "8", "9"));
	}
}
