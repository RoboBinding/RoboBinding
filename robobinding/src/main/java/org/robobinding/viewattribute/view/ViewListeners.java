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

import android.view.View;
import android.view.View.OnFocusChangeListener;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class ViewListeners {
    View view;
    private OnFocusChangeListeners onFocusChangeListeners;

    public ViewListeners(View view) {
	this.view = view;
    }

    public void addOnFocusChangeListener(OnFocusChangeListener listener) {
	ensureOnFocusChangeListenersInitialized();
	onFocusChangeListeners.addListener(listener);
    }

    private void ensureOnFocusChangeListenersInitialized() {
	if (onFocusChangeListeners == null) {
	    onFocusChangeListeners = new OnFocusChangeListeners();
	    view.setOnFocusChangeListener(onFocusChangeListeners);
	}
    }
}
