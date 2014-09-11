package org.robobinding;

import android.view.View;
import android.view.ViewGroup;

/**
 * Inflate and bind a layout to a presentation model. It is used for Activity, Fragment, Dialog and so on.
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
