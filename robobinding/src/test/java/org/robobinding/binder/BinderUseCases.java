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

import org.robobinding.ActivityBinder;
import org.robobinding.viewattribute.BindingAttributeMapper;
import org.robobinding.viewattribute.BindingAttributeMappings;
import org.robobinding.viewattribute.view.ViewListeners;

import android.app.Activity;
import android.content.Context;
import android.view.View;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class BinderUseCases {
    private Activity activity;
    private int layoutId;
    private Object presentationModel;
    
    public void staticBind() {
	Binders.bind(activity, layoutId, presentationModel);
    }
    
    public void customBind() {
	BinderFactoryBuilder builder = new BinderFactoryBuilder();
	builder.mapView(View.class, new AlternativeBindingAttributeMapperForView());
	builder.mapView(CustomView.class, new BindingAttributeMapperForCustomView(), ViewListenersForCustomView.class);
	BinderFactory reusableBinderFactory = builder.build();
	
	ActivityBinder activityBinder = reusableBinderFactory.createActivityBinder(activity, false);
	activityBinder.inflateAndBind(layoutId, presentationModel);
    }
    
    private static class AlternativeBindingAttributeMapperForView implements BindingAttributeMapper<View> {
	@Override
	public void mapBindingAttributes(BindingAttributeMappings<View> mappings) {
	}
    }
    
    private static class CustomView extends View {
	public CustomView(Context context) {
	    super(context);
	}
    }
    
    private static class BindingAttributeMapperForCustomView implements BindingAttributeMapper<CustomView> {
	@Override
	public void mapBindingAttributes(BindingAttributeMappings<CustomView> mappings) {
	}
    }
    
    private static class ViewListenersForCustomView extends ViewListeners {
	public ViewListenersForCustomView(CustomView view) {
	    super(view);
	}
    }
}
