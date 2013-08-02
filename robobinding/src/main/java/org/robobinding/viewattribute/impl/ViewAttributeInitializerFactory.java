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
package org.robobinding.viewattribute.impl;

import java.util.Map;

import org.robobinding.viewattribute.StandaloneViewAttributeInitializer;
import org.robobinding.viewattribute.ViewListenersInjector;
import org.robobinding.viewattribute.view.ViewListeners;

import android.view.View;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class ViewAttributeInitializerFactory {
    private final Map<Class<? extends View>, Class<? extends ViewListeners>> viewToViewListenersMap;
    
    public ViewAttributeInitializerFactory(Map<Class<? extends View>, Class<? extends ViewListeners>> viewToViewListenersMap) {
	this.viewToViewListenersMap = viewToViewListenersMap;
    }
    
    public ViewAttributeInitializer create(View view) {
	ViewListenersInjector viewListenersInjector = new ViewListenersProvider(viewToViewListenersMap);
	return new ViewAttributeInitializer(new StandaloneViewAttributeInitializer(viewListenersInjector, view));
    }
}
