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

import org.apache.commons.lang3.Validate;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class ViewAttributeValidation
{
	private StringBuilder errorMessages;

	public ViewAttributeValidation()
	{
		errorMessages = new StringBuilder();
	}

	public void notNull(Object obj, String errorMessage)
	{
		if(obj == null)
		{
			addError(errorMessage);
		}
	}
	
	public void addError(String errorMessage)
	{
		Validate.notBlank(errorMessage);
		errorMessages.append(errorMessage);
	}

	public boolean hasErrors()
	{
		return errorMessages.length() > 0;
	}

	public String getErrorMessages()
	{
		return errorMessages.toString();
	}
}
