package org.robobinding.binder;

import org.robobinding.ActivityBinder;
import org.robobinding.viewattribute.ViewBinding;
import org.robobinding.viewattribute.BindingAttributeMappings;
import org.robobinding.widget.view.ViewListeners;

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
    
    private static class AlternativeBindingAttributeMapperForView implements ViewBinding<View> {
	@Override
	public void mapBindingAttributes(BindingAttributeMappings<View> mappings) {
	}
    }
    
    private static class CustomView extends View {
	public CustomView(Context context) {
	    super(context);
	}
    }
    
    private static class BindingAttributeMapperForCustomView implements ViewBinding<CustomView> {
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
