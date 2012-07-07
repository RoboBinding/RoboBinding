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
import java.util.List;

import org.robobinding.ViewResolutionError;

import com.google.common.collect.Lists;

import android.view.View;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class BindingViewInflationError
{
	private View view;
	private ViewResolutionError resolutionError;
	private ViewBindingError bindingError;
	public BindingViewInflationError(ViewResolutionError resolutionError)
	{
		this.view = resolutionError.getView();
		this.resolutionError = resolutionError;
	}

	void setBindingError(ViewBindingError bindingError)
	{
		this.bindingError = bindingError;
	}

	public boolean hasErrors()
	{
		return resolutionError.hasErrors() || bindingError.hasErrors();
	}

	public View getView()
	{
		return view;
	}

	public ViewResolutionError getResolutionError()
	{
		return resolutionError;
	}

	public ViewBindingError getBindingError()
	{
		return bindingError;
	}

	public int numErrors()
	{
		return resolutionError.numErrors()+bindingError.numErrors();
	}

	public String getViewName()
	{
		return view.getClass().getSimpleName();
	}

	public Collection<Exception> getErrors()
	{
		List<Exception> errors = Lists.newArrayList();
		errors.addAll(resolutionError.getErrors());
		errors.addAll(bindingError.getAttributeErrors());
		return errors;
	}

}
