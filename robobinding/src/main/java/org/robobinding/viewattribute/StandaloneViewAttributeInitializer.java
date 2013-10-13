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

import org.robobinding.attribute.CommandAttribute;
import org.robobinding.attribute.ValueModelAttribute;

import android.view.View;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
public class StandaloneViewAttributeInitializer {
    private final ViewListenersInjector viewListenersInjector;
    private View view;

    public StandaloneViewAttributeInitializer(ViewListenersInjector viewListenersInjector) {
	this.viewListenersInjector = viewListenersInjector;
    }

    public StandaloneViewAttributeInitializer(ViewListenersInjector viewListenersInjector, View view) {
	this(viewListenersInjector);
	this.view = view;
    }

    @SuppressWarnings({ "unchecked" })
    public void initializePropertyViewAttribute(
    		PropertyViewAttribute<? extends View> propertyViewAttribute, ValueModelAttribute attribute) {
	if (propertyViewAttribute instanceof AbstractMultiTypePropertyViewAttribute<?>) {
	    initializeMultiTypePropertyViewAttribute((AbstractMultiTypePropertyViewAttribute<View>) propertyViewAttribute, attribute);
	} else {
	    initializeAbstractPropertyViewAttribute((AbstractPropertyViewAttribute<View, ?>) propertyViewAttribute, attribute);
	}
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private void initializeAbstractPropertyViewAttribute(AbstractPropertyViewAttribute<View, ?> viewAttribute, ValueModelAttribute attribute) {
	viewAttribute.initialize(new PropertyViewAttributeConfig(view, attribute));
	viewListenersInjector.injectIfRequired(viewAttribute, view);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private void initializeMultiTypePropertyViewAttribute(AbstractMultiTypePropertyViewAttribute<? extends View> viewAttribute,
	    ValueModelAttribute attribute) {
	viewAttribute.initialize(new MultiTypePropertyViewAttributeConfig(view, attribute, viewListenersInjector));
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void initializeCommandViewAttribute(AbstractCommandViewAttribute<? extends View> viewAttribute, CommandAttribute attribute) {
	viewAttribute.initialize(new CommandViewAttributeConfig(view, attribute));
	viewListenersInjector.injectIfRequired(viewAttribute, view);
    }

    public void setView(View view) {
	this.view = view;
    }
}
