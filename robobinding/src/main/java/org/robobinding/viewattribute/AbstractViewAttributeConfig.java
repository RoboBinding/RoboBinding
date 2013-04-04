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

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import android.view.View;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class AbstractViewAttributeConfig<T extends View>
{
	private T view;

	public AbstractViewAttributeConfig(T view)
	{
		this.view = view;
	}

	public T getView()
	{
		return view;
	}
	
	@Override
	public boolean equals(Object other)
	{
		if (this == other)
			return true;
		if (!(other instanceof AbstractViewAttributeConfig))
			return false;
	
		@SuppressWarnings({ "unchecked", "rawtypes" })
		final AbstractViewAttributeConfig<T> that = (AbstractViewAttributeConfig) other;
		return new EqualsBuilder()
			.append(view, that.view)
			.isEquals();
	}

	@Override
	public int hashCode()
	{
		return new HashCodeBuilder()
			.append(view)
			.toHashCode();
	}

}
