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
	if (itemBinder == null) {
	    itemBinder = new ItemBinder(binderImplementor);
	}
	
	return itemBinder;
    }

    @Override
    public InternalViewBinder getInternalViewBinder() {
	if (internalViewBinder == null) {
	    internalViewBinder = new InternalViewBinder(binderImplementor, nonBindingViewInflater);
	}
	
	return internalViewBinder;
    }

}
