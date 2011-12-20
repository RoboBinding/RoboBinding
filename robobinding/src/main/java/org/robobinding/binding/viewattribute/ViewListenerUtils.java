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
package org.robobinding.binding.viewattribute;

import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class ViewListenerUtils
{
	private ViewListenerUtils()
	{
	}
	
	public static void addOnFocusChangeListener(View view, OnFocusChangeListener listener)
	{
		OnFocusChangeListener existingListener = view.getOnFocusChangeListener();
		if(existingListener == null)
		{
			view.setOnFocusChangeListener(listener);
		}else
		{
			OnFocusChangeListeners listeners = OnFocusChangeListeners.convert(existingListener);
			listeners.addListener(listener);
			view.setOnFocusChangeListener(listeners);
		}
	}

	public static void addOnItemSelectedListener(AdapterView<?> view, OnItemSelectedListener listener)
	{
		OnItemSelectedListener existingListener = view.getOnItemSelectedListener();
		if (existingListener == null)
		{
			view.setOnItemSelectedListener(listener);
		}else
		{
			OnItemSelectedListeners listeners = OnItemSelectedListeners.convert(existingListener);
			listeners.addListener(listener);
			view.setOnItemSelectedListener(listeners);
		}
	}
}
