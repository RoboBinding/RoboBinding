package org.robobinding;

import java.util.Collection;

import android.view.View;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Robert Taylor
 * @author Cheng Wei
 */
public class ItemBinder {
    private BinderImplementor binderImplementor;

    public ItemBinder(BinderImplementor binderImplementor) {
	this.binderImplementor = binderImplementor;
    }

    public View inflateAndBind(int layoutId, Object presentationModel, Collection<PredefinedPendingAttributesForView> predefinedPendingAttributesForViewGroup) {
	return binderImplementor.inflateAndBind(layoutId, presentationModel, predefinedPendingAttributesForViewGroup);
    }
}
