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
package org.robobinding.viewattribute;

import org.robobinding.attribute.GroupedAttributeDescriptor;

import android.view.View;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class GroupedViewAttributeConfig<T extends View> extends AbstractViewAttributeConfig<T>
{
	private GroupedAttributeDescriptor descriptor;
	private ViewListenersProvider viewListenersProvider;

	public GroupedViewAttributeConfig(T view, GroupedAttributeDescriptor descriptor, ViewListenersProvider viewListenersProvider)
	{
		super(view);
		this.descriptor = descriptor;
		this.viewListenersProvider = viewListenersProvider;

		performValidate();
	}

	@Override
	protected void doValidate(ViewAttributeValidation validation)
	{
		super.doValidate(validation);
		validation.addErrorIfGroupedAttributeDescriptorNotSet(descriptor);
		validation.addErrorIfViewListenersProviderNotSet(viewListenersProvider);
	}

	public GroupedAttributeDescriptor getDescriptor()
	{
		return descriptor;
	}

	public ViewListenersProvider getViewListenersProvider()
	{
		return viewListenersProvider;
	}
}
