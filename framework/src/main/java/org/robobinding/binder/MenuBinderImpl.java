package org.robobinding.binder;

import org.robobinding.MenuBinder;

/**
 *
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class MenuBinderImpl implements MenuBinder {
    private final BindingMenuInflater bindingMenuInflater;
    private final ViewBindingLifecycle viewBindingLifecycle;

    public MenuBinderImpl(BindingMenuInflater bindingMenuInflater, 
	    ViewBindingLifecycle viewBindingLifecycle) {
        this.bindingMenuInflater = bindingMenuInflater;
        this.viewBindingLifecycle = viewBindingLifecycle;
    }

    public void inflateAndBind(int menuRes, Object presentationModel) {
	InflatedView inflatedView = bindingMenuInflater.inflate(menuRes);
	
	viewBindingLifecycle.run(inflatedView, presentationModel);
    }
}
