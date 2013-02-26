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

import java.util.Collection;
import java.util.Map;

import org.robobinding.ViewResolutionErrors;

import android.view.View;

import com.google.common.collect.Maps;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
@SuppressWarnings("serial")
public class ViewHierarchyInflationErrorsException extends RuntimeException
{
	private Map<View, ViewInflationErrors> errorMap;
	private String errorMessage;
	
	ViewHierarchyInflationErrorsException()
	{
		errorMap = Maps.newLinkedHashMap();
	}

	void addViewResolutionError(ViewResolutionErrors error)
	{
		errorMap.put(error.getView(), new ViewInflationErrors(error));
	}

	void addViewBindingError(ViewBindingErrors error)
	{
		try
		{
		ViewInflationErrors inflationError = errorMap.get(error.getView());
		inflationError.setBindingErrors(error);
		}catch(NullPointerException e)
		{
			throw e;
		}
	}
	
	void assertNoErrors(ErrorFormatter errorFormatter)
	{
		StringBuilder sb = new StringBuilder();
		for(ViewInflationErrors error : errorMap.values())
		{
			if(error.hasErrors())
			{
				appendln(sb, errorFormatter.format(error));
			}
		}
		
		if(sb.length() != 0)
		{
			errorMessage = sb.toString();
			throw this;
		}
	}
	
	private static void appendln(StringBuilder sb, String str)
	{
		sb.append(str);
		sb.append("\r\n");
	}
	
	@Override
	public String getMessage()
	{
		return errorMessage;
	}
	
	public Collection<ViewInflationErrors> getErrors()
	{
		return errorMap.values();
	}
	
	protected interface ErrorFormatter
	{
		String format(ViewInflationErrors error);
	}

}
