/**
 * Copyright 2012 Cheng Wei, Robert Taylor
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
package org.robobinding.binder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
class ViewInflator
{
	private LayoutInflater layoutInflater;
	private ViewGroup parentViewToAttach;
	
	public ViewInflator(LayoutInflater layoutInflater, ViewGroup parentViewToAttach)
	{
		this.layoutInflater = layoutInflater;
		this.parentViewToAttach = parentViewToAttach;
	}
	
	public ViewInflator(Context context, ViewGroup parentViewToAttach)
	{
		this(LayoutInflater.from(context).cloneInContext(context),
				parentViewToAttach);
	}
	
	public View inflateView(int layoutId)
	{
		if(shouldAttachToParentView())
		{
			return layoutInflater.inflate(layoutId, parentViewToAttach, true);
		}else
		{
			return layoutInflater.inflate(layoutId, null);
		}
	}

	private boolean shouldAttachToParentView()
	{
		return parentViewToAttach != null;
	}

}
