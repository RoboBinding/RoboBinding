package org.robobinding.binder;

import org.robobinding.BinderImplementor;
import org.robobinding.BinderProvider;
import org.robobinding.ViewBinder;
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
    private ViewBinder viewBinder;
    
    public BinderProviderImpl(BinderImplementor binderImplementor, NonBindingViewInflater nonBindingViewInflater) {
	this.binderImplementor = binderImplementor;
	this.nonBindingViewInflater = nonBindingViewInflater;
    }

    @Override
    public ItemBinder getItemBinder() {
	if (itemBinder == null) {
	    itemBinder = new ItemBinder(binderImplementor);
	}
	
	return itemBinder;
    }

    @Override
    public ViewBinder getViewBinder() {
	if (viewBinder == null) {
	    viewBinder = new ViewBinder(binderImplementor, nonBindingViewInflater);
	}
	
	return viewBinder;
    }

}
