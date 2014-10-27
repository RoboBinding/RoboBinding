package org.robobinding.widget.view;

import android.view.View;

/**
 * 
 * @since 1.0
 * @version $Revision: 1.0 $
 * @author Cheng Wei
 * @author Robert Taylor
 */
public abstract class AbstractVisibility {
	public abstract void makeVisible();

	public abstract void makeGone();

	protected abstract void makeInvisible();

	public final void setVisibility(int visibility) {
		if (View.VISIBLE == visibility) {
			makeVisible();
		} else if (View.INVISIBLE == visibility) {
			makeInvisible();
		} else {
			makeGone();
		}
	}
}
