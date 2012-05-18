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
package org.robobinding.viewattribute;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import static org.robobinding.CollectionUtils.*;


/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@SuppressWarnings("serial")
public class AttributeGroupBindingException extends RuntimeException
{
	private List<String> generalErrors;
	private Map<String, String> childAttributeErrors;
	public AttributeGroupBindingException()
	{
		generalErrors = Lists.newArrayList();
		childAttributeErrors = Maps.newHashMap();
	}
	
	void addGeneralError(String errorMessage)
	{
		generalErrors.add(errorMessage);
	}
	
	void addChildAttributeError(String attribute, String errorMessage)
	{
		childAttributeErrors.put(attribute, errorMessage);
	}
	
	void assertNoErrors()
	{
		if(hasErrors())
		{
			throw this;
		}
	}

	private boolean hasErrors()
	{
		return isNotEmpty(generalErrors) || isNotEmpty(childAttributeErrors);
	}
}
