package org.robobinding;

import android.view.View;
import android.view.ViewGroup;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public interface ViewBinder {
    View inflate(int layoutId);

    View inflateAndBind(int layoutId, Object presentationModel);

    View inflateAndBind(int layoutId, Object presentationModel, ViewGroup attachToRoot);

}
