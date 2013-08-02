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
public class InitializedChildViewAttributes {
    private final Map<String, ViewAttribute> childAttributeMap;
    private final boolean failOnFirstBindingError;
    private final AttributeGroupBindingException bindingErrors;

    public InitializedChildViewAttributes(Map<String, ViewAttribute> childAttributeMap, boolean failOnFirstBindingError) {
	this.childAttributeMap = childAttributeMap;
	this.failOnFirstBindingError = failOnFirstBindingError;
	this.bindingErrors = new AttributeGroupBindingException();
    }

    public void bindTo(BindingContext bindingContext) {
	for (Map.Entry<String, ViewAttribute> childAttributeEntry : childAttributeMap.entrySet()) {
	    ViewAttribute childAttribute = childAttributeEntry.getValue();

	    try {
		childAttribute.bindTo(bindingContext);
	    } catch (AttributeBindingException e) {
		bindingErrors.addChildAttributeError(childAttributeEntry.getKey(), e);

		if (failOnFirstBindingError)
		    break;
	    }
	}

	bindingErrors.assertNoErrors();
    }

    public void preInitializeView(BindingContext bindingContext) {
	for (Map.Entry<String, ViewAttribute> childAttributeEntry : childAttributeMap.entrySet()) {
	    ViewAttribute childAttribute = childAttributeEntry.getValue();
	    childAttribute.preInitializeView(bindingContext);
	}
    }
}