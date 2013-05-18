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

import org.robobinding.ViewResolutionErrors;

import com.google.common.collect.Lists;

import android.view.View;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class ViewInflationErrors {
    private View view;
    private ViewResolutionErrors resolutionErrors;
    private ViewBindingErrors bindingErrors;

    public ViewInflationErrors(ViewResolutionErrors resolutionError) {
	this.view = resolutionError.getView();
	this.resolutionErrors = resolutionError;
    }

    void setBindingErrors(ViewBindingErrors bindingError) {
	this.bindingErrors = bindingError;
    }

    public boolean hasErrors() {
	return resolutionErrors.hasErrors() || bindingErrors.hasErrors();
    }

    public View getView() {
	return view;
    }

    public ViewResolutionErrors getResolutionErrors() {
	return resolutionErrors;
    }

    public ViewBindingErrors getBindingErrors() {
	return bindingErrors;
    }

    public int numErrors() {
	return resolutionErrors.numErrors() + bindingErrors.numErrors();
    }

    public String getViewName() {
	return view.getClass().getSimpleName();
    }

    public Collection<Exception> getErrors() {
	List<Exception> errors = Lists.newArrayList();
	errors.addAll(resolutionErrors.getErrors());
	errors.addAll(bindingErrors.getAttributeErrors());
	return errors;
    }

}
