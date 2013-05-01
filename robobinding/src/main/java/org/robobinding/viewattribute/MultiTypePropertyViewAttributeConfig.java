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

import org.robobinding.attribute.ValueModelAttribute;

import android.view.View;

import com.google.common.base.Objects;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class MultiTypePropertyViewAttributeConfig<T extends View> extends PropertyViewAttributeConfig<T>
{
	private ViewListenersInjector viewListenersInjector;
	public MultiTypePropertyViewAttributeConfig(T view, ValueModelAttribute attribute, ViewListenersInjector viewListenersInjector)
	{
		super(view, attribute);
		this.viewListenersInjector = viewListenersInjector;
		
	}

	public ViewListenersInjector getViewListenersInjector()
	{
		return viewListenersInjector;
	}
	
	@Override
	public boolean equals(Object other)
	{
		if (this == other)
			return true;
		if (!(other instanceof MultiTypePropertyViewAttributeConfig))
			return false;
	
		@SuppressWarnings({ "unchecked", "rawtypes" })
		final MultiTypePropertyViewAttributeConfig<T> that = (MultiTypePropertyViewAttributeConfig) other;
		return super.equals(that) && Objects.equal(viewListenersInjector, that.viewListenersInjector);
	}

	@Override
	public int hashCode()
	{
		return super.hashCode() + Objects.hashCode(viewListenersInjector);
	}
}
