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
package org.robobinding;

import static com.google.common.collect.Lists.newArrayList;

import java.util.Collections;
import java.util.List;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
@SuppressWarnings("serial")
public class GroupedAttributeResolutionException extends RuntimeException
{
	private final List<AttributeResolutionException> resolutionExceptions = newArrayList();
	
	public void add(AttributeResolutionException e)
	{
		resolutionExceptions.add(e);
	}

	public void assertNoErrors()
	{
		if (!resolutionExceptions.isEmpty()) {
			throw this;
		}
	}
	
	public List<AttributeResolutionException> getAttributeResolutionExceptions()
	{
		return Collections.unmodifiableList(resolutionExceptions);
	}

}
