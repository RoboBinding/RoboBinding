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
package org.robobinding.viewattribute.view;

import static com.google.common.collect.Maps.newHashMap;

import java.util.Map;

import android.view.View;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class ViewListenersMapImpl implements ViewListenersMap {
    private final Map<Class<? extends View>, Class<? extends ViewListeners>> mappings;
    
    public ViewListenersMapImpl() {
	mappings = newHashMap();
    }
    
    private ViewListenersMapImpl(Map<Class<? extends View>, Class<? extends ViewListeners>> mappings) {
	this.mappings = newHashMap(mappings);
    }
    
    @Override
    public boolean contains(Class<? extends View> viewClass) {
        return mappings.containsKey(viewClass);
    }
    
    @Override
    public Class<? extends ViewListeners> getViewListenersClass(Class<? extends View> viewClass) {
        return mappings.get(viewClass);
    }
    
    public void put(Class<? extends View> viewClass, Class<? extends ViewListeners> viewListenersClass) {
	mappings.put(viewClass, viewListenersClass);
    }
    
    public ViewListenersMapImpl copy() {
	return new ViewListenersMapImpl(mappings);
    }
}
