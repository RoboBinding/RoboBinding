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
package org.robobinding.viewattribute;

import java.util.Map;

import org.robobinding.BindingContext;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class ChildViewAttributes {
    final Map<String, Bindable> childViewAttributeMap;
    private final boolean failOnFirstBindingError;
    private final AttributeGroupBindingException bindingErrors;

    public ChildViewAttributes(Map<String, Bindable> childViewAttributeMap, boolean failOnFirstBindingError) {
	this.childViewAttributeMap = childViewAttributeMap;
	this.failOnFirstBindingError = failOnFirstBindingError;
	this.bindingErrors = new AttributeGroupBindingException();
    }

    public void bindTo(BindingContext bindingContext) {
	for (Map.Entry<String, Bindable> childViewAttributeEntry : childViewAttributeMap.entrySet()) {
	    Bindable childViewAttribute = childViewAttributeEntry.getValue();

	    try {
		childViewAttribute.bindTo(bindingContext);
	    } catch (RuntimeException e) {
		bindingErrors.addChildAttributeError(childViewAttributeEntry.getKey(), e);

		if (failOnFirstBindingError)
		    break;
	    }
	}

	bindingErrors.assertNoErrors();
    }

    public void preInitializeView(BindingContext bindingContext) {
	for (Map.Entry<String, Bindable> childViewAttributeEntry : childViewAttributeMap.entrySet()) {
	    Bindable childViewAttribute = childViewAttributeEntry.getValue();
	    if (childViewAttribute instanceof ViewAttribute) {
		((ViewAttribute) childViewAttribute).preInitializeView(bindingContext);
	    }
	}
    }
}