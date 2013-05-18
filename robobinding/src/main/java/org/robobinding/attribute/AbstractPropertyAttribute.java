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
package org.robobinding.attribute;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public abstract class AbstractPropertyAttribute extends AbstractAttribute {
    public AbstractPropertyAttribute(String name) {
	super(name);
    }

    public abstract boolean isTwoWayBinding();

    public final ValueModelAttribute asValueModelAttribute() {
	if (isValueModel()) {
	    return (ValueModelAttribute) this;
	} else {
	    throw new RuntimeException("Not a value model attribute");
	}
    }

    boolean isValueModel() {
	return ValueModelAttribute.class.isInstance(this);
    }

    public final StaticResourceAttribute asStaticResourceAttribute() {
	if (isStaticResource()) {
	    return (StaticResourceAttribute) this;
	} else {
	    throw new RuntimeException("Not a static resource attribute value");
	}
    }

    public final boolean isStaticResource() {
	return StaticResourceAttribute.class.isInstance(this);
    }
}
