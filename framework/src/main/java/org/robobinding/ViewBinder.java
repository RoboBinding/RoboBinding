package org.robobinding;

import android.view.View;
import android.view.ViewGroup;

/**
 * Inflate and bind a layout to a presentation model. It is used for Activity,
 * Fragment, Dialog and so on.
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public interface ViewBinder {
	/**
	 * Note that: the use of this method should be limited to the layouts that don't have a parent view, e.g., the layouts for Activities, Dialogs.
	 * One who has a parent view should use {@link #inflateAndBindWithoutAttachingToRoot(int, Object, ViewGroup)} instead, 
	 * so that {@link android.view.ViewGroup.LayoutParams} from the parent view can be inherited and applied.
	 * 
	 */
	View inflateAndBind(int layoutId, Object presentationModel);

	View inflateAndBind(int layoutId, Object presentationModel, ViewGroup root);

	View inflateAndBindWithoutAttachingToRoot(int layoutId, Object presentationModel, ViewGroup root);
}
