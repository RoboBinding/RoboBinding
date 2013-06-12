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
package org.robobinding.viewattribute.view;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;

import java.lang.reflect.ParameterizedType;

import org.junit.Before;
import org.robobinding.viewattribute.AbstractPropertyViewAttributeTest;
import org.robobinding.viewattribute.ParameterizedTypeUtils;
import org.robobinding.viewattribute.PropertyViewAttribute;

import android.view.View;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public abstract class AbstractPropertyViewAttributeWithViewListenersAwareTest<ViewType extends View, 
	PropertyViewAttributeType extends PropertyViewAttribute<? super ViewType>, ViewListenersType extends ViewListeners>
	extends AbstractPropertyViewAttributeTest<ViewType, PropertyViewAttributeType> {
    protected ViewListenersType viewListeners;

    @SuppressWarnings("unchecked")
    @Before
    public void initializeViewListeners() {
	assertThat(attribute, instanceOf(ViewListenersAware.class));
	ParameterizedType superclass = (ParameterizedType) getClass().getGenericSuperclass();
	viewListeners = ParameterizedTypeUtils.createTypeArgument(superclass, 2, view.getClass(), view);
	((ViewListenersAware<ViewListeners>) attribute).setViewListeners(viewListeners);
    }

}
