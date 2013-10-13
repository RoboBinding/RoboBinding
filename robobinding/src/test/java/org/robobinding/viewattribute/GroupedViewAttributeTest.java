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

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Ignore;
import org.junit.Test;
import org.robobinding.BindingContext;
import org.robobinding.attribute.ChildAttributeResolverMappings;

import android.view.View;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 */
public class GroupedViewAttributeTest extends ViewAttributeContractTest<AbstractGroupedViewAttribute<View>> {
    @Test
    public void whenGetDefaultCompulsoryAttributes_thenReturnEmptyArray() {
	AbstractGroupedViewAttribute<View> groupedViewAttribute = new GroupedViewAttributeForTest();

	assertThat(groupedViewAttribute.getCompulsoryAttributes().length, is(0));
    }

    @Ignore
    @Test(expected = AttributeGroupBindingException.class)
    @Override
    public void whenAnExceptionIsThrownDuringPreInitializingView_thenCatchAndRethrow() {
	//super.whenAnExceptionIsThrownDuringPreInitializingView_thenCatchAndRethrow();
    }

    @Override
    protected AbstractGroupedViewAttribute<View> throwsExceptionDuringPreInitializingView() {
	//doThrow(new AttributeGroupBindingException()).when(initializedChildViewAttributes).preInitializeView(any(BindingContext.class));
	return new GroupedViewAttributeForTest();
    }

    @Ignore
    @Test(expected = AttributeGroupBindingException.class)
    @Override
    public void whenAnExceptionIsThrownDuringBinding_thenCatchAndRethrow() {
	//super.whenAnExceptionIsThrownDuringBinding_thenCatchAndRethrow();
    }

    @Override
    protected AbstractGroupedViewAttribute<View> throwsExceptionDuringBinding() {
	//doThrow(new AttributeGroupBindingException()).when(initializedChildViewAttributes).preInitializeView(any(BindingContext.class));
	return new GroupedViewAttributeForTest();
    }

    private static class GroupedViewAttributeForTest extends AbstractGroupedViewAttribute<View> {

	@Override
	public void mapChildAttributeResolvers(ChildAttributeResolverMappings resolverMappings) {
	}

	@Override
	protected void setupChildViewAttributes(ChildViewAttributesBuilder<View> childViewAttributesBuilder, BindingContext bindingContext) {
	}
    }
}
