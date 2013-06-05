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
package org.robobinding.integrationtest;

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

import org.robobinding.binder.NonBindingViewInflater;
import org.robobinding.binder.ViewFactory.ViewCreationListener;

import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class NonBindingViewInflaterWithOnViewCreationCalls extends NonBindingViewInflater {
    private final NonBindingViewInflater forwardingViewInflater;
    private final List<ViewCreationListener> listeners;
    private final List<OnViewCreationCall> onViewCreationCalls;

    public NonBindingViewInflaterWithOnViewCreationCalls(NonBindingViewInflater nonBindingViewInflater) {
	super(null);
	this.forwardingViewInflater = nonBindingViewInflater;
	this.listeners = newArrayList();
	onViewCreationCalls = newArrayList();
    }

    @Override
    public View inflate(int layoutId) {
	View resultView = forwardingViewInflater.inflate(layoutId);
	performOnViewCreationCall(resultView);
	return resultView;
    }

    private void performOnViewCreationCall(View view) {
	for(OnViewCreationCall call : onViewCreationCalls) {
	    call.execute(listeners);
	}
    }

    @Override
    public View inflate(int layoutId, ViewGroup attachToRoot) {
	View resultView = forwardingViewInflater.inflate(layoutId, attachToRoot);
	performOnViewCreationCall(resultView);
	return resultView;
    }
    
    public void addOnViewCreationCall(View view, AttributeSet attributeSet) {
	onViewCreationCalls.add(new OnViewCreationCall(view, attributeSet));
    }

    public void addViewCreationListener(ViewCreationListener listener) {
	listeners.add(listener);
    }
    
    private static class OnViewCreationCall {
	private View view;
	private AttributeSet attributeSet;

	public OnViewCreationCall(View view, AttributeSet attributeSet) {
	    this.view = view;
	    this.attributeSet = attributeSet;
	}
	
	public void execute(List<ViewCreationListener> listeners) {
	    for (ViewCreationListener listener : listeners) {
		    listener.onViewCreated(view, attributeSet);
		}
	}
    }
}
