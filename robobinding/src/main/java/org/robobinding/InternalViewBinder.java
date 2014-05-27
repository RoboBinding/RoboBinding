package org.robobinding;


import android.view.View;
import android.view.ViewGroup;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
public class InternalViewBinder {
    private final BinderImplementor binderImplementor;
    private final NonBindingViewInflater nonBindingViewInflater;

    public InternalViewBinder(BinderImplementor binderImplementor, NonBindingViewInflater nonBindingViewInflater) {
	this.binderImplementor = binderImplementor;
	this.nonBindingViewInflater = nonBindingViewInflater;
    }

    public View inflate(int layoutId) {
        return nonBindingViewInflater.inflate(layoutId);
    }

    public View inflateAndBind(int layoutId, Object presentationModel) {
	return binderImplementor.inflateAndBind(layoutId, presentationModel);
    }

    public View inflateAndBind(int layoutId, Object presentationModel, ViewGroup attachToRoot) {
	return binderImplementor.inflateAndBind(layoutId, presentationModel, attachToRoot);
    }
    
    public View justBind(View view, Object presentationModel) {
    	if (binderImplementor instanceof ViewBinderImplementor) {
    		ViewBinderImplementor viewBinderImplementor = (ViewBinderImplementor) binderImplementor;
    		return viewBinderImplementor.bind(view, presentationModel);
    	}
    	return view;
    }

}
