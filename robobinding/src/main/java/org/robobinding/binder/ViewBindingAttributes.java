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
package org.robobinding.binder;

import java.util.Collection;
import java.util.List;

import org.robobinding.BindingContext;
import org.robobinding.viewattribute.AttributeBindingException;
import org.robobinding.viewattribute.AttributeGroupBindingException;
import org.robobinding.viewattribute.ViewAttribute;

import android.view.View;

import com.google.common.collect.Lists;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
public class ViewBindingAttributes
{
	private View view;
	private final List<ViewAttribute> viewAttributes;
	
	ViewBindingAttributes(View view)
	{
		this.view = view;
		this.viewAttributes = Lists.newArrayList();
	}
	
	void addResolvedViewAttributes(Collection<ViewAttribute> viewAttributes)
	{
		this.viewAttributes.addAll(viewAttributes);
	}
	
	public void bindTo(BindingContext bindingContext)
	{
		ViewBindingException viewBindingErrors = new ViewBindingException(view);
		for (ViewAttribute viewAttribute : viewAttributes)
		{
			try
			{
				viewAttribute.bindTo(bindingContext);
			}catch(AttributeBindingException e)
			{
				viewBindingErrors.addAttributeError(e);
			}catch (AttributeGroupBindingException e) 
			{
				viewBindingErrors.addAttributeGroupError(e);
			}
		}
		viewBindingErrors.assertNoErrors();
	}
}