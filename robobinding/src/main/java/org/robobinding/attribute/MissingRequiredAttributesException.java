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

import java.util.Collection;
import java.util.Collections;

import com.google.common.base.Joiner;



/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
@SuppressWarnings("serial")
public class MissingRequiredAttributesException extends RuntimeException
{
	private final Collection<String> missingAttributes;
	
	public MissingRequiredAttributesException(Collection<String> missingAttributes)
	{
		this.missingAttributes = missingAttributes;
	}
	
	public Collection<String> getMissingAttributes()
	{
		return Collections.unmodifiableCollection(missingAttributes);
	}
	
	@Override
	public String getMessage()
	{
		return "Missing attributes: "+Joiner.on(", ").join(missingAttributes);
	}
	
	@Override
	public String toString()
	{
		return getMessage();
	}
}
