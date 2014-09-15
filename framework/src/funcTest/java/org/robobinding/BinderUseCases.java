package org.robobinding;

import org.robobinding.ViewBinder;
import org.robobinding.binder.BinderFactory;
import org.robobinding.binder.BinderFactoryBuilder;
import org.robobinding.binder.Binders;
import org.robobinding.viewattribute.BindingAttributeMappings;
import org.robobinding.viewattribute.ViewBinding;
import org.robobinding.viewattribute.ViewListeners;

import android.content.Context;
import android.view.View;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class BinderUseCases {
    private Context context;
    private int layoutId;
    private Object presentationModel;
    
    public void staticBind() {
	Binders.inflateAndBind(context, layoutId, presentationModel);
    }
    
    public void customBind() {
	BinderFactoryBuilder builder = new BinderFactoryBuilder();
	builder.mapView(View.class, new AlternativeBindingAttributeMapperForView());
	builder.mapView(CustomView.class, new BindingAttributeMapperForCustomView(), ViewListenersForCustomView.class);
	BinderFactory reusableBinderFactory = builder.build();
	
	ViewBinder viewBinder = reusableBinderFactory.createViewBinder(context, false);
	viewBinder.inflateAndBind(layoutId, presentationModel);
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
    
    private static class ViewListenersForCustomView implements ViewListeners {
    }
}
