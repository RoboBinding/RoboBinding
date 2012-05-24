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
package org.robobinding.attribute;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public abstract class AbstractAttribute
{
	private final String name;

	public AbstractAttribute(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return name;
	}
	

	@Override
	public boolean equals(Object other)
	{
		if (this == other)
			return true;
		if (!(other instanceof PlainAttribute))
			return false;
	
		final PlainAttribute that = (PlainAttribute) other;
		return new EqualsBuilder()
			.append(getName(), that.getName())
			.append(value, that.value)
			.isEquals();
	}

	@Override
	public int hashCode()
	{
		return new HashCodeBuilder()
			.append(getName())
			.append(value)
			.toHashCode();
	}
}
