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

import static org.mockito.Mockito.mock;

import org.robobinding.AttributeResolutionException;
import org.robobinding.ViewResolutionException;
import org.robobinding.attribute.MissingRequiredAttributesException;
import org.robobinding.viewattribute.AttributeBindingException;

import android.view.View;

import com.google.common.collect.Lists;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class BindingViewInflationErrorBuilder
{
	private String viewName;
	private ViewResolutionException viewResolutionError;
	private ViewBindingError viewBindingError;
	
	private BindingViewInflationErrorBuilder(String viewName)
	{
		this.viewName = viewName;
		
		View view = mock(View.class);
		viewResolutionError = new ViewResolutionException(view);
		viewBindingError = new ViewBindingError(view);
	}

	public static BindingViewInflationErrorBuilder aBindingViewInflationError(String viewName)
	{
		return new BindingViewInflationErrorBuilder(viewName);
	}

	public BindingViewInflationErrorBuilder withAttributeResolutionErrorOf(String attribute, String errorMessage)
	{
		viewResolutionError.addAttributeError(new AttributeResolutionException(attribute, errorMessage));
		return this;
	}

	public BindingViewInflationErrorBuilder withMissingRequiredAttributesResolutionErrorOf(String... missingAttributes)
	{
		viewResolutionError.addMissingRequiredAttributeError(new MissingRequiredAttributesException(Lists.newArrayList(missingAttributes)));
		return this;
	}

	public BindingViewInflationErrorBuilder withAttributeBindingErrorOf(String attribute, String errorMessage)
	{
		viewBindingError.addAttributeError(new AttributeBindingException(attribute, new RuntimeException(errorMessage)));
		return this;
	}

	public BindingViewInflationError build()
	{
		BindingViewInflationError error = new BindingViewInflationError(viewResolutionError)
		{
			@Override
			public String getViewName()
			{
				return viewName;
			}
		};
		error.setBindingError(viewBindingError);
		return error;
	}
	
}
