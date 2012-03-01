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

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.robobinding.viewattribute.view.ViewListeners;

import android.view.View;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class ViewAttributeValidation
{
	static final String ERROR_MESSAGE_SEPARATOR = "; ";
	
	StringBuilder errorMessages;

	ViewAttributeValidation()
	{
	}

	void addErrorIfViewNotSet(View view)
	{
		notNull(view, "View not set");
	}

	void addErrorIfPropertyAttributeValueNotSet(PropertyBindingDetails propertyBindingDetails)
	{
		notNull(propertyBindingDetails, "Attribute value not set");
	}

	public void addErrorIfViewListenersNotSet(ViewListeners viewListeners)
	{
		notNull(viewListeners, "ViewListeners not set");
	}

	void notNull(Object obj, String errorMessage)
	{
		if (obj == null)
		{
			addError(errorMessage);
		}
	}

	void addErrorIfCommandNameNotSet(String commandName)
	{
		notBlank(commandName, "Command name not set");
	}

	void notBlank(String str, String errorMessage)
	{
		if(StringUtils.isBlank(str))
		{
			addError(errorMessage);
		}
	}
	
	public void addError(String errorMessage)
	{
		Validate.notBlank(errorMessage);
		
		if(hasErrors())
		{
			errorMessages.append(ERROR_MESSAGE_SEPARATOR);
		}else
		{
			errorMessages = new StringBuilder();
		}
		errorMessages.append(errorMessage);
	}

	boolean hasErrors()
	{
		return errorMessages != null;
	}
	
	void assertNoErrors()
	{
		if(hasErrors())
		{
			throw new IllegalStateException(errorMessages.toString());
		}
	}
}
