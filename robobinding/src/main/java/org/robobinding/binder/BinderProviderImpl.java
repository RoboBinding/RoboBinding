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
package org.robobinding.binder;

import org.robobinding.BinderImplementor;
import org.robobinding.BinderProvider;
import org.robobinding.InternalViewBinder;
import org.robobinding.ItemBinder;
import org.robobinding.NonBindingViewInflater;


/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
class BinderProviderImpl implements BinderProvider {
    private final BinderImplementor binderImplementor;
    private final NonBindingViewInflater nonBindingViewInflater;
    
    private ItemBinder itemBinder;
    private InternalViewBinder internalViewBinder;
    
    public BinderProviderImpl(BinderImplementor binderImplementor, NonBindingViewInflater nonBindingViewInflater) {
	this.binderImplementor = binderImplementor;
	this.nonBindingViewInflater = nonBindingViewInflater;
    }

    @Override
    public ItemBinder getItemBinder() {
	if(itemBinder == null) {
	    itemBinder = new ItemBinder(binderImplementor);
	}
	
	return itemBinder;
    }

    @Override
    public InternalViewBinder getInternalViewBinder() {
	if(internalViewBinder == null) {
	    internalViewBinder = new InternalViewBinder(binderImplementor, nonBindingViewInflater);
	}
	
	return internalViewBinder;
    }

}
