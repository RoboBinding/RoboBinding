package org.robobinding.widget.view;

import android.view.View;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public interface VisibilityFactory<T extends View> {
	AbstractVisibility create(T view);

}
