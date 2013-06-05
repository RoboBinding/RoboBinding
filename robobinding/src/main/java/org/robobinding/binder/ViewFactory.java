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
package org.robobinding.binder;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.LayoutInflater.Factory;
import android.view.View;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
public class ViewFactory implements Factory {
    private final LayoutInflater layoutInflater;
    private final ViewNameResolver viewNameResolver;
    private final ViewCreationListener listener;

    public ViewFactory(LayoutInflater layoutInflater, ViewNameResolver viewNameResolver, 
	    ViewCreationListener listener) {
	this.layoutInflater = layoutInflater;
	this.viewNameResolver = viewNameResolver;
	this.listener = listener;

	layoutInflater.setFactory(this);
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
	try {
	    String viewFullName = viewNameResolver.getViewNameFromLayoutTag(name);

	    View view = layoutInflater.createView(viewFullName, null, attrs);

	    notifyViewCreated(attrs, view);

	    return view;
	} catch (ClassNotFoundException e) {
	    throw new RuntimeException(e);
	}
    }

    private void notifyViewCreated(AttributeSet attrs, View view) {
	listener.onViewCreated(view, attrs);
    }

    public static interface ViewCreationListener {
	void onViewCreated(View view, AttributeSet attrs);
    }
}
