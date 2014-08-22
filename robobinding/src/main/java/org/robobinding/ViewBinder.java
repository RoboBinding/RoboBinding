package org.robobinding;


import static org.robobinding.util.Preconditions.checkValidResourceId;

import org.robobinding.internal.guava.Preconditions;

import android.view.View;
import android.view.ViewGroup;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
public class ViewBinder {
    private final BinderImplementor binderImplementor;
    private final NonBindingViewInflater nonBindingViewInflater;

    public ViewBinder(BinderImplementor binderImplementor, NonBindingViewInflater nonBindingViewInflater) {
	this.binderImplementor = binderImplementor;
	this.nonBindingViewInflater = nonBindingViewInflater;
    }

    public View inflate(int layoutId) {
	checkLayoutId(layoutId);
	
        return nonBindingViewInflater.inflate(layoutId);
    }
    
    private void checkLayoutId(int layoutId) {
	checkValidResourceId(layoutId, "invalid layoutId '" + layoutId + "'");
    }

    public View inflateAndBind(int layoutId, Object presentationModel) {
	checkLayoutId(layoutId);
	checkPresentationModel(presentationModel);
	
	return binderImplementor.inflateAndBind(layoutId, presentationModel);
    }
    
    private void checkPresentationModel(Object presentationModel) {
	Preconditions.checkNotNull(presentationModel, "presentationModel must not be null");
    }

    public View inflateAndBind(int layoutId, Object presentationModel, ViewGroup attachToRoot) {
	checkLayoutId(layoutId);
	checkPresentationModel(presentationModel);
	checkAttachToRoot(attachToRoot);

	return binderImplementor.inflateAndBind(layoutId, presentationModel, attachToRoot);
    }
    
    private void checkAttachToRoot(ViewGroup attachToRoot) {
	Preconditions.checkNotNull(attachToRoot, "attachToRoot must not be null");
    }

}
