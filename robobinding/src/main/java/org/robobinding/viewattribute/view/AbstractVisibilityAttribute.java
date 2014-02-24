/**
 * Copyright 2011 Cheng Wei, Robert Taylor
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
package org.robobinding.viewattribute.view;

import org.robobinding.viewattribute.AbstractMultiTypePropertyViewAttribute;
import org.robobinding.viewattribute.AbstractPropertyViewAttribute;
import org.robobinding.viewattribute.AbstractReadOnlyPropertyViewAttribute;
import org.robobinding.viewattribute.PrimitiveTypeUtils;

import android.view.View;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
public abstract class AbstractVisibilityAttribute extends AbstractMultiTypePropertyViewAttribute<View> {
    protected AbstractVisibility visibility;

    @Override
    protected AbstractPropertyViewAttribute<View, ?> createPropertyViewAttribute(Class<?> propertyType) {
	if (PrimitiveTypeUtils.integerIsAssignableFrom(propertyType)) {
	    return new IntegerVisibilityAttribute(visibility);
	} else if (PrimitiveTypeUtils.booleanIsAssignableFrom(propertyType)) {
	    return new BooleanVisibilityAttribute(visibility);
	}

	throw new RuntimeException("Could not find a suitable visibility attribute class for property type: " + propertyType);
    }

    static class BooleanVisibilityAttribute extends AbstractReadOnlyPropertyViewAttribute<View, Boolean> {
	private AbstractVisibility visibility;
	public BooleanVisibilityAttribute(AbstractVisibility visibility) {
	    this.visibility = visibility;
	}

	@Override
	protected void valueModelUpdated(Boolean newValue) {
	    if (newValue) {
		visibility.makeVisible();
	    } else {
		visibility.makeGone();
	    }
	}
    }

    static class IntegerVisibilityAttribute extends AbstractReadOnlyPropertyViewAttribute<View, Integer> {
	private AbstractVisibility visibility;
	public IntegerVisibilityAttribute(AbstractVisibility visibility) {
	    this.visibility = visibility;
	}

	@Override
	protected void valueModelUpdated(Integer newValue) {
	    visibility.setVisibility(newValue);
	}
    }

}
