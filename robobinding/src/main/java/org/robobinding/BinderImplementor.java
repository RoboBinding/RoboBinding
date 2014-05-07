package org.robobinding;

import java.util.Collection;

import android.view.View;
import android.view.ViewGroup;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public interface BinderImplementor {
    View inflateAndBind(int layoutId, Object presentationModel);
    
    View inflateAndBind(int layoutId, Object presentationModel, ViewGroup attachToRoot);

    View inflateAndBind(int layoutId, Object presentationModel, Collection<PredefinedPendingAttributesForView> predefinedPendingAttributesForViewGroup);
}
