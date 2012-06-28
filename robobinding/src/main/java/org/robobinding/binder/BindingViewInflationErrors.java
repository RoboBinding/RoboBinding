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
package org.robobinding.binder;

import java.util.Map;

import org.apache.commons.lang3.text.StrBuilder;
import org.robobinding.ViewResolutionError;

import android.view.View;

import com.google.common.collect.Maps;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@SuppressWarnings("serial")
public class BindingViewInflationErrors extends RuntimeException
{
	private Map<View, BindingViewInflationError> errorMap;
	private String errorMessage;
	
	public BindingViewInflationErrors()
	{
		errorMap = Maps.newLinkedHashMap();
	}

	public void addViewResolutionError(ViewResolutionError error)
	{
		errorMap.put(error.getView(), new BindingViewInflationError(error));
	}

	public void addViewBindingError(ViewBindingError error)
	{
		BindingViewInflationError inflationError = errorMap.get(error.getView());
		inflationError.setViewBindingError(error);
	}
	
	public void assertNoErrors(ErrorFormatter errorFormatter)
	{
		StrBuilder sb = new StrBuilder();
		for(BindingViewInflationError error : errorMap.values())
		{
			if(error.hasErrors())
			{
				sb.appendln(errorFormatter.format(error));
			}
		}
		
		if(!sb.isEmpty())
		{
			errorMessage = sb.toString();
			throw this;
		}
	}
	
	@Override
	public String getMessage()
	{
		return errorMessage;
	}
	
	public interface ErrorFormatter
	{

		String format(BindingViewInflationError error);
		
	}

}
