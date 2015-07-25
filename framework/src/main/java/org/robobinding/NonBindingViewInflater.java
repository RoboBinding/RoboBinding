package org.robobinding;

import android.view.View;
import android.view.ViewGroup;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public interface NonBindingViewInflater {
	View inflateWithoutRoot(int layoutId);

	View inflate(int layoutId, ViewGroup root, boolean attachToRoot);
}