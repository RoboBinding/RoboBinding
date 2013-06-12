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
package org.robobinding.viewattribute;

import static org.junit.Assert.assertNotNull;
import static org.robobinding.viewattribute.FromClassViewAttributeFactory.viewAttributeFactoryForClass;

import org.junit.Test;
import org.robobinding.BindingContext;
import org.robobinding.viewattribute.view.VisibilityAttribute;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class FromClassViewAttributeFactoryTest {

    @Test
    public void shouldCreateNewInstanceFromClass() {
	ViewAttributeFactory<VisibilityAttribute> viewAttributeFactory = viewAttributeFactoryForClass(VisibilityAttribute.class);

	VisibilityAttribute visibility = viewAttributeFactory.create();

	assertNotNull(visibility);
    }

    @Test(expected = RuntimeException.class)
    public void givenSuppliedClassIsAbstract_shouldThrowRuntimeException() {
	viewAttributeFactoryForClass(AbstractViewAttribute.class).create();
    }

    @Test(expected = RuntimeException.class)
    public void givenSuppliedClassIsNotVisible_shouldThrowRuntimeException() {
	viewAttributeFactoryForClass(NonVisibleViewAttribute.class).create();
    }

    public static class VisibilityAttributeFactory implements ViewAttributeFactory<VisibilityAttribute> {
	@Override
	public VisibilityAttribute create() {
	    return new VisibilityAttribute();
	}
    }

   public abstract static class AbstractViewAttribute implements ViewAttribute {
    }

    private static class NonVisibleViewAttribute implements ViewAttribute {
	@Override
	public void bindTo(BindingContext bindingContext) {
	}

	@Override
	public void preInitializeView(BindingContext bindingContext) {
	}
    }
}
