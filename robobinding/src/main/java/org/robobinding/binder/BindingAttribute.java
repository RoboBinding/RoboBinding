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

import java.util.List;

import org.robobinding.internal.com_google_common.collect.Lists;
import org.robobinding.presentationmodel.PresentationModelAdapter;
import org.robobinding.viewattribute.ViewAttribute;


import android.content.Context;


/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class BindingAttribute
{
	private final List<String> attributeNames;
	private final ViewAttribute viewAttribute;
	
	public BindingAttribute(List<String> attributeNames, ViewAttribute viewAttribute)
	{
		this.attributeNames = attributeNames;
		this.viewAttribute = viewAttribute;
	}
	
	public BindingAttribute(String attributeName, ViewAttribute viewAttribute)
	{
		this(Lists.newArrayList(attributeName), viewAttribute);
	}
	
	public List<String> getAttributeNames()
	{
		return attributeNames;
	}
	
	public ViewAttribute getViewAttribute()
	{
		return viewAttribute;
	}
	
	public void bind(PresentationModelAdapter presentationModelAdapter, Context context)
	{
		viewAttribute.bind(presentationModelAdapter, context);
	}
}
