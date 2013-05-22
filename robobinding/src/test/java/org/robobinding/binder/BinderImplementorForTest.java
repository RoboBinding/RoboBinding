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

import java.util.Collection;

import org.robobinding.BindingContext;
import org.robobinding.PredefinedPendingAttributesForView;

import android.content.Context;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class BinderImplementorForTest extends InternalBinder {
    private BindingViewInflater bindingViewInflater;

    public BinderImplementorForTest(final Context context, BindingViewInflater bindingViewInflater) {
	super(context, new BindingContextCreator() {

	    @Override
	    public BindingContext create(Object presentationModel) {
		return new BindingContext(mock(InternalBinderFactory.class), context, presentationModel, false);
	    }
	}, new ViewHierarchyInflationErrorsException.ErrorFormatter() {

	    @Override
	    public String format(ViewInflationErrors error) {
		return error.toString();
	    }
	}, true);
	this.bindingViewInflater = bindingViewInflater;
    }

    @Override
    BindingViewInflater createBindingViewInflater(Collection<PredefinedPendingAttributesForView> predefinedPendingAttributesForViewGroup) {
	return bindingViewInflater;
    }
}