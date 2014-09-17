package org.robobinding.widget.view;

import android.view.View;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 */
public class ViewVisibilityFactory implements VisibilityFactory<View> {
	@Override
	public AbstractVisibility create(View view) {
		return new ViewVisibility(view);
	}
}
