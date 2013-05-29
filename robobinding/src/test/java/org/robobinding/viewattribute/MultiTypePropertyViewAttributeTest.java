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

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.robobinding.viewattribute.MockMultiTypePropertyViewAttributeConfigBuilder.aMultiTypePropertyViewAttributeConfig;

import org.robobinding.BindingContext;

import android.view.View;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class MultiTypePropertyViewAttributeTest extends ViewAttributeContractTest<AbstractMultiTypePropertyViewAttribute<View>> {
    @Override
    protected AbstractMultiTypePropertyViewAttribute<View> throwsExceptionDuringPreInitializingView() {
	return new ThrowsExceptionDuringPreInitializingView();
    }

    @Override
    protected AbstractMultiTypePropertyViewAttribute<View> throwsExceptionDuringBinding() {
	return new ThrowsExceptionDuringBinding();
    }

    private abstract static class AbstractMultiTypePropertyViewAttributeWithDefaultConfig extends AbstractMultiTypePropertyViewAttribute<View> {
	public AbstractMultiTypePropertyViewAttributeWithDefaultConfig() {
	    initialize(aMultiTypePropertyViewAttributeConfig(mock(View.class), "propertyName"));
	}
    }

    private static class ThrowsExceptionDuringPreInitializingView extends AbstractMultiTypePropertyViewAttributeWithDefaultConfig {
	@Override
	protected AbstractPropertyViewAttribute<View, ?> createPropertyViewAttribute(Class<?> propertyType) {
	    return null;
	}
    }

    private static class ThrowsExceptionDuringBinding extends AbstractMultiTypePropertyViewAttributeWithDefaultConfig {
	@Override
	protected AbstractPropertyViewAttribute<View, ?> createPropertyViewAttribute(Class<?> propertyType) {
	    @SuppressWarnings("unchecked")
	    AbstractPropertyViewAttribute<View, ?> propertyViewAttribute = mock(AbstractPropertyViewAttribute.class);
	    doThrow(new RuntimeException()).when(propertyViewAttribute).bindTo(any(BindingContext.class));
	    return propertyViewAttribute;
	}
    }
}
