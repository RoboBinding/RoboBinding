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

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 *
 */
public class Binder
{

	static class InflationResult
	{
		private View rootView;
		
		public InflationResult(View rootView)
		{
			this.rootView = rootView;
		}

		public View getRootView()
		{
			return rootView;
		}

	}

	public InflationResult inflateView(Context context, int resourceId, ViewGroup rootViewGroup, boolean attachToRoot)
	{
		LayoutInflater layoutInflater = LayoutInflater.from(context).cloneInContext(context);
		return new InflationResult(layoutInflater.inflate(resourceId, rootViewGroup, attachToRoot));
	}

}
