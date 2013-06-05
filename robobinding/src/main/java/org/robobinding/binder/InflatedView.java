/**
 * Copyright 2013 Cheng Wei, Robert Taylor
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

import java.util.List;

import org.robobinding.BindingContext;
import org.robobinding.binder.ViewHierarchyInflationErrorsException.ErrorFormatter;

import android.view.View;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
public class InflatedView {
    private View rootView;
    List<ResolvedBindingAttributesForView> childViewBindingAttributesGroup;
    private ViewHierarchyInflationErrorsException errors;

    InflatedView(View rootView, List<ResolvedBindingAttributesForView> childViewBindingAttributesGroup,
    	ViewHierarchyInflationErrorsException errors) {
        this.rootView = rootView;
        this.childViewBindingAttributesGroup = childViewBindingAttributesGroup;
        this.errors = errors;
    }

    public View getRootView() {
        return rootView;
    }

    public void bindChildViews(BindingContext bindingContext) {
        for (ResolvedBindingAttributesForView viewBindingAttributes : childViewBindingAttributesGroup)
    	errors.addViewBindingError(viewBindingAttributes.bindTo(bindingContext));
    }

    public void assertNoErrors(ErrorFormatter errorFormatter) {
        errors.assertNoErrors(errorFormatter);
    }

    public void preinitializeViews(BindingContext bindingContext) {
        for (ResolvedBindingAttributesForView viewBindingAttributes : childViewBindingAttributesGroup) {
    	viewBindingAttributes.preinitializeView(bindingContext);
        }
    }
}